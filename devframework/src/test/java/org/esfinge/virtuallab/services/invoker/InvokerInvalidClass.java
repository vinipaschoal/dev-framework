package org.esfinge.virtuallab.services.invoker;

import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;

@ServiceClass
public class InvokerInvalidClass
{
	// construtor privado, InvokerService nao deve conserguir instanciar
	private InvokerInvalidClass()
	{
	}
	
	@ServiceMethod
	public String validMethod(String param1, int param2)
	{
		return param1 + param2;
	}
}
