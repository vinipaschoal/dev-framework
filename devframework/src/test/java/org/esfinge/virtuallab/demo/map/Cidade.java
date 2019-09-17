package org.esfinge.virtuallab.demo.map;

public class Cidade
{
	private String nome;
	private String estado;
	private double latitude;
	private double longitude;

	
	public Cidade()
	{

	}

	public Cidade(String nome, String estado, double latitude, double longitude)
	{
		super();
		this.nome = nome;
		this.estado = estado;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getEstado()
	{
		return estado;
	}

	public void setEstado(String estado)
	{
		this.estado = estado;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}
}
