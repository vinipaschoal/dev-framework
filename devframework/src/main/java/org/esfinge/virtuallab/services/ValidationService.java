package org.esfinge.virtuallab.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceDAO;
import org.esfinge.virtuallab.exceptions.ClassLoaderException;
import org.esfinge.virtuallab.exceptions.ValidationException;
import org.esfinge.virtuallab.metadata.ClassMetadata;
import org.esfinge.virtuallab.metadata.MetadataHelper;
import org.esfinge.virtuallab.utils.ReflectionUtils;
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
			
			// verifica se esta anotada com @ServiceClass ou @ServiceDAO
			if ( !metadata.isServiceClass() && !metadata.isServiceDAO() )
				throw new ValidationException(String.format("A classe '%s' nao possui a anotacao @ServiceClass ou @ServiceDAO!", clazz.getCanonicalName()));
			
			// ok, classe valida!
		}
		catch ( Exception e )
		{
			throw new ValidationException(String.format("Erro ao validar a classe '%s'!", clazz.getCanonicalName()), e);
		}
	}

	/**
	 * Verifica se o metodo eh valido.
	 */
	public void checkMethod(Method method) throws ValidationException
	{
		//
		Utils.throwIfNull(method, ValidationException.class,  "O metodo nao pode ser nulo!");
		
		// obtem a classe do metodo
		Class<?> methodClass = method.getDeclaringClass();
		
		// verifica se a classe esta anotada com @ServiceClass ou @ServiceDAO
		boolean serviceClass = methodClass.getAnnotation(ServiceClass.class) != null;
		serviceClass |= methodClass.getAnnotation(ServiceDAO.class) != null;
		
		if (! serviceClass )
			throw new ValidationException(String.format("A classe '%s' nao possui a anotacao @ServiceClass ou @ServiceDAO!", methodClass.getCanonicalName()));

		// verifica o tipo do retorno
		Class<?> returnClass = method.getReturnType();
		
		// nao pode ser void
		if ( returnClass.equals(void.class) )
			throw new ValidationException(String.format("O metodo '%s' deve retornar um objeto valido! (nao pode ser void)", method.getName()));
		
		// tipos validos: basicos / array / colecao / objeto valido
		if (! ReflectionUtils.isBasicType(returnClass) )
			if (! ReflectionUtils.isArray(returnClass) )
				if (! ReflectionUtils.isCollection(returnClass) )
					if (! ReflectionUtils.isFlatObject(returnClass) )
						throw new ValidationException(String.format("O metodo '%s' retorna um objeto nao suportado (%s)", 
								method.getName(), returnClass.getCanonicalName()));
		
		// verifica os tipos dos parametros
		// tipos validos: basico / objeto valido
		for ( Class<?> paramClass : method.getParameterTypes() )
			if (! ReflectionUtils.isBasicType(paramClass) )
				if (! ReflectionUtils.isFlatObject(paramClass) )
					throw new ValidationException(String.format("O metodo '%s' possui um parametro nao suportado (%s)", 
							method.getName(), paramClass.getCanonicalName()));
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
