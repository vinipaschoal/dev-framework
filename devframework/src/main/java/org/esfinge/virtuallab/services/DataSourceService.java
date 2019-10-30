package org.esfinge.virtuallab.services;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.esfinge.virtuallab.metadata.ServiceDAOMetadata;
import org.esfinge.virtuallab.utils.Utils;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Responsavel em gerenciar os DataSource dos servicos que acessam Bancos de Dados.
 */
public class DataSourceService extends AbstractRoutingDataSource
{
	// chave para o DS default (null object)
	private static final DSKey DEFAULT_DS = new DSKey("DEFAULT", "org.hibernate.dialect.H2Dialect");
	
	// singleton
	private static DataSourceService _instance;

	// mapa Classe -> DataSource
	private Map<String, DSKey> keyMap;
	
	// mapa dos DataSource registrados
	private Map<Object, Object> dsMap;
	
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
	
	/**
	 * Seleciona o DataSource relacionado com a classe DAO informada.
	 */
	public static void setDataSourceFor(Class<?> clazz)
	{
		getInstance().context.set(_instance.keyMap.get(clazz.getCanonicalName()));
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
			String daoClass = dsMetadata.getClazz().getCanonicalName();
			DSKey key = new DSKey(daoClass, dsMetadata.getDialect());
			
			// registra a chave
			getInstance().keyMap.put(daoClass, key);
			
			// registra o DataSource
			_instance.dsMap.put(key, ds);

			// atualiza as propriedades do AbstractRoutingDataSource
			_instance.afterPropertiesSet();
		}
	}
	
	/**
	 * Remove o registro do DataSource.
	 */
	public static void unregisterDataSource(ServiceDAOMetadata dsMetadata)
	{
		// obtem a chave de identificacao do DataSource
		String daoClass = dsMetadata.getClazz().getCanonicalName();
		
		// cancela o registro da chave
		DSKey key = getInstance().keyMap.remove(daoClass);
		
		// cancela o registro do DataSource
		_instance.dsMap.remove(key);

		// atualiza as propriedades do AbstractRoutingDataSource
		_instance.afterPropertiesSet();
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
