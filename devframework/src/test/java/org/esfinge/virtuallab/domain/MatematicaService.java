package org.esfinge.virtuallab.domain;

import org.esfinge.virtuallab.api.annotations.Param;
import org.esfinge.virtuallab.api.annotations.ParamAttribute;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;

@ServiceClass
public class MatematicaService
{
	@ServiceMethod(label = "Distancia entre Pontos", description = "Calcula a distancia entre dois pontos")
	public double calcularDistancia(
			@Param(label = "Ponto A", fields = {@ParamAttribute(name = "x", label = "Coordenada X"), @ParamAttribute(name = "y", label = "Coordenada Y")}) Ponto p1,
			@Param(label = "Ponto B") Ponto p2)
	{
		double a = Math.pow(p2.getX() - p1.getX(), 2);
		double b = Math.pow(p2.getY() - p1.getY(), 2);
		return ( Math.sqrt(a + b));
	}
	
	@ServiceMethod(label = "Raiz Quadrada")
	public double calcularRaizQuadrada(double numero)
	{
		return Math.sqrt(numero);
	}
}
