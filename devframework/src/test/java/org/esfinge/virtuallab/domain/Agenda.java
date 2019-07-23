package org.esfinge.virtuallab.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.esfinge.virtuallab.annotations.HtmlTableReturn;
import org.esfinge.virtuallab.annotations.JsonReturn;
import org.esfinge.virtuallab.annotations.ServiceClass;
import org.esfinge.virtuallab.annotations.ServiceMethod;

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
	
	@ServiceMethod
	@HtmlTableReturn
	public List<Pessoa> getPessoaHtml1() {
		Pessoa p = new Pessoa("José", 50, "rua das acacias",new Date());
		List<Pessoa> lista = new ArrayList<Pessoa>();
		lista.add(p);
		return lista;
	}
	
	@ServiceMethod
	@HtmlTableReturn
	public List<Pessoa> getPessoaHtml2() {
		Pessoa p = new Pessoa("José", 50, "rua das acacias",new Date());
		Pessoa p2 = new Pessoa("Pedro", 49, "rua das palmeiras",new Date());
		List<Pessoa> lista = new ArrayList<Pessoa>();
		lista.add(p);
		lista.add(p2);
		return lista;
	}
	
	@ServiceMethod
	@HtmlTableReturn
	public List<Pessoa> getPessoaHtml3() {
		return new ArrayList<Pessoa>();
	}


	public Pessoa getPessoaSemAnotacao() {
		return new Pessoa("José", 50, "rua das acacias",new Date());
	}

	@ServiceMethod
	public Pessoa getPessoa() {
		return new Pessoa("José", 50, "rua das acacias",new Date());
	}

}