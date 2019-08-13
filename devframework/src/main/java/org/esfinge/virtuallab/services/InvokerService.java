package org.esfinge.virtuallab.services;

import java.lang.reflect.Method;

import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.exceptions.InvocationException;
import org.esfinge.virtuallab.utils.ReflectionUtils;

/**
 * Invoca metodos em classes de servico. 
 */
public class InvokerService
{
	// instancia unica da classe
	private static InvokerService _instance;

	
 	/**
	 * Construtor interno.
	 */
	private InvokerService()
	{
		
	}
	
	/**
	 * Singleton.
	 */
	public static InvokerService getInstance()
	{
		if (_instance == null)
			_instance = new InvokerService();

		return _instance;
	}
	
	/**
	 * Invoca o metodo com os parametros informados.
	 */
	public Object call(MethodDescriptor methodDescriptor, Object... paramValues) throws InvocationException
	{
		// verifica se foi especificado um descritor de metodo
		if ( methodDescriptor == null )
			throw new InvocationException("O descritor do metodo a ser invocado nao pode ser nulo!");
		
		try
		{
			// obtem o metodo a ser invocado
			Method method = ReflectionUtils.getMethod(methodDescriptor);
			
			// cria um objeto da classe cujo metodo sera invocado
			Object obj = ReflectionUtils.findClass(methodDescriptor.getClassName()).newInstance();
			
			// invoca o metodo
			return method.invoke(obj, paramValues);
		}
		catch ( Exception exc )
		{
			throw new InvocationException("Erro ao tentar invocar metodo!", exc);
		}
	}
}