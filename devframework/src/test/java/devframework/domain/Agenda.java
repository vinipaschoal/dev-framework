package devframework.domain;

import devframework.annotations.JsonReturn;
import devframework.annotations.ServiceClass;
import devframework.annotations.ServiceMethod;

@ServiceClass
public class Agenda {
	
	private int idade = 50;

	@ServiceMethod
	@JsonReturn
	public Agenda getPessoaJson() {
		return this;
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

	public Agenda getPessoaSemAnotacao() {
		return this;
	}

	@ServiceMethod
	public Agenda getPessoa() {
		return this;
	}

}