package org.esfinge.virtuallab.services;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.esfinge.virtuallab.metadata.ServiceDAOMetadata;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.utils.Utils;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

import net.sf.esfinge.querybuilder.Repository;

/**
 * Responsavel em gerenciar os DataSource dos servicos que acessam Bancos de Dados.
 */
public class DataSourceService extends AbstractRoutingDataSource implements PersistenceUnitPostProcessor
{
	// chave para o DS default (null object)
	private static final DSKey DEFAULT_DS = new DSKey("DEFAULT", "org.hibernate.dialect.H2Dialect");
	
	// singleton
	private static DataSourceService _instance;

	// mapa Classe -> DataSource
	private Map<Class<?>, DSKey> keyMap;
	
	// mapa dos DataSource registrados
	private Map<Object, Object> dsMap;
	
	// gerencia as entidades JPA mapeadas
	private MutablePersistenceUnitInfo persistenceUnitInfo;
	
	// controla o DataSource selecionado
	private final ThreadLocal<DSKey> context = new ThreadLocal<>();
	
	
	/**
	 * Construtor interno.
	 */
	private DataSourceService()
	{
		this.keyMap = new HashMap<>();
		this.dsMap = new HashMap<>();
		this.setTargetDataSources(this.dsMap);
		
		// registra o DataSource default (in memory)
 		this.setDefaultTargetDataSource(DataSourceBuilder
				.create()
				.url("jdbc:h2:mem:testdb")
				.username("sa")
                .password("")
                .build());
	}	
	
	/**
	 * Singleton.
	 */
	public static DataSourceService getInstance()
	{
		if ( _instance == null )
			_instance = new DataSourceService();
		
		return _instance;
	}
	
	@Override
	protected Object determineCurrentLookupKey()
	{
		return getCurrentDataBase();
	}
	
	@Override
	public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui)
	{
		this.persistenceUnitInfo = pui;
//		this.persistenceUnitInfo.addManagedClassName("org.esfinge.virtuallab.domain.Temperatura");
		/*
		try
		{
			ClassLoaderService.getInstance().loadJar("/tmp/temperatura.jar");
			this.persistenceUnitInfo.addJarFileUrl(Paths.get("/tmp", "temperatura.jar").toUri().toURL());
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		
		System.err.println("PUI managed entities: " + _instance.persistenceUnitInfo.getManagedClassNames());
		*/
	}
	
	/**
	 * Seleciona o DataSource relacionado a classe de BD informada.
	 */
	public static void setDataSourceFor(Class<?> clazz)
	{
		//TODO: debug..
		System.out.println("DS SERVICE >> " + getInstance().keyMap.get(clazz));
		
		getInstance().context.set(_instance.keyMap.get(clazz));
	}
	
	/**
	 * Libera o DataSource.
	 */
	public static void clear()
	{
		getInstance().context.remove();
	}
	
	/**
	 * Retorna as informacoes do DataSource selecionado.
	 */
	public static DSKey getCurrentDataBase()
	{
		DSKey key = getInstance().context.get(); 
		return key == null ? DEFAULT_DS : key;
	}
	
	/**
	 * Registra um novo DataSource.
	 */
	public static void registerDataSource(ServiceDAOMetadata dsMetadata)
	{
		if (! Utils.isNullOrEmpty(dsMetadata) )
		{
			// cria o DataSource
			DataSource ds = DataSourceBuilder
					.create()
					.url(dsMetadata.getUrl())
					.username(dsMetadata.getUser())
	                .password(dsMetadata.getPassword())
	                .build();
			
			// cria a chave de identificacao do DataSource
			Class<?> daoClass = dsMetadata.getClazz();
			DSKey key = new DSKey(daoClass.getCanonicalName(), dsMetadata.getDialect());
			
			// registra a chave
			getInstance().keyMap.put(daoClass, key);
			
			// registra o DataSource
			_instance.dsMap.put(key, ds);

			// atualiza as propriedades do AbstractRoutingDataSource
			_instance.afterPropertiesSet();
			
			// obtem a classe da entidade do @ServiceDAO
			Class<?> entityClass = ReflectionUtils.getActualTypesFromGenericInterface(Repository.class, daoClass).get(0);
			
			// registra a entidade no gerenciador JPA
//			_instance.persistenceUnitInfo.addManagedClassName(entityClass.getCanonicalName());
//			EntityManagerFactoryBeanHelper.getInstance().afterPropertiesSet();
			
//			System.out.println("Entity class: " + entityClass);
//			System.out.println("PUI managed entities: " + _instance.persistenceUnitInfo.getManagedClassNames());
//			System.out.println("PUI name: " + _instance.persistenceUnitInfo.getPersistenceUnitName());
//			System.out.println("EntityManagerFactory metamodel: " + new QueryBuilderEntityManagerProvider().getEntityManagerFactory().getMetamodel().getEntities());
		}
	}
	
	/**
	 * Chave para identificar um DataSource.
	 */
	public static class DSKey
	{
		// chave do DataSource
		private String key;
		
		// dialeto do BD do DataSource
		private String dialect;
		
		
		/**
		 * Construtor interno.
		 */
		private DSKey(String key, String dialect)
		{
			this.key = key;
			this.dialect = dialect;
		}

		public String getKey()
		{
			return key;
		}

		public String getDialect()
		{
			return dialect;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((dialect == null) ? 0 : dialect.hashCode());
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DSKey other = (DSKey) obj;
			if (dialect == null)
			{
				if (other.dialect != null)
					return false;
			}
			else if (!dialect.equals(other.dialect))
				return false;
			if (key == null)
			{
				if (other.key != null)
					return false;
			}
			else if (!key.equals(other.key))
				return false;
			return true;
		}

		@Override
		public String toString()
		{
			return String.format("%s - %s", this.key, this.dialect);
		}
	}
}
