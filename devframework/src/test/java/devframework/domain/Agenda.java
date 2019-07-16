package devframework.domain;

import java.util.Date;

import devframework.annotations.JsonReturn;
import devframework.annotations.ServiceClass;
import devframework.annotations.ServiceMethod;

@ServiceClass
public class Agenda {
	
	private int idade = 50;

	@ServiceMethod
	@JsonReturn
	public Pessoa getPessoaJson() {
		return new Pessoa("José", 50, "rua das acacias",new Date());
	}

	@ServiceMethod
	public int getPessoaPrimitivo() {
		return idade;
	}

	@ServiceMethod
	public String getPessoaComParametro(int a, String b) {
		return "pessoa com parametro int e String";
	}

	@ServiceMethod
	public String getPessoaComParametro(String a, int b) {
		return "pessoa com parametro String e int";
	}
	
	@ServiceMethod(alias="getPessoaComParametroStringLong")
	public String getPessoaComParametro(String a, long b) {
		return "pessoa com parametro String e long anotada com alias";
	}


	@ServiceMethod
	public void getPessoaSemRetorno() {

	}

	public Pessoa getPessoaSemAnotacao() {
		return new Pessoa("José", 50, "rua das acacias",new Date());
	}

	@ServiceMethod
	public Pessoa getPessoa() {
		return new Pessoa("José", 50, "rua das acacias",new Date());
	}

}