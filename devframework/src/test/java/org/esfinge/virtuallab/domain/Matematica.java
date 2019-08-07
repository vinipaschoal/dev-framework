package org.esfinge.virtuallab.domain;

import org.esfinge.virtuallab.annotations.Label;
import org.esfinge.virtuallab.annotations.ServiceClass;
import org.esfinge.virtuallab.annotations.ServiceMethod;

@ServiceClass
public class Matematica
{
	@ServiceMethod
	@Label("Distancia entre Pontos")
	public double calcularDistancia(@Label("Ponto A") Ponto p1, @Label("Ponto B") Ponto p2)
	{
		double a = Math.pow(p2.getX() - p1.getX(), 2);
		double b = Math.pow(p2.getY() - p1.getY(), 2);
		return ( Math.sqrt(a + b));
	}
	
	@ServiceMethod
	@Label("Raiz Quadrada")
	public double calcularRaizQuadrada(double numero)
	{
		return Math.sqrt(numero);
	}
}
