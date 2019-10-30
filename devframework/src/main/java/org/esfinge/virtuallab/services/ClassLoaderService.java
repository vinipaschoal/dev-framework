package org.esfinge.virtuallab.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.persistence.Entity;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.commons.io.FilenameUtils;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceDAO;
import org.esfinge.virtuallab.exceptions.ClassLoaderException;
import org.esfinge.virtuallab.utils.Utils;

/**
 * Carregador de classes de servicos.
 */
public class ClassLoaderService
{
	// mapa dos classloaders associados as classes de servico
	private Map<String, InternalClassLoader> classLoaderMap;

	// instancia unica da classe
	private static ClassLoaderService _instance;
	

	/**
	 * Singleton.
	 */
	public static ClassLoaderService getInstance()
	{
		if (_instance == null)
			_instance = new ClassLoaderService();

		return _instance;
	}

	/**
	 * Cria um novo carregador de classes de servicos.
	 */
	private ClassLoaderService()
	{
		// mapa dos classloaders associados as classes de servico
		this.classLoaderMap = new ConcurrentHashMap<>();
	}
	
	/**
	 * Carrega o arquivo de classe de servico.
	 */
	public Class<?> loadService(String serviceFilePath) throws ClassLoaderException
	{
		return this.loadService(new File(serviceFilePath));
	}

	/**
	 * Carrega o arquivo de classe de servico.
	 */
	public Class<?> loadService(File serviceFile) throws ClassLoaderException
	{
		// nome do arquivo
		String fileName = serviceFile.getName();
		
		// verifica se o arquivo eh valido e acessivel
		if ( !serviceFile.isFile() || !serviceFile.canRead() )
			throw new ClassLoaderException(String.format("Erro ao carregar servico, arquivo inacessivel: %s", fileName));
			
		// verifica se eh um .class ou .jar		
		if (! (FilenameUtils.isExtension(fileName, "class") ||  
			   FilenameUtils.isExtension(fileName, "jar")) )
			throw new ClassLoaderException(String.format("Erro ao carregar servico, arquivo invalido: %s", fileName));
		
		try
		{
			// tenta carregar o arquivo (class/jar)
			InternalClassLoader cl = new InternalClassLoader(serviceFile.toURI().toURL());
			
			// obtem a classe de servico carregada
			Class<?> serviceClass = cl.getServiceClass();
			
			// mapeia o classloader com a classe de servico
			this.classLoaderMap.put(serviceClass.getCanonicalName(), cl);
			
			// fecha o classloader
			cl.close();
			
			// retorna a classe de servico
			return serviceClass;
		}
		catch ( Exception e)
		{
			throw new ClassLoaderException(String.format("Erro ao carregar servico: %s", fileName), e);
		}
	}
	
	/**
	 * Descarrega a classe de servico informada.
	 */
	public boolean unloadService(String qualifiedClassName)
	{
		// remove o classloader associado a classe
		return this.classLoaderMap.remove(qualifiedClassName) != null;
	}
	
	/**
	 * Retorna a classe de servico informada.
	 */
	public Class<?> getService(String qualifiedClassName) throws ClassLoaderException
	{
		// obtem o classloader associado ao servico
		InternalClassLoader cl = this.classLoaderMap.get(qualifiedClassName);

		// classe nao mapeada/carregada
		Utils.throwIfNull(cl, ClassLoaderException.class, 
				String.format("Classe de servico nao carregada: %s", qualifiedClassName));

		return cl.getServiceClass();
	}
	
	/**
	 * Verifica se o servico esta carregado.
	 */
	public boolean isServiceLoaded(String qualifiedClassName)
	{
		return this.classLoaderMap.containsKey(qualifiedClassName);
	}
	 
	/**
	 * Retorna os ClassLoaders dos servicos que contem entidades JPA.
	 */
	public List<ClassLoader> getJPAClassLoaders()
	{
		return new ArrayList<ClassLoader>(
				Utils.filterFromCollection(
						this.classLoaderMap.values(), 
						InternalClassLoader::hasEntities));
	}
	
