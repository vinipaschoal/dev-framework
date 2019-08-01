package org.esfinge.virtuallab.domain;

import org.esfinge.virtuallab.annotations.Label;
import org.esfinge.virtuallab.annotations.ServiceClass;
import org.esfinge.virtuallab.annotations.ServiceMethod;

@ServiceClass(description="Disponibiliza serviços relacionados a tarefas, no estilo TO-DO.")
@Label("ToDo")
public class Tarefa {
	private String tarefas = "Nenhuma tarefa cadastrada!";

	@ServiceMethod(description="Retorna o nome da tarefa")
	public String getNome() {
		return tarefas;
	}

	@ServiceMethod
	@Label("Cadastrar tarefa")
	public String setTask(@Label("Tarefa") String nomeTarefa, @Label("Prioridade") int priorTarefa) {
		return String.format("Tarefa '%s' cadastrada com prioridade '%d'", nomeTarefa, priorTarefa);
	}
}