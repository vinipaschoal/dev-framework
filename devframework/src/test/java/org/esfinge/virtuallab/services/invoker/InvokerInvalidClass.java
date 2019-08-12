package org.esfinge.virtuallab.services.invoker;

public class InvokerInvalidClass
{
	// construtor privado, InvokerService nao deve conserguir instanciar
	private InvokerInvalidClass()
	{
	}
	
	public String validMethod(String param1, int param2)
	{
		return param1 + param2;
	}
}
