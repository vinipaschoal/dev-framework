package org.esfinge.virtuallab.domain;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.esfinge.virtuallab.annotations.CustomReturn;
import org.esfinge.virtuallab.annotations.Param;
import org.esfinge.virtuallab.annotations.ServiceClass;
import org.esfinge.virtuallab.annotations.ServiceMethod;
import org.esfinge.virtuallab.annotations.TableReturn;

@ServiceClass(label = "Tarefas / ToDo", description = "Disponibiliza serviços relacionados a tarefas, no estilo ToDo.")
public class TarefaService
{
	@ServiceMethod(label = "Cadastrar tarefa (STRING)", description = "Cria uma nova tarefa")
	public String setTask(@Param(label="Tarefa") String name, @Param(label="Prioridade") int priority)
	{
		return String.format("Tarefa %s cadastrada com prioridade %d", name, priority);
	}

	@ServiceMethod(label = "Listar prioridades (ARRAY)", description = "Retorna os valores de prioridade permitidos")
	public int[] getPriorityRange()
	{
		return new int[] { 1, 2, 3, 4, 5 };
	}

	@ServiceMethod(label = "Obter tarefa (OBJETO)", description = "Obtem uma tarefa especifica")
	public Tarefa getTask(@Param(label="Identificador") int id)
	{
		return newTask("Uma tarefa");
	}
	
	@ServiceMethod(label = "Obter tarefa (CUSTOM)", description = "Obtem uma tarefa especifica")
	@CustomReturn
	public Tarefa getTaskCustom(int id)
	{
		return newTask("Uma tarefa");
	}

	@ServiceMethod(label = "Listar tarefas (CUSTOM - ARRAY)", description = "Retorna todas as tarefas cadastradas")
	@CustomReturn(fields = {"nome", "prioridade"})
	public Tarefa[] getAllTasksCustom()
	{
		return this.getAllTasks().toArray(new Tarefa[] {});
	}
	
	@ServiceMethod(label = "Listar tarefas (CUSTOM - COLECAO COM LABEL)", description = "Retorna todas as tarefas cadastradas")
	@CustomReturn(fields = {"nome", "completada"}, labels = {"NAME", "DONE"})
	public List<Tarefa> getAllTasksCustomLabel()
	{
		return this.getAllTasks();
	}

	@ServiceMethod(label = "Listar tarefas (TABLE)", description = "Retorna todas as tarefas cadastradas")
	@TableReturn
	public List<Tarefa> getAllTasks()
	{
		return Arrays.asList(newTask("Tarefa 1"), newTask("Tarefa 2"), newTask("Tarefa 3"));
	}
	
	@ServiceMethod(label = "Listar tarefas (TABLE - COM LABEL)", description = "Retorna todas as tarefas cadastradas")
	@TableReturn(headerLabels = {"Task ID", "Task Priority", "Task Name", "Done"})
	public List<Tarefa> getAllTasksLabel()
	{
		return this.getAllTasks();
	}
	
	@ServiceMethod(label = "Listar tarefas (TABLE - SEM HEADER)", description = "Retorna todas as tarefas cadastradas")
	@TableReturn(showHeader = false)
	public List<Tarefa> getAllTasksNoHeader()
	{
		return this.getAllTasks();
	}
	
	@ServiceMethod(label = "Listar tarefas (TABLE - CAMPOS CUSTOMIZADOS)", description = "Retorna todas as tarefas cadastradas")
	@TableReturn(fields = {"completada", "nome", "prioridade"})
	public List<Tarefa> getAllTasksCustomFields()
	{
		return this.getAllTasks();
	}

	@ServiceMethod(label = "Listar tarefas (TABLE - CAMPOS+LABEL CUSTOMIZADOS)", description = "Retorna todas as tarefas cadastradas")
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
