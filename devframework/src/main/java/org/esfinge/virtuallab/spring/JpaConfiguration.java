package org.esfinge.virtuallab.spring;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.esfinge.virtuallab.services.DataSourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Classe de configuracao do Spring Framework.
 */
@Configuration
@EnableTransactionManagement
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
		LocalContainerEntityManagerFactoryBean emfh = EntityManagerFactoryHelper.getInstance();
		emfh.setDataSource(this.getDataSource());
		// precisa especificar um pacote pois senao ele
		// procura automaticamente pelo arquivo 'persistence.xml'
		emfh.setPackagesToScan(new String[] { "org.esfinge.virtuallab" }); 

		// dialeto do BD default
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect"); 
		emfh.setJpaVendorAdapter(vendorAdapter);
		
		return emfh;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf)
	{
		return new JpaTransactionManager(emf);
	}
}
