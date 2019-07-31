package org.esfinge.virtuallab.domain;

import org.esfinge.virtuallab.annotations.ServiceClass;
import org.esfinge.virtuallab.annotations.ServiceMethod;

@ServiceClass
public class TarefaInvalida
{
	private String nome = "compromissos";
	
	protected TarefaInvalida() {}

	@ServiceMethod
	public String getNome() {
		return nome;
	}

//	@ServiceMethod
	public void setNome(String nome) {
		this.nome = nome;
	}
	
//	@ServiceMethod
	protected int getPrioridade() {
		return 1;
	}
}