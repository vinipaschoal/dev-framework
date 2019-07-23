package org.esfinge.virtuallab.domain;

import org.esfinge.virtuallab.annotations.ServiceMethod;

public class TarefaInvalida
{
	private String nome = "compromissos";

	@ServiceMethod
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
}