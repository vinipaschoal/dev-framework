package org.esfinge.virtuallab.demo.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Temperatura
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String latitude;
	private String longitude;
	private String local;
	private double maxima;
	private double minima;
	private String mes;
	
	public Temperatura()
	{		
	}
	
	public Temperatura(Long id, String lat, String lng, String local, double max, double min, String mes)
	{
		this.id = id;
		this.latitude = lat;
		this.longitude = lng;
		this.local = local;
		this.maxima = max;
		this.minima = min;
		this.mes = mes;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getLatitude()
	{
		return latitude;
	}

	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}

	public String getLongitude()
	{
		return longitude;
	}

	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}
	
	public String getLocal()
	{
		return local;
	}
	
	public void setLocal(String local)
	{
		this.local = local;
	}

	public double getMaxima()
	{
		return maxima;
	}

	public void setMaxima(double maxima)
	{
		this.maxima = maxima;
	}

	public double getMinima()
	{
		return minima;
	}

	public void setMinima(double minima)
	{
		this.minima = minima;
	}

	public String getMes()
	{
		return mes;
	}

	public void setMes(String mes)
	{
		this.mes = mes;
	}
}
