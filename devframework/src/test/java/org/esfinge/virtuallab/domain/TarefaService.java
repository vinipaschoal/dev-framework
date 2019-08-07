package org.esfinge.virtuallab.domain;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.esfinge.virtuallab.annotations.CustomReturn;
import org.esfinge.virtuallab.annotations.Label;
import org.esfinge.virtuallab.annotations.ServiceClass;
import org.esfinge.virtuallab.annotations.ServiceMethod;
import org.esfinge.virtuallab.annotations.TableReturn;

@ServiceClass(description = "Disponibiliza serviços relacionados a tarefas, no estilo ToDo.")
@Label("Tarefas / ToDo")
public class TarefaService
{
	@ServiceMethod(description = "Cria uma nova tarefa")
	@Label("Cadastrar tarefa (PLAIN - STRING)")
	public String setTask(@Label("Tarefa") String name, @Label("Prioridade") int priority)
	{
		return String.format("Tarefa %s cadastrada com prioridade %d", name, priority);
	}

	@ServiceMethod(description = "Retorna os valores de prioridade permitidos")
	@Label("Listar prioridades (PLAIN - ARRAY)")
	public int[] getPriorityRange()
	{
		return new int[] { 1, 2, 3, 4, 5 };
	}

	@ServiceMethod(description = "Obtem uma tarefa especifica")
	@Label("Obter tarefa (PLAIN - OBJETO)")
	public Tarefa getTask(int id)
	{
		return newTask("Uma tarefa");
	}
	
	@ServiceMethod(description = "Obtem uma tarefa especifica")
	@Label("Obter tarefa (CUSTOM)")
	@CustomReturn
	public Tarefa getTaskCustom(int id)
	{
		return newTask("Uma tarefa");
	}

	@ServiceMethod(description = "Retorna todas as tarefas cadastradas")
	@Label("Listar tarefas (CUSTOM - ARRAY)")
	@CustomReturn(fields = {"nome", "prioridade"})
	public Tarefa[] getAllTasksCustom()
	{
		return this.getAllTasks().toArray(new Tarefa[] {});
	}
	
	@ServiceMethod(description = "Retorna todas as tarefas cadastradas")
	@Label("Listar tarefas (CUSTOM - COLECAO COM LABEL)")
	@CustomReturn(fields = {"nome", "completada"}, labels = {"NAME", "DONE"})
	public List<Tarefa> getAllTasksCustomLabel()
	{
		return this.getAllTasks();
	}

	@ServiceMethod(description = "Retorna todas as tarefas cadastradas")
	@Label("Listar tarefas (TABLE)")
	@TableReturn
	public List<Tarefa> getAllTasks()
	{
		return Arrays.asList(newTask("Tarefa 1"), newTask("Tarefa 2"), newTask("Tarefa 3"));
	}
	
	@ServiceMethod(description = "Retorna todas as tarefas cadastradas")
	@Label("Listar tarefas (TABLE - COM LABEL)")
	@TableReturn(headerLabels = {"Task ID", "Task Priority", "Task Name", "Done"})
	public List<Tarefa> getAllTasksLabel()
	{
		return this.getAllTasks();
	}
	
	@ServiceMethod(description = "Retorna todas as tarefas cadastradas")
	@Label("Listar tarefas (TABLE - SEM HEADER)")
	@TableReturn(showHeader = false)
	public List<Tarefa> getAllTasksNoHeader()
	{
		return this.getAllTasks();
	}
	
	@ServiceMethod(description = "Retorna todas as tarefas cadastradas")
	@Label("Listar tarefas (TABLE - CAMPOS CUSTOMIZADOS)")
	@TableReturn(fields = {"completada", "nome", "prioridade"})
	public List<Tarefa> getAllTasksCustomFields()
	{
		return this.getAllTasks();
	}

	@ServiceMethod(description = "Retorna todas as tarefas cadastradas")
	@Label("Listar tarefas (TABLE - CAMPOS+LABEL CUSTOMIZADOS)")
	@TableReturn(fields = {"completada", "nome", "prioridade"}, headerLabels = {"Done", "Name", "Prior"})
	public List<Tarefa> getAllTasksCustomFieldsAndLabels()
	{
		return this.getAllTasks();
	}
	
	private Tarefa newTask(String name)
	{
		Tarefa t = new Tarefa();
		t.setId(RandomUtils.nextInt(1, 100));
		t.setPrioridade(RandomUtils.nextInt(1, 6));
		t.setNome(name);
		t.setCompletada(RandomUtils.nextInt(0, 2) == 0 ? false : true);
		
		return t;
	}
}
