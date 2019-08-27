package org.esfinge.virtuallab.domain;

import java.util.List;

import org.esfinge.virtuallab.api.annotations.ServiceDAO;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;

import net.sf.esfinge.querybuilder.Repository;

@ServiceDAO(label = "DAO Topics",
description = "Banco de Dados: MySQL  -  Entidade: Topic",
url = "jdbc:mysql://localhost:3306/querybuilder", user = "root", password = "", dialect = "org.hibernate.dialect.MySQL8Dialect")
public interface TopicService extends Repository<Topic>
{
	@ServiceMethod(label = "Obter todos os topicos", description = "Retorna todos os topicos cadastrados no Banco de Dados")
	public List<Topic> getTopic();
	
	@ServiceMethod(label = "Obter topicos por nome", description = "Retorna os topicos cadastrados no Banco de Dados com o nome informado")
	public List<Topic> getTopicByName(String name);
}
