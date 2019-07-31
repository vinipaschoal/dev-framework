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
		MetadataHelper metadataHelper = MetadataHelper.getInstance();
		
		// obtem os arquivos de classes e jar do diretorio de upload
		for (File file : this.getUploadedFiles())
			try
			{
				// caminho para o arquivo
				String filePath = file.getAbsolutePath();

				// classe
				if (FilenameUtils.isExtension(filePath, "class"))
				{
					Class<?> clazz = validation.checkClass(filePath);
					if (clazz != null)
						this.classCache.put(clazz.getCanonicalName(), metadataHelper.getClassMetadata(clazz));
				}
				// jar
				else
				{
					// obtem as classes validas do jar
					List<Class<?>> jarClassList = validation.checkJar(filePath);
					if (jarClassList != null)
						for (Class<?> clazz : jarClassList)
							this.classCache.put(clazz.getCanonicalName(), metadataHelper.getClassMetadata(clazz));
				}
			}
			catch (Exception exc)
			{
				exc.printStackTrace();
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
			return classMetadata.getMethodsWithServiceMethod().stream()
					.map(m -> new MethodDescriptor(m)).collect(Collectors.toList());

		return null;
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
		Object valid = fileName.endsWith(".jar") ? ValidationService.getInstance().checkJar(streamCopy, fileName)
				: ValidationService.getInstance().checkClass(streamCopy, fileName);

		if (valid != null)
		{
			// arquivo de destino
			File file = new File(Utils.getInstance().getUploadDir() + File.separator + fileName);

			// salva o arquivo no diretorio de upload
			streamCopy.reset();
			FileUtils.copyInputStreamToFile(streamCopy, file);
			
			// atualiza o cache
			MetadataHelper metadataHelper = MetadataHelper.getInstance();
			if (valid instanceof Class<?>)
			{
				Class<?> clazz = (Class<?>) valid;
				this.classCache.put(clazz.getCanonicalName(), metadataHelper.getClassMetadata(clazz));
			}
			else
			{
				@SuppressWarnings("unchecked")
				List<Class<?>> jarClassList = (List<Class<?>>) valid;
				if (jarClassList != null)
					for (Class<?> clazz : jarClassList)
						this.classCache.put(clazz.getCanonicalName(), metadataHelper.getClassMetadata(clazz));
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