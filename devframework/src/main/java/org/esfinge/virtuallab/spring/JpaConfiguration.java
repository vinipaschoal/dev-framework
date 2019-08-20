package org.esfinge.virtuallab.spring;

import javax.sql.DataSource;

import org.esfinge.virtuallab.services.DataSourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * Classe de configuracao do Spring Framework.
 */
@Configuration
public class JpaConfiguration
{
	@Bean
	public DataSource getDataSource()
	{
		return DataSourceService.getInstance();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
	{
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();// EntityManagerFactoryBeanHelper.getInstance();
		em.setDataSource(this.getDataSource());
		em.setPackagesToScan(new String[] { "org.esfinge.virtuallab" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
		em.setJpaVendorAdapter(vendorAdapter);
		em.setPersistenceUnitPostProcessors(DataSourceService.getInstance());
		
		return em;
	}

	@Bean
	public JpaTransactionManager transactionManager()
	{
		JpaTransactionManager txnMgr = new JpaTransactionManager();
		txnMgr.setEntityManagerFactory(entityManagerFactory().getObject());
		return txnMgr;
	}
}
