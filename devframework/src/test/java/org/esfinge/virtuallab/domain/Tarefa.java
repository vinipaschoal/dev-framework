package org.esfinge.virtuallab.domain;

public class Tarefa
{
	private int id;
	private int prioridade;
	private String nome;
	private boolean completada;
	
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getPrioridade()
	{
		return prioridade;
	}

	public void setPrioridade(int prioridade)
	{
		this.prioridade = prioridade;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public boolean isCompletada()
	{
		return completada;
	}

	public void setCompletada(boolean completada)
	{
		this.completada = completada;
	}
}