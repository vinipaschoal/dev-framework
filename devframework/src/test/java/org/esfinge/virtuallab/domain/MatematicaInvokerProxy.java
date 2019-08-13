package org.esfinge.virtuallab.domain;

import org.esfinge.virtuallab.api.InvokerProxy;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;

@ServiceClass
public class MatematicaInvokerProxy
{
	@org.esfinge.virtuallab.api.annotations.InvokerProxy
	private InvokerProxy invoker;
	
	@ServiceMethod(label = "Raiz Quadrada (INVOKER PROXY)")
	public double calcularRaizQuadrada(double numero)
	{
		double resultado = invoker.invoke("org.esfinge.virtuallab.domain.Matematica", "calcularRaizQuadrada", double.class, numero);
		return resultado;
	}
}
