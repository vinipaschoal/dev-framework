package devframework.domain;

import java.util.Date;

import devframework.annotations.JsonReturn;
import devframework.annotations.ServiceClass;
import devframework.annotations.ServiceMethod;

@ServiceClass
public class Agenda {

	private Pessoa pessoa = new Pessoa("Joao", 50, "rua acacias", new Date(System.currentTimeMillis()));

	@ServiceMethod
	@JsonReturn
	public Pessoa getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}