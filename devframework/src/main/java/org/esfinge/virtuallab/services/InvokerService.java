package org.esfinge.virtuallab.services;

import java.lang.reflect.Method;

import org.esfinge.virtuallab.api.InvokerProxy;
import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.descriptors.ParameterDescriptor;
import org.esfinge.virtuallab.exceptions.InvocationException;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.utils.Utils;

/**
 * Invoca metodos em classes de servico. 
 */
public class InvokerService implements InvokerProxy
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
		Utils.throwIfNull(methodDescriptor, InvocationException.class, "O descritor do metodo a ser invocado nao pode ser nulo!");
		
		try
		{
			// recupera a classe do metodo a ser invocado
			Class<?> clazz = ReflectionUtils.findClass(methodDescriptor.getClassName());
			
			// verifica se a classe eh valida
			ValidationService.getInstance().checkClass(clazz);
			
			// obtem o metodo a ser invocado
			Method method = ReflectionUtils.getMethod(methodDescriptor);
			
			// verifica se o metodo eh valido
			ValidationService.getInstance().checkMethod(method);
			
			// cria um objeto da classe cujo metodo sera invocado
			Object obj = clazz.newInstance();
			
			// injeta o servico nos campos que tem a anotacao @InvokerProxy
			ReflectionUtils.getDeclaredFieldsAnnotatedWith(clazz, org.esfinge.virtuallab.api.annotations.InvokerProxy.class).forEach(f -> {
				try
				{
					f.set(obj, InvokerService.this);
				}
				catch ( Exception e )
				{
					throw new InvocationException(String.format("Erro ao injetar objeto InvokerProxy no campo '%s' da classe '%s'!",
							f.getName(), clazz.getCanonicalName()), e);
				}
			});
			
			// invoca o metodo
			return method.invoke(obj, paramValues);
		}
		catch ( Exception exc )
		{
			throw new InvocationException(String.format("Erro ao tentar invocar metodo '%s' da classe '%s'!", 
					methodDescriptor.getName(), methodDescriptor.getClassName()), exc);
		}
	}
	
	@Override
	public <E> E invoke(String qualifiedClassName, String methodName, Class<E> returnType, Object... paramValues)
			throws InvocationException
	{
		// cria o descritor do metodo a ser invocado
		MethodDescriptor md = new MethodDescriptor();
		md.setClassName(qualifiedClassName);
		md.setName(methodName);
		
		// monta os descritores dos parametros
		if ( Utils.isNullOrEmpty(paramValues) )
			for (int i = 0; i < paramValues.length; i++)
			{
				ParameterDescriptor pd = new ParameterDescriptor();
				pd.setIndex(i);
				pd.setDataType(paramValues[i].getClass().getCanonicalName());
			}
		
		// tenta invoca o metodo
		Object result = this.call(md, paramValues);
		
		// cast do resultado para o tipo de retorno informado 
		return returnType.cast(result);
	}
}