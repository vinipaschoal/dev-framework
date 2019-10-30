package org.esfinge.virtuallab.demo.chart.bars;

public class Votes
{
	private int total;
	private String region;
	private String color;

	
	public Votes()
	{

	}

	public Votes(int total, String region, String color)
	{
		super();
		this.total = total;
		this.region = region;
		this.color = color;
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}
}
