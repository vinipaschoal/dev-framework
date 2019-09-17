package org.esfinge.virtuallab.demo.json;

public class Disciplina implements Comparable<Disciplina>
{
	private String codigo;
	private String nome;
	private String docente;
	private int periodo;
	private int creditos;
		
	
	public Disciplina()
	{
		
	}

	public Disciplina(String codigo, String nome, String docente, int periodo, int creditos)
	{
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.docente = docente;
		this.periodo = periodo;
		this.creditos = creditos;
	}

	public String getCodigo()
	{
		return codigo;
	}

	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getDocente()
	{
		return docente;
	}

	public void setDocente(String docente)
	{
		this.docente = docente;
	}

	public int getPeriodo()
	{
		return periodo;
	}

	public void setPeriodo(int periodo)
	{
		this.periodo = periodo;
	}

	public int getCreditos()
	{
		return creditos;
	}

	public void setCreditos(int creditos)
	{
		this.creditos = creditos;
	}

	@Override
	public int compareTo(Disciplina o)
	{
		return this.codigo.compareTo(o.codigo);
	}
}
