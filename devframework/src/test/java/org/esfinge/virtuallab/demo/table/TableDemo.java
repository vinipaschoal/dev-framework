package org.esfinge.virtuallab.demo.table;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;
import org.esfinge.virtuallab.utils.Utils;


/*--------------------------------------------------------------------------
 * Demonstracao da anotacao @TableReturn.
 *-------------------------------------------------------------------------*/
@ServiceClass(
	label = "TABELAS",
	description = "Demonstração da anotação @TableReturn.")
public class TableDemo
{
	private static final List<Task> tasks = new ArrayList<>();
	static {
		for ( int i = 1; i <= 10; i++ )
			tasks.add(new Task(i, RandomUtils.nextInt(1, 6), String.format("Tarefa %02d", i), RandomUtils.nextBoolean()));
	}
	
	/*--------------------------------------------------------------------------
	 * Utiliza a anotacao sem parametros.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Listar todas tarefas",
		description = "@TableReturn sem parâmetros.")
	@TableReturn
	public List<Task> getAllTasks()
	{
		return tasks;
	}

	
	/*--------------------------------------------------------------------------
	 * Especifica quais as colunas (campos e ordem) serao retornadas.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label="Listar tarefas concluídas",
		description = "@TableReturn especificando as colunas.")
	@TableReturn(
		fields = {"name", "priority"})
	public List<Task> getClosedTasks()
	{
		return Utils.filterFromCollection(tasks, t -> t.isCompleted());
	}
	
	
	/*--------------------------------------------------------------------------
	 * Especifica os cabecalhos das colunas que serao retornadas.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label="Listar tarefas não concluídas",
		description = "@TableReturn especificando os cabeçalhos.")
	@TableReturn(
		fields = {"name", "priority", "completed"},
		headerLabels = {"Nome", "Prioridade", "Concluída"})
	public List<Task> getOpenTasks()
	{
		return Utils.filterFromCollection(tasks, t -> !t.isCompleted());
	}
	

	/*--------------------------------------------------------------------------
	 * Retorna a tabela sem cabecalho.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label="Listar tarefas por prioridade",
		description = "@TableReturn sem cabeçalho.")
	@TableReturn(
		showHeader = false)
	public List<Task> getTasksByPriority(int priority)
	{
		return Utils.filterFromCollection(tasks, t -> t.getPriority() == priority);
	}
}
