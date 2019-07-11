package devframework.domain;

import devframework.annotations.ServiceMethod;

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