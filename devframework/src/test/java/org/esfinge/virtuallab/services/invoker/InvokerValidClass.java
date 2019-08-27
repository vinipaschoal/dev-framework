package org.esfinge.virtuallab.services.invoker;

import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;

@SuppressWarnings("unused")
@ServiceClass
public class InvokerValidClass
{
	public InvokerValidClass()
	{
	}
	
	@ServiceMethod
	public String validMethod(String param1, int param2)
	{
		return param1 + param2;
	}
	
	// metodo privado, InvokerService nao deve conseguir invocar
	private String privateMethod(String param1, int param2)
	{
		return param1 + param2;
	}
}
