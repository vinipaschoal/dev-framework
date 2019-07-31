package org.esfinge.virtuallab.domain;

import org.esfinge.virtuallab.annotations.Label;
import org.esfinge.virtuallab.annotations.ServiceClass;
import org.esfinge.virtuallab.annotations.ServiceMethod;

@ServiceClass(description="Disponibiliza serviços relacionados a tarefas, no estilo TO-DO.")
@Label("ToDo")
public class Tarefa {
	
	private String nome = "compromissos";

	@ServiceMethod(description="Retorna o nome da tarefa")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
	
	@ServiceMethod
	@Label("Obter prioridade")
	public int getPrioridade(@Label("Categoria") String categoria, @Label("Prioridade") int prioridade) {
		return 1;
	}
}