package org.esfinge.virtuallab.spring;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import org.esfinge.virtuallab.services.ClassLoaderService;
import org.esfinge.virtuallab.utils.Utils;
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
	
	// flag para sinalizar se o EMF precisa ser recarregado
	private boolean needsReload;
	

	/**
	 * Construtor interno.
	 */
	private EntityManagerFactoryHelper()
	{
		this.setPersistenceUnitPostProcessors(this);
		this.entityJars = new HashSet<URL>();
		this.needsReload = false;
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
	 * Carrega as entidades JPA contidas no JAR.
	 */
	public void loadEntitiesFromJar(String jarFilePath)
	{
		if ( Utils.isNullOrEmpty(jarFilePath) )
			return;
		
		try
		{
			// obtem a URL para o JAR
			URL jarURL = Paths.get(jarFilePath).toUri().toURL();

			// verifica se o JAR ja foi mapeado
			if (! entityJars.contains(jarURL) )
			{
				// obtem as classes anotadas com @Entity do JAR
				List<Class<?>> entityClasses = Utils.filterFromCollection(ClassLoaderService.getInstance().loadJar(jarFilePath),
						c -> c.isAnnotationPresent(Entity.class));
				
				// verifica se as classes ja estao carregadas
				if (! Utils.isNullOrEmpty(entityClasses) )
				{
					// obtem as entidades ja mapeadas pelo EMF
					Set<String> loadedEntities = this.getObject().getMetamodel().getEntities()
							.stream().map(e -> e.getJavaType().getCanonicalName()).collect(Collectors.toSet());
					
					for ( Class<?> clazz : entityClasses )
						if (! loadedEntities.contains(clazz.getCanonicalName()) )
						{
							// o JAR contem uma entidade nao mapeada!
							entityJars.add(jarURL);
							needsReload = true;
							break;
						}
				}
			}
		}
		catch (MalformedURLException e)
		{
			// TODO: debug..
			e.printStackTrace();
		}
	}
	
	/**
	 * Recarrega o EntityManagerFactory.
	 */
	public synchronized void reload()
	{
		if ( this.needsReload )
			super.afterPropertiesSet();
	}
	
	@Override
	public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui)
	{
		// adiciona os jars que contem entidades para
		this.entityJars.forEach(pui::addJarFileUrl);
	}
}
