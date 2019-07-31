package org.esfinge.virtuallab.services;

import java.lang.reflect.Method;

import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.descriptors.ParameterDescriptor;

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
	public Object call(MethodDescriptor methodDescriptor, Object... paramValues) throws Exception
	{
		// obtem a classe que contem o metodo
		Class<?> clazz = Class.forName(methodDescriptor.getClassName());
		
		// procura na classe o metodo que sera invocado
		Method method = this.searchMatchingMethod(clazz, methodDescriptor);
		
		// invoca o metodo
		return method.invoke(clazz.newInstance(), paramValues);
	}
	
	/**
	 * Procura pelo metodo na classe.
	 */
	private Method searchMatchingMethod(Class<?> clazz, MethodDescriptor methodDescriptor)
	{
		for (Method m : clazz.getMethods())
			if (m.getParameterCount() == methodDescriptor.getParameters().size())
				if (m.getName().equals(methodDescriptor.getName()))
				{
					Class<?>[] paramTypes = m.getParameterTypes();
					for (ParameterDescriptor p : methodDescriptor.getParameters())
						if(!p.getDataType().equals(paramTypes[p.getIndex()].getCanonicalName()))
							break;
					
					// encontrou o metodo
					return m;
				}
		
		// nao encontrou o metodo, erro!
		throw new IllegalArgumentException("Metodo '" + methodDescriptor.getName() + "' não encontrado na clase '" + clazz.getCanonicalName());
	}
}