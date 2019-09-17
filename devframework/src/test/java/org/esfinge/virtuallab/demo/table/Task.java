package org.esfinge.virtuallab.demo.table;

public class Task
{
	private int id;
	private int priority;
	private String name;
	private boolean completed;
	

	public Task()
	{

	}

	public Task(int id, int priority, String name, boolean completed)
	{
		super();
		this.id = id;
		this.priority = priority;
		this.name = name;
		this.completed = completed;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isCompleted()
	{
		return completed;
	}

	public void setCompleted(boolean completed)
	{
		this.completed = completed;
	}
}