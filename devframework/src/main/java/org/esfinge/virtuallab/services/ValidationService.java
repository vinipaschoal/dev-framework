package org.esfinge.virtuallab.services;

import java.io.File;
import java.lang.reflect.Method;

import org.esfinge.virtuallab.exceptions.MetadataException;
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
	 * Verifica se eh um arquivo de servico valido, retornando os metadados da classe de servico.
	 */
	public ClassMetadata validateUploadedFile(File file) throws ValidationException
	{
		try
		{
			// tenta carregar o servico (class ou jar)
			Class<?> serviceClass = ClassLoaderService.getInstance().loadService(file);
			
			// verifica se eh uma classe de servico valida
			this.assertValidService(serviceClass);
			
			// retorna os metadados da classe de servico
			return MetadataHelper.getInstance().getClassMetadata(serviceClass);
		}
		catch (Exception e) 
		{
			throw new ValidationException(String.format("Erro ao validar arquivo de upload: '%s'", file.getName()), e);
		}
	}

	/**
	 * Assegura que a classe de servico seja valida.
	 */
	public void assertValidService(Class<?> clazz) throws ValidationException
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
		catch ( MetadataException e )
		{
			// erro na validacao dos metadados da classe
			throw new ValidationException(String.format("Erro ao validar a classe '%s'!", clazz.getCanonicalName()), e);
		}
	}

	/**
	 * Assegura que o metodo de servico seja valido.
	 */
	public void assertValidMethod(Method method) throws ValidationException
	{
		//
		Utils.throwIfNull(method, ValidationException.class,  "O metodo nao pode ser nulo!");
		
		// verifica o tipo do retorno
		Class<?> returnClass = method.getReturnType();
		
		// nao pode ser void
		if ( returnClass.equals(void.class) )
			throw new ValidationException(String.format("O metodo '%s' deve retornar um objeto valido! (nao pode ser void)", method.getName()));
		
		// tipos validos: basicos / array / colecao / mapa / objeto valido
		if (! ReflectionUtils.isBasicType(returnClass) )
			if (! ReflectionUtils.isArray(returnClass) )
				if (! ReflectionUtils.isCollection(returnClass) )
					if (! ReflectionUtils.isMap(returnClass) )
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
}
