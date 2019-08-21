package org.esfinge.virtuallab.spring;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.esfinge.virtuallab.services.DataSourceService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import net.sf.esfinge.querybuilder.jpa1.EntityManagerProvider;

/**
 * Classe de configuracao do Esfinge QueryBuilder.
 */
@Component
@DependsOn({"entityManagerFactory"})
public class QueryBuilderEntityManagerProvider implements EntityManagerProvider, ApplicationContextAware
{
	// Contexto do Spring
	private static ApplicationContext _context;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		// armazena o contexto do Spring para ter acesso aos beans
		_context = applicationContext;
	}

	@Override
	public EntityManager getEntityManager()
	{
		// configura o dialeto do BD do DataSource selecionado
		Map<String,String> props = new HashMap<>();
		props.put( "org.hibernate.dialect", DataSourceService.getCurrentDataBase().getDialect());
		
		// cria um EntityManager para o DataSource selecionado
		return getEntityManagerFactory().createEntityManager(props);
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory()
	{
		// retorna do contexto do Spring
		return _context.getBean(EntityManagerFactory.class);
	}
}
