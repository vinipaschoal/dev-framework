package org.esfinge.virtuallab.domain;

import org.esfinge.virtuallab.annotations.Label;
import org.esfinge.virtuallab.annotations.ServiceClass;
import org.esfinge.virtuallab.annotations.ServiceMethod;

@ServiceClass(description = "Disponibiliza serviços relacionados a tarefas, no estilo ToDo.")
@Label("Tarefas / ToDo")
public class Tarefa
{
	private String task = "Nenhuma tarefa cadastrada!";

	@ServiceMethod(description = "Retorna o nome da tarefa")
	@Label("Obter tarefa")
	public String getTask()
	{
		return task;
	}

	@ServiceMethod
	@Label("Cadastrar tarefa")
	public String setTask(@Label("Tarefa") String name, @Label("Prioridade") int priority)
	{
		return String.format("Tarefa %s cadastrada com prioridade %d", name, priority);
	}

	@ServiceMethod(description = "Retorna os valores de prioridade permitidos")
	@Label("Listar prioridades")
	public int[] getPriorityRange()
	{
		return new int[] { 1, 2, 3, 4, 5 };
	}
}