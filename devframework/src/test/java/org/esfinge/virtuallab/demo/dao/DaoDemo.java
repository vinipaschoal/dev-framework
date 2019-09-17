package org.esfinge.virtuallab.demo.dao;

import java.util.List;

import org.esfinge.virtuallab.api.annotations.ServiceDAO;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;

import net.sf.esfinge.querybuilder.Repository;
import net.sf.esfinge.querybuilder.annotation.GreaterOrEquals;
import net.sf.esfinge.querybuilder.annotation.LesserOrEquals;


/*--------------------------------------------------------------------------
 * Demonstracao da anotacao @ServiceDAO.
 *-------------------------------------------------------------------------*/
@ServiceDAO(
	label = "DAO",
	description = "Demonstração da anotação @ServiceDAO.",
	url = "jdbc:postgresql://localhost:5432/postgres", 
	user = "postgres", 
	password = "postgres", 
	dialect = "org.hibernate.dialect.PostgreSQLDialect")
public interface DaoDemo extends Repository<Temperatura>
{
	
	@ServiceMethod(
		label = "Listar todas temperaturas",
		description = "Retorna todos as temperaturas cadastradas no Banco de Dados.")
	@TableReturn
	public List<Temperatura> getTemperatura();
	
	
	@ServiceMethod(
		label = "Listar temperaturas por mês",
		description = "Retorna as temperaturas referentes ao mês informado.")
	@TableReturn
	public List<Temperatura> getTemperaturaByMes(String mes);

	
	@ServiceMethod(
		label = "Listar temperaturas máximas",
		description = "Retorna as temperaturas com máxima igual ou maior ao valor informado.")
	@TableReturn
	public List<Temperatura> getTemperaturaByMaximaOrderByMaximaAsc(@GreaterOrEquals double temp);

	
	@ServiceMethod(
			label = "Listar temperaturas mínimas",
			description = "Retorna as temperaturas com mínima igual ou menor ao valor informado.")
	@TableReturn
	public List<Temperatura> getTemperaturaByMinimaOrderByMinimaDesc(@LesserOrEquals double temp);
}
