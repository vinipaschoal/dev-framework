package org.esfinge.virtuallab.services.invoker;

@SuppressWarnings("unused")
public class InvokerValidClass
{
	public InvokerValidClass()
	{
	}
	
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