	/**
	 * Procura pela classe entre as classes carregadas pelos ClassLoaders dos servicos. 
	 */
	private Class<?> findClass(String qualifiedClassName, InternalClassLoader caller) throws ClassNotFoundException
	{
		for ( InternalClassLoader cl : this.classLoaderMap.values() )
		{
			// nao procura no proprio ClassLoader que chamou este metodo
			if ( cl == caller )
				continue;
			
			// verifica se a classe foi carregada pelo ClassLoader
			if ( cl.isClassLoaded(qualifiedClassName) )
				return cl.loadClass(qualifiedClassName);
		}

		// nao encontrou a classe em nenhum ClassLoader!
		throw new ClassNotFoundException();
	}


	/**
	 * Carregador de classes interno.
	 */
	private class InternalClassLoader extends URLClassLoader
	{
		// a classe de servico lida do arquivo
		private Class<?> serviceClass;
		
		// indica se o servico (DAO) possui entidades JPA mapeadas
		private boolean hasEntities;
		
		
		public InternalClassLoader(URL fileURL) throws Exception
		{
			super(new URL[] {fileURL}, ClassLoaderService.class.getClassLoader());
			
			this.serviceClass = null;
			this.hasEntities = false;

			// arquivo (jar ou class) apontado pela URL
			File file = new File(fileURL.toURI());
			
			if ( FilenameUtils.isExtension(file.getName(), "class") )
				this.loadServiceClass(file);
			else
				this.loadServiceJar(file);
		}
		
		public Class<?> getServiceClass()
		{
			return this.serviceClass;
		}
		
		public boolean hasEntities()
		{
			return this.hasEntities;
		}
		
		@Override
		protected Class<?> findClass(String qualifiedClassName) throws ClassNotFoundException
		{
			// utilizado quando tenta carregar uma classe que eh referenciada pelo servico
			// mas nao foi incluida no arquivo JAR ou .class (pois faz parte de outro servico ja existente).
			// um exemplo sao classes de servico injetadas via @Inject.

			// tenta encontrar a classe nos ClassLoaders dos servicos carregados pelo ClassLoaderService
			return ClassLoaderService.this.findClass(qualifiedClassName, this);
		}
		
		protected boolean isClassLoaded(String qualifiedClassName)
		{
			// verifica se a classe foi carregada por esse ClassLoader
			return this.findLoadedClass(qualifiedClassName) != null;
		}
		
		private void loadServiceClass(File file) throws Exception
		{
			// carrega a classe
			this.serviceClass = this.loadClassInternal(new FileInputStream(file), file.getName());
		}		
		
		private void loadServiceJar(File file) throws Exception
		{
			// arquivo jar
			JarFile jarFile = null;

			try
			{
				// abre o arquivo jar
				jarFile = new JarFile(file);

				// obtem a entrada para as classes do jar
				Enumeration<JarEntry> jarEntries = jarFile.entries();
				while (jarEntries.hasMoreElements())
				{
					JarEntry jarEntry = jarEntries.nextElement();

					// verifica se eh uma classe
					if (jarEntry.isDirectory() || !FilenameUtils.isExtension(jarEntry.getName(), "class"))
						continue;

					// le a entrada no arquivo JAR e carrega a classe
					Class<?> clazz = this.loadClassInternal(jarFile.getInputStream(jarEntry), FilenameUtils.getName(jarEntry.getName()));
					
					// verifica se eh uma classe de servico
					if ( clazz.isAnnotationPresent(ServiceClass.class) ||
						 clazz.isAnnotationPresent(ServiceDAO.class) )
					{
						// verifica se jah existe uma classe de servico mapeada
						if ( this.serviceClass != null )
							throw new ClassLoaderException(String.format(
									"Nao eh permitido mais de uma classe de servico por jar: %s | %s", 
									this.serviceClass.getName(), clazz.getName()));
							
						this.serviceClass = clazz;
					}
					
					// verifica se eh uma entidade JPA 
					if ( clazz.isAnnotationPresent(Entity.class) )
						this.hasEntities = true;
				}
			}
			finally
			{
				// fecha o arquivo jar
				if ( jarFile != null )
				{
					try
					{
						jarFile.close();
					}
					catch (IOException e)
					{
						// TODO: debug..
						e.printStackTrace();
					}
				}
			}
		}

		private Class<?> loadClassInternal(InputStream inputStream, String fileName) throws ClassFormatException, IOException
		{
			// obtem as informacoes da classe
			JavaClass jc = new ClassParser(inputStream, fileName).parse();
			
			// carrega a classe lendo o seu bytecode
			return this.defineClass(jc.getClassName(), jc.getBytes(), 0, jc.getBytes().length);
		}
	}
}
