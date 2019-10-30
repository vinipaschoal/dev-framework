package org.esfinge.virtuallab.spring;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.esfinge.virtuallab.services.ClassLoaderService;
import org.esfinge.virtuallab.utils.Utils;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

/**
 * Responsavel em gerenciar a criacao do EntityManagerFactory e do mapeamento de entidades JPA.
 */
public class EntityManagerFactoryHelper extends LocalContainerEntityManagerFactoryBean implements PersistenceUnitPostProcessor
{
	// singleton
	private static EntityManagerFactoryHelper _instance;
	
	// lista de jars contendo entidades JPA para serem carregadas pelo EntityManagerFactory
	private Set<URL> entityJars;
	

	/**
	 * Construtor interno.
	 */
	private EntityManagerFactoryHelper()
	{
		this.setPersistenceUnitPostProcessors(this);
		this.entityJars = new HashSet<URL>();
	}
	
	/**
	 * Singleton.
	 */
	public static EntityManagerFactoryHelper getInstance()
	{
		if ( _instance == null )
			_instance = new EntityManagerFactoryHelper();
		
		return _instance;
	}
	
	/**
	 * Mapeia as entidades JPA contidas no JAR.
	 */
	public void mapEntitiesFromJar(URL jarURL)
	{
		if ( Utils.isNullOrEmpty(jarURL) )
			return;
		
		entityJars.add(jarURL);
	}
	
	/**
	 * Remove as entidades JPA contidas no JAR.
	 */
	public void unmapEntitiesFromJar(URL jarURL)
	{
		if ( Utils.isNullOrEmpty(jarURL) )
			return;
		
		entityJars.remove(jarURL);
	}
	
	/**
	 * Recarrega as entidades JPA no EntityManagerFactory.
	 */
	public synchronized void loadEntities()
	{
		// adiciona os ClassLoaders dos servicos DAO que contem entidades JPA	
		Map<String,Object> propsMap = new HashMap<>();
		propsMap.put(AvailableSettings.CLASSLOADERS, ClassLoaderService.getInstance().getJPAClassLoaders());
		this.setJpaPropertyMap(propsMap);
		
		// recarrega o EntityManagerFactory
		super.afterPropertiesSet();
	}
	
	@Override
	public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui)
	{
		// adiciona as URLs dos jars que contem entidades para mapea-las
		this.entityJars.forEach(pui::addJarFileUrl);
	}
}
