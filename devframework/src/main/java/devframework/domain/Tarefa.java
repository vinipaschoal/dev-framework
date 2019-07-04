package devframework.domain;

import devframework.annotations.ServiceClass;
import devframework.annotations.ServiceMethod;

@ServiceClass
public class Tarefa {
	
	private String nome = "compromissos";

	@ServiceMethod
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
}