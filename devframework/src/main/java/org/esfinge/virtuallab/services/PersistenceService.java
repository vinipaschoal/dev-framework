package org.esfinge.virtuallab.services;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.apache.commons.io.FileUtils;
import org.esfinge.virtuallab.descriptors.ClassDescriptor;
import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.metadata.ClassMetadata;
import org.esfinge.virtuallab.metadata.MetadataHelper;
import org.esfinge.virtuallab.metadata.ServiceClassMetadata;
import org.esfinge.virtuallab.metadata.ServiceDAOMetadata;
import org.esfinge.virtuallab.spring.EntityManagerFactoryHelper;
import org.esfinge.virtuallab.utils.Utils;

/**
 * Gerencia as classes persistidas com servicos validos.
 */
public class PersistenceService
{
	// instancia unica da classe
	private static PersistenceService _instance;
	
	// cache para as classes de servico ja carregadas
	private Map<String, ServiceInfo> serviceCache;

 	/**
	 * Construtor interno.
	 */
	private PersistenceService()
	{
		// inicializa o cache
		this.serviceCache = new HashMap<>();
		
		// mapa temporario dos arquivos com suas classes de servico
		Map<Class<?>, File> tmpMap = new HashMap<>();
		
		// obtem os arquivos de classes e jar do diretorio de upload
		for (File file : this.getUploadedFiles())
		{
			try
			{
				// primeiro carrega todas as classes / jars 
				// (sem carregar os metadados para nao ter problemas com ClassNotFoundException)
				Class<?> serviceClass = ClassLoaderService.getInstance().loadService(file);
				
				// armazena a classe e o arquivo no mapa temporario
				// para depois carregar os metadados (evita os ClassNotFoundException)
				tmpMap.put(serviceClass, file);
			}
			catch (Exception exc)
			{
				// TODO: debug..
				exc.printStackTrace();
			}
		}
		
		// agora que todas as classes ja foram carregadas, 
		// obtem os metadados das classes de servico e constroi o cache
		for (Entry<Class<?>, File> entry: tmpMap.entrySet())
		{
			try
			{
				ClassMetadata serviceMetadata = MetadataHelper.getInstance().getClassMetadata(entry.getKey());
				
				// atualiza o cache de servicos
				// NAO recarrega o EntityManager de imediato, 
				// espera carregar todos os servicos para depois 
				// carregar as entidades de uma so vez 
				this.cacheService(new ServiceInfo(entry.getValue(), serviceMetadata), false);
			}
			catch (Exception exc)
			{
				// TODO: debug..
				exc.printStackTrace();
			}
		}
		
		// carrega as entidades no EntityManager
		EntityManagerFactoryHelper.getInstance().loadEntities();
	}
	
	/**
	 * Singleton.
	 */
	public static PersistenceService getInstance()
	{
		if (_instance == null)
			_instance = new PersistenceService();

		return _instance;
	}


	/**
	 * Salva o arquivo (classe/jar) no diretorio de upload se o mesmo for valido.
	 */
	public synchronized void saveUploadedFile(InputStream fileStream, String fileName) throws PersistenceException
	{
		try
		{
			// salva o arquivo no diretorio temporario para ser validado
			File tmpFile = Utils.saveToTempDir(fileStream, fileName);

			// verifica se o arquivo (classe/jar) eh valido
			ClassMetadata serviceMetadata = ValidationService.getInstance().validateUploadedFile(tmpFile);

			// move o arquivo para o diretorio de upload
			Path destFile = Paths.get(Utils.getInstance().getUploadDir(), fileName);			
			Files.move(Paths.get(tmpFile.getAbsolutePath()), destFile, StandardCopyOption.REPLACE_EXISTING);
			
			// atualiza o cache de servicos
			this.cacheService(new ServiceInfo(destFile.toFile(), serviceMetadata), true);
		}
		catch ( Exception e ) 
		{
			throw new PersistenceException(String.format("Erro ao salvar arquivo de upload: %s", fileName), e);
		}
	}
	
	/**
	 * Lista as classes do diretorio de upload com servicos validos.
	 */
	public List<ClassDescriptor> listServiceClasses()
	{
		return this.serviceCache.values().stream()
				.map(si -> new ClassDescriptor(si.getServiceMetadata())).collect(Collectors.toList());
	}

