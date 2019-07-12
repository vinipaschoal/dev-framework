package devframework.domain;

import java.util.Date;

import devframework.annotations.ServiceClass;

@ServiceClass
public class AgendaInvalida
{	
	private Pessoa pessoa = new Pessoa("Joao", 50, "rua acacias", new Date(System.currentTimeMillis()));

	public Pessoa getPessoaJson() {
		return pessoa;
	}
	
	public int getPessoaPrimitivo() {
		return pessoa.getIdade();
	}
	
	public void getPessoaSemRetorno() {
		
	}
	
	public Pessoa getPessoaSemAnotacao() {
		return pessoa;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
