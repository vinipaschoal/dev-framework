package org.esfinge.virtuallab.services;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.esfinge.virtuallab.metadata.ClassMetadata;
import org.esfinge.virtuallab.metadata.MetadataHelper;
import org.esfinge.virtuallab.utils.Utils;

/**
 * Responsavel em validar as classes e servicos.
 */
public final class ValidationService
{
	// instancia unica da classe
	private static ValidationService _instance;

	/**
	 * Construtor interno.
	 */
	private ValidationService()
	{
	}

	/**
	 * Singleton.
	 */
	public synchronized static ValidationService getInstance()
	{
		if (_instance == null)
			_instance = new ValidationService();

		return _instance;
	}

	/**
	 * Retorna a classe se ela for valida.
	 */
	public Class<?> checkClass(String classFilePath) throws Exception
	{
		return this.checkClass(FileUtils.openInputStream(new File(classFilePath)),
				FilenameUtils.getName(classFilePath));
	}

	/**
	 * Retorna a classe se ela for valida.
	 */
	public Class<?> checkClass(InputStream classFileStream, String fileName) throws Exception
	{
		List<Class<?>> classList = this.validateInternal(classFileStream, fileName);

		return (Utils.isNullOrEmpty(classList) ? null : classList.get(0));
	}

	/**
	 * Retorna as classes validas do arquivo jar.
	 */
	public List<Class<?>> checkJar(String jarFilePath) throws Exception
	{
		return this.checkJar(FileUtils.openInputStream(new File(jarFilePath)), FilenameUtils.getName(jarFilePath));
	}

	/**
	 * Retorna as classes validas do arquivo jar.
	 */
	public List<Class<?>> checkJar(InputStream jarFileStream, String fileName) throws Exception
	{
		return this.validateInternal(jarFileStream, fileName);
	}

	/**
	 * Retorna as classes validas.
	 */
	private List<Class<?>> validateInternal(InputStream fileStream, String fileName) throws Exception
	{
		List<Class<?>> classList = new ArrayList<>();
		List<Class<?>> validClasses = new ArrayList<>();
		
		// carrega as classes do jar
		if (fileName.endsWith(".jar"))
			classList.addAll(ClassLoaderService.getInstance().loadJar(fileStream));
		else
			classList.add(ClassLoaderService.getInstance().loadClass(fileStream, fileName));

		// 
		MetadataHelper metadataHelper = MetadataHelper.getInstance();
		
		for (Class<?> clazz : classList)
		{
			// obtem os metadados da classe
			ClassMetadata metadata = metadataHelper.getClassMetadata(clazz);
			
			// verifica se esta anotada com @ClassService e possui metodos @ServiceMethod
			if (metadata.isAnnotatedWithServiceClass() && !Utils.isNullOrEmpty(metadata.getMethodsWithServiceMethod()))
				validClasses.add(clazz);
			
			// TODO: verificar se os parametros sao dos tipos validos (primitivos: integer, number, boolean, string, Array, Collection, objeto simples!)
		}
		
		return (validClasses.size() == 0 ? null : validClasses);
	}

}
