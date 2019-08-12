package org.esfinge.virtuallab.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.esfinge.virtuallab.exceptions.ClassLoaderException;
import org.esfinge.virtuallab.exceptions.ValidationException;
import org.esfinge.virtuallab.metadata.ClassMetadata;
import org.esfinge.virtuallab.metadata.MetadataHelper;
import org.esfinge.virtuallab.metadata.MethodMetadata;
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
	public Class<?> checkClassFile(String classFilePath) throws ValidationException
	{
		try
		{
			return this.checkClassFile(FileUtils.openInputStream(new File(classFilePath)),
					FilenameUtils.getName(classFilePath));
		}
		catch (IOException e) 
		{
			throw new ValidationException("Erro ao acessar a classe a ser validada!", e);
		}
	}

	/**
	 * Retorna a classe se ela for valida.
	 */
	public Class<?> checkClassFile(InputStream classFileStream, String fileName) throws ValidationException
	{
		List<Class<?>> classList = this.validateInternal(classFileStream, fileName);

		return (Utils.isNullOrEmpty(classList) ? null : classList.get(0));
	}

	/**
	 * Retorna as classes validas do arquivo jar.
	 */
	public List<Class<?>> checkJarFile(String jarFilePath) throws ValidationException
	{
		try
		{
			return this.checkJarFile(FileUtils.openInputStream(new File(jarFilePath)), FilenameUtils.getName(jarFilePath));
		}
		catch (IOException e)
		{
			throw new ValidationException("Erro ao acessar o jar a ser validado!", e);
		}
	}

	/**
	 * Retorna as classes validas do arquivo jar.
	 */
	public List<Class<?>> checkJarFile(InputStream jarFileStream, String fileName) throws ValidationException
	{
		return this.validateInternal(jarFileStream, fileName);
	}
	
	/**
	 * Verifica se a classe eh valida.
	 */
	public void checkClass(Class<?> clazz) throws ValidationException
	{
		//
		Utils.throwIfNull(clazz, ValidationException.class,  "A classe nao pode ser nula!");
		
		try
		{
			// obtem os metadados da classe
			ClassMetadata metadata = MetadataHelper.getInstance().getClassMetadata(clazz);
			
			// verifica se esta anotada com @ClassService e possui metodos @ServiceMethod
			if (!metadata.isAnnotatedWithServiceClass())
				throw new ValidationException(String.format("A classe '%s' nao possui a anotacao @ServiceClass!", clazz.getCanonicalName()));
			
			// verifica se possui metodos @ServiceMethod
			if (Utils.isNullOrEmpty(metadata.getMethodsWithServiceMethod()))
				throw new ValidationException(String.format("A classe '%s' nao possui metodos com a anotacao @ServiceMethod!", clazz.getCanonicalName()));
			
			// ok, classe valida!
		}
		catch ( Exception e )
		{
			throw new ValidationException(String.format("Erro ao validar a classe '%s':", clazz.getCanonicalName()), e);
		}
	}

	/**
	 * Verifica se o metodo eh valido.
	 */
	public void checkMethod(Method method) throws ValidationException
	{
		//
		Utils.throwIfNull(method, ValidationException.class,  "O metodo nao pode ser nulo!");
		
		// obtem os metadados do metodo
		MethodMetadata metadata = MetadataHelper.getInstance().getMethodMetadata(method);
		
		// verifica se esta anotado com @ServiceMethod
		if (!metadata.isAnnotatedWithServiceMethod())
			throw new ValidationException(String.format("O metodo '%s' nao possui a anotacao @ServiceMethod!", method.getName()));
	}

	/**
	 * Retorna as classes validas.
	 */
	private List<Class<?>> validateInternal(InputStream fileStream, String fileName) throws ValidationException
	{
		try
		{
			List<Class<?>> classList = new ArrayList<>();
			List<Class<?>> validClasses = new ArrayList<>();
			
			// carrega as classes do jar
			if (fileName.endsWith(".jar"))
				classList.addAll(ClassLoaderService.getInstance().loadJar(fileStream));
			else
				classList.add(ClassLoaderService.getInstance().loadClass(fileStream, fileName));

			// verifica se as classes sao validas
			for (Class<?> clazz : classList)
			{
				try
				{
					this.checkClass(clazz);
					validClasses.add(clazz);
				}
				catch ( ValidationException e )
				{
					// classe invalida, ignora
				}
			}
				
			return (validClasses.size() == 0 ? null : validClasses);
		}
		catch ( ClassLoaderException e )
		{
			throw new ValidationException("Erro ao carregar classes para validacao!", e);
		}
	}
}