	/**
	 * Lista os servicos validos da classe informada.
	 */
	public List<MethodDescriptor> listServiceMethods(String classQualifiedName)
	{
		// obtem as informacoes da classe informada
		ServiceInfo serviceInfo = this.serviceCache.get(classQualifiedName);
		
		if ( serviceInfo != null )
		{
			// @ServiceDAO
			if ( serviceInfo.isServiceDAO() )
				return ((ServiceDAOMetadata) serviceInfo.getServiceMetadata()).getMethods()
						.stream().map(m -> new MethodDescriptor(m)).collect(Collectors.toList());

			// @ServiceClass
			else
				return ((ServiceClassMetadata) serviceInfo.getServiceMetadata()).getMethodsWithServiceMethod()
						.stream().map(m -> new MethodDescriptor(m)).collect(Collectors.toList());
		}

		return new ArrayList<MethodDescriptor>();
	}
	
	/**
	 * Remove os servicos da classe informada. 
	 */
	public boolean removeServiceClass(String classQualifiedName)
	{
		try
		{
			// remove as informacoes do cache de servicos
			ServiceInfo serviceInfo = this.serviceCache.remove(classQualifiedName);

			// descarrega a(s) classe(s) do servico
			ClassLoaderService.getInstance().unloadService(classQualifiedName);
			
			// remove o arquivo do diretorio de upload
			FileUtils.forceDelete(serviceInfo.getServiceFile());

			// verifica se eh uma classe DAO
			if ( serviceInfo.isServiceDAO() )
			{
				// remove o DataSource associado
				DataSourceService.unregisterDataSource((ServiceDAOMetadata) serviceInfo.getServiceMetadata());
				
				// desmapeia as entidades JPA
				EntityManagerFactoryHelper.getInstance().unmapEntitiesFromJar(serviceInfo.getServiceURL());
				
				// TODO: recarregar as entidades do EMF 
				// ou esperar a adicao de um novo servico DAO que recarregue o EMF?
				//EntityManagerFactoryHelper.getInstance().loadEntities();
			}
			
			return true;
		}
		catch ( Exception e )
		{
			//TODO: debug..
			e.printStackTrace();
			
			return false;
		}
	}
	
	/**
	 * Retorna os arquivos de classes/jar do diretorio de upload.
	 */
	public List<File> getUploadedFiles()
	{
		return new ArrayList<File>(FileUtils.listFiles(new File(Utils.getInstance().getUploadDir()),
				new String[] { "class", "jar" }, false));
	}
	
	/**
	 * Armazena as informacoes da classe de servico em cache.
	 */
	private void cacheService(ServiceInfo serviceInfo, boolean reloadEntities) throws Exception
	{
		// armazena as informacoes de metadados no cache
		this.serviceCache.put(serviceInfo.getClassCanonicalName(), serviceInfo);

		// se for servico DAO, tem que registrar o DataSource e as entidades JPA
		if ( serviceInfo.isServiceDAO() )
		{
			// salva as informacoes do DataSource da classe @ServiceDAO
			DataSourceService.registerDataSource((ServiceDAOMetadata) serviceInfo.getServiceMetadata());

			// mapeia as entidades JPA
			EntityManagerFactoryHelper.getInstance().mapEntitiesFromJar(serviceInfo.getServiceURL());
			
			// carrega as entidades JPA
			if ( reloadEntities )
				EntityManagerFactoryHelper.getInstance().loadEntities();
		}
	}
	
	
	/**
	 * Armazena as informacoes das classes de servico.
	 */
	private class ServiceInfo
	{
		private File serviceFile;
		private ClassMetadata serviceMetadata;
		
		
		ServiceInfo(File serviceFile, ClassMetadata serviceMetadata)
		{
			this.serviceFile = serviceFile;
			this.serviceMetadata = serviceMetadata;
		}
		
		public File getServiceFile()
		{
			return serviceFile;
		}

		public ClassMetadata getServiceMetadata()
		{
			return serviceMetadata;
		}
		
		public URL getServiceURL() throws Exception
		{
			return serviceFile.toURI().toURL();
		}
		
		public String getClassCanonicalName()
		{
			return serviceMetadata.getClazz().getCanonicalName();
		}
		
		public boolean isServiceDAO()
		{
			return serviceMetadata.isServiceDAO();
		}
	}
}