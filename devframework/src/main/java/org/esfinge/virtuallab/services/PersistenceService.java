package org.esfinge.virtuallab.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
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
	
	// cache para as classes ja carregadas
	private Map<String, ClassMetadata> classCache;

 	/**
	 * Construtor interno.
	 */
	private PersistenceService()
	{
		// inicializa o cache
		this.classCache = new HashMap<>();
		
		// 
		ValidationService validation = ValidationService.getInstance();
		
		// obtem os arquivos de classes e jar do diretorio de upload
		for (File file : this.getUploadedFiles())
			try
			{
				// caminho para o arquivo
				String filePath = file.getAbsolutePath();

				// classe
				if (FilenameUtils.isExtension(filePath, "class"))
				{
					Class<?> clazz = validation.checkClassFile(filePath);
					this.cacheServiceClass(clazz);
				}
				// jar
				else
				{
					// obtem as classes validas do jar
					List<Class<?>> jarClassList = validation.checkJarFile(filePath);
					if (jarClassList != null)
						for (Class<?> clazz : jarClassList)
							this.cacheServiceClass(clazz);
				}
			}
			catch (Exception exc)
			{
				exc.printStackTrace();
			}
	}
	
	/**
	 * Armazena as informacoes da classe de servico em cache.
	 */
	private void cacheServiceClass(Class<?> clazz)
	{
		if (clazz != null)
		{
			// obtem os metadados da classe
			ClassMetadata metadata = MetadataHelper.getInstance().getClassMetadata(clazz);
			
			// armazena as informacoes de metadados no cache
			this.classCache.put(clazz.getCanonicalName(), metadata);
			
			// salva as informacoes do DataSource da classe @ServiceDAO
			if ( metadata.isServiceDAO() )
				DataSourceService.registerDataSource((ServiceDAOMetadata) metadata);
		}
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
	 * Lista as classes do diretorio de upload com servicos validos.
	 */
	public List<ClassDescriptor> listServiceClasses()
	{
		return this.classCache.values().stream()
				.map(c -> new ClassDescriptor(c)).collect(Collectors.toList());
	}

	/**
	 * Lista os servicos validos da classe informada.
	 */
	public List<MethodDescriptor> listServiceMethods(String classQualifiedName)
	{
		// obtem a classe informada
		ClassMetadata classMetadata = this.classCache.get(classQualifiedName);
		
		if ( classMetadata != null )
		{
			// @ServiceClass
			if ( classMetadata.isServiceClass() )
				return ((ServiceClassMetadata) classMetadata).getMethodsWithServiceMethod().stream()
						.map(m -> new MethodDescriptor(m)).collect(Collectors.toList());
			
			// @ServiceDAO
			if ( classMetadata.isServiceDAO() )
				return ((ServiceDAOMetadata) classMetadata).getMethods().stream()
						.map(m -> new MethodDescriptor(m)).collect(Collectors.toList());
		}

		return new ArrayList<MethodDescriptor>();
	}

	/**
	 * Salva o arquivo (classe/jar) no diretorio de upload se o mesmo for valido.
	 */
	public synchronized boolean saveUploadedFile(InputStream fileStream, String fileName) throws Exception
	{
		// salva uma copia do stream (pois o metodo de validacao consome o stream
		// informado,
		// nao permitindo utiliza-lo novamente)
		ByteArrayInputStream streamCopy = new ByteArrayInputStream(IOUtils.toByteArray(fileStream));

		// verifica se o arquivo (classe/jar) eh valido
		Object valid = fileName.endsWith(".jar") ? ValidationService.getInstance().checkJarFile(streamCopy, fileName)
				: ValidationService.getInstance().checkClassFile(streamCopy, fileName);

		if (valid != null)
		{
			// arquivo de destino
			File file = new File(Utils.getInstance().getUploadDir() + File.separator + fileName);

			// salva o arquivo no diretorio de upload
			streamCopy.reset();
			FileUtils.copyInputStreamToFile(streamCopy, file);
			
			// atualiza o cache
			if (valid instanceof Class<?>)
			{
				Class<?> clazz = (Class<?>) valid;
				this.cacheServiceClass(clazz);
			}
			else
			{
				@SuppressWarnings("unchecked")
				List<Class<?>> jarClassList = (List<Class<?>>) valid;
				for (Class<?> clazz : jarClassList)
					this.cacheServiceClass(clazz);
				
				// informa o EntityManagerFactory sobre o JAR para carregar as entidades
				EntityManagerFactoryHelper.getInstance().loadEntitiesFromJar(file.getAbsolutePath());
				EntityManagerFactoryHelper.getInstance().reload();
			}

			return true;
		}

		return false;
	}
	
	/**
	 * Retorna os arquivos de classes/jar do diretorio de upload.
	 */
	public List<File> getUploadedFiles()
	{
		return new ArrayList<File>(FileUtils.listFiles(new File(Utils.getInstance().getUploadDir()),
				new String[] { "class", "jar" }, false));
	}
}