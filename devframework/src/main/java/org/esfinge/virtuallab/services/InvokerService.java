package org.esfinge.virtuallab.services;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.commons.lang3.ClassUtils;
import org.esfinge.virtuallab.api.InvokerProxy;
import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.descriptors.ParameterDescriptor;
import org.esfinge.virtuallab.exceptions.InvocationException;
import org.esfinge.virtuallab.metadata.ClassMetadata;
import org.esfinge.virtuallab.metadata.MetadataHelper;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.utils.Utils;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;

import net.sf.esfinge.querybuilder.QueryBuilder;

/**
 * Invoca metodos em classes de servico. 
 */
public class InvokerService implements InvokerProxy, InvocationHandler
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
	public synchronized Object call(MethodDescriptor methodDescriptor, Object... paramValues) throws InvocationException
	{
		// verifica se foi especificado um descritor de metodo
		Utils.throwIfNull(methodDescriptor, InvocationException.class, "O descritor do metodo a ser invocado nao pode ser nulo!");
		
		try
		{
			// obtem o metodo a ser invocado
			Method method = ReflectionUtils.getMethod(methodDescriptor);
			
			// tenta invocar o metodo
			return this.call(method, paramValues);
		}
		catch ( Exception exc )
		{
			// verifica se a excecao veio do metodo 'call'
			if ( exc instanceof InvocationException )
				throw (InvocationException) exc;
			
			// excecao veio do metodo 'ReflectionUtils.getMethod'
			throw new InvocationException(String.format("Erro ao tentar invocar metodo '%s' da classe '%s'!", 
					methodDescriptor.getName(), methodDescriptor.getClassName()), exc);
		}
	}
	
	/**
	 * Invoca o metodo com os parametros informados.
	 */
	public synchronized Object call(Method method, Object... paramValues) throws InvocationException
	{
		// verifica se foi especificado um descritor de metodo
		Utils.throwIfNull(method, InvocationException.class, "O metodo a ser invocado nao pode ser nulo!");

		try
		{
			// verifica se o metodo eh valido
			ValidationService.getInstance().assertValidMethod(method);
			
			// recupera a classe do metodo a ser invocado
			Class<?> clazz = ClassLoaderService.getInstance().getService(method.getDeclaringClass().getCanonicalName());
			ClassMetadata classMetadata = MetadataHelper.getInstance().getClassMetadata(clazz);
			
			// cria um objeto da classe cujo metodo sera invocado
			Object obj;
			
			// @ServiceClass
			if ( classMetadata.isServiceClass() )
			{
				obj = clazz.newInstance();
				
				// injeta o servico nos campos que tem a anotacao @InvokerProxy
				ReflectionUtils.getDeclaredFieldsAnnotatedWith(clazz, org.esfinge.virtuallab.api.annotations.Invoker.class).forEach(f -> {
					try
					{
						ReflectionUtils.setFieldValue(obj, f.getName(), InvokerService.this);
					}
					catch ( Exception e )
					{
						throw new InvocationException(String.format("Erro ao injetar objeto InvokerProxy no campo '%s' da classe '%s'!",
								f.getName(), clazz.getCanonicalName()), e);
					}
				});
				
				// injeta o servico nos campos que tem a anotacao @Inject
				ReflectionUtils.getDeclaredFieldsAnnotatedWith(clazz, org.esfinge.virtuallab.api.annotations.Inject.class).forEach(f -> {
					try
					{
						ReflectionUtils.setFieldValue(obj, f.getName(), this.createProxyFor(f.getType()));
					}
					catch ( Exception e )
					{
						throw new InvocationException(String.format("Erro ao injetar objeto '%s' no campo '%s' da classe '%s'!",
								f.getType().getCanonicalName(), f.getName(), clazz.getCanonicalName()), e);
					}
				});
				
				// invoca o metodo
				return method.invoke(obj, paramValues);
			}
			
			// @ServiceDAO
			else
			{
				try
				{
					// seleciona o DataSource relacionado com a classe
					DataSourceService.setDataSourceFor(clazz);
					
					// cria a classe do QueryBuilder
					obj = QueryBuilder.create(clazz);
					
					// invoca o metodo
					return Proxy.getInvocationHandler(obj).invoke(obj, method, paramValues);
				}
				finally
				{
					// libera o DataSource
					DataSourceService.clear();
				}
			}
			
		}
		catch ( Throwable exc )
		{
			throw new InvocationException(String.format("Erro ao tentar invocar metodo '%s' da classe '%s'!", 
					method.getName(), method.getDeclaringClass().getCanonicalName()), exc);
		}
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public <E> E invoke(String qualifiedClassName, String methodName, Class<E> returnType, Object... paramValues) throws InvocationException
	{
		// cria o descritor do metodo a ser invocado
		MethodDescriptor md = new MethodDescriptor();
		md.setClassName(qualifiedClassName);
		md.setName(methodName);
		
		// monta os descritores dos parametros
		if (! Utils.isNullOrEmpty(paramValues) )
			for (int i = 0; i < paramValues.length; i++)
			{
				ParameterDescriptor pd = new ParameterDescriptor();
				pd.setIndex(i);
				pd.setDataType(paramValues[i].getClass().getCanonicalName());
				md.getParameters().add(pd);
			}
		
		// tenta invocar o metodo
		Object result = this.call(md, paramValues);
		
		// cast do resultado para o tipo de retorno informado
		if ( returnType.isPrimitive() )
			return (E) ClassUtils.primitiveToWrapper(returnType).cast(result);
		else
			return returnType.cast(result);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		return this.call(method, args);
	}

	/**
	 * Cria um proxy para a classe informada.
	 */
	private <E> E createProxyFor(Class<E> superclass)
	{
		// CGLIB
		Enhancer proxy = new Enhancer();
//		proxy.setClassLoader(ClassLoaderService.class.getClassLoader());
		proxy.setClassLoader(ClassLoaderService.getInstance().getService(superclass.getCanonicalName()).getClassLoader());
		proxy.setSuperclass(superclass);
		proxy.setCallback(InvokerService.getInstance());
		
		return superclass.cast(proxy.create());
	}
}