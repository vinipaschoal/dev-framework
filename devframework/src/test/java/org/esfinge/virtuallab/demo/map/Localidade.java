package org.esfinge.virtuallab.demo.map;

public class Localidade
{
	private String nome;
	private String observacoes;
	private double posLat;
	private double posLong;
	
	
	public Localidade()
	{
		
	}

	public Localidade(String nome, String observacoes, double posLat, double posLong)
	{
		super();
		this.nome = nome;
		this.observacoes = observacoes;
		this.posLat = posLat;
		this.posLong = posLong;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getObservacoes()
	{
		return observacoes;
	}

	public void setObservacoes(String observacoes)
	{
		this.observacoes = observacoes;
	}

	public double getPosLat()
	{
		return posLat;
	}

	public void setPosLat(double posLat)
	{
		this.posLat = posLat;
	}

	public double getPosLong()
	{
		return posLong;
	}

	public void setPosLong(double posLong)
	{
		this.posLong = posLong;
	}
}
