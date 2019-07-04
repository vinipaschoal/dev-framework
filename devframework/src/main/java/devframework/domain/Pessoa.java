package devframework.domain;

import java.util.Date;

public class Pessoa {
	
	private String nome;
	private int idade;
	private String endereco;
	private Date dataNascimento;

	public Pessoa(String nome, int idade, String endereco, Date dataNascimento) {
		super();
		this.nome = nome;
		this.idade = idade;
		this.endereco = endereco;
		this.dataNascimento = dataNascimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNacimento) {
		this.dataNascimento = dataNascimento;
	}

}
