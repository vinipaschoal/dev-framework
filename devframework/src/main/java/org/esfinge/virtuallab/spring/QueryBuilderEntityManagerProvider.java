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
	private static ThreadLocal<EntityManager> _openEM = new ThreadLocal<>();
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		// armazena o contexto do Spring para ter acesso aos beans
		_context = applicationContext;
	}

	@Override
	public EntityManager getEntityManager()
	{
		// configura as propriedades do DataSource selecionado
		Map<String,String> props = new HashMap<>();
		props.put( "hibernate.dialect", DataSourceService.getCurrentDataBase().getDialect());
		
		
		// recupera o ultimo EntityManager criado
		EntityManager em = _openEM.get();
		if ( em != null )
		{
			// fecha o EM para liberar a conexao
			em.close();
			_openEM.remove();
		}
		
		// cria um novo EntityManager para o DataSource selecionado
		em = getEntityManagerFactory().createEntityManager(props);
		_openEM.set(em);
		
		return em; 
		
		/*
		// DEBUG: lista as entidades carregadas no EntityManager
		EntityManager em = getEntityManagerFactory().createEntityManager(props);
		System.out.println("===> ENTITIES: \n");
		em.getMetamodel().getEntities().forEach(System.out::println);
		return em;
		*/
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory()
	{
		// retorna do contexto do Spring
		return _context.getBean(EntityManagerFactory.class);
	}
}
