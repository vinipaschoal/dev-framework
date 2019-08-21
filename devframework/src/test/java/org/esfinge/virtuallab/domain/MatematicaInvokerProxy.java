package org.esfinge.virtuallab.domain;

import org.esfinge.virtuallab.api.InvokerProxy;
import org.esfinge.virtuallab.api.annotations.Invoker;
import org.esfinge.virtuallab.api.annotations.Param;
import org.esfinge.virtuallab.api.annotations.ParamAttribute;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;

@ServiceClass(label = "MatematicaService @Invoker", description = "Demonstra a utilizacao de proxy para invocar um servico disponivel (@Invoker e @InvokerProxy)")
public class MatematicaInvokerProxy
{
	@Invoker
	private InvokerProxy invoker;

	
	@ServiceMethod(label = "Distancia entre Pontos @InvokerProxy", description = "Invoca o metodo 'calcular distancia entre pontos' do servico MatematicaService")
	public double calcularDistanciaProxy(
			@Param(label = "Ponto A", fields = {@ParamAttribute(name = "x", label = "Coordenada X"), @ParamAttribute(name = "y", label = "Coordenada Y")}) Ponto p1,
			@Param(label = "Ponto B", fields = {@ParamAttribute(name = "x", label = "Coordenada X"), @ParamAttribute(name = "y", label = "Coordenada Y")}) Ponto p2)
	{
		double resultado = invoker.invoke("org.esfinge.virtuallab.domain.MatematicaService", "calcularDistancia", double.class, p1, p2);
		return resultado;
	}
	
	@ServiceMethod(label = "Raiz Quadrada @InvokerProxy", description = "Invoca o metodo 'calcular raiz quadrada' do servico MatematicaService")
	public double calcularRaizQuadradaProxy(double numero)
	{
		double resultado = invoker.invoke("org.esfinge.virtuallab.domain.MatematicaService", "calcularRaizQuadrada", double.class, numero);
		return resultado;
	}
}
