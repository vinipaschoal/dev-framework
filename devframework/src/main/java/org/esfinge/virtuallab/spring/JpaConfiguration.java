package org.esfinge.virtuallab.spring;

import javax.persistence.EntityManagerFactory;
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
		LocalContainerEntityManagerFactoryBean emfh = EntityManagerFactoryHelper.getInstance();
		emfh.setDataSource(this.getDataSource());
		emfh.setPackagesToScan(new String[] { "org.esfinge.virtuallab" }); // precisa especificar um pacote para nao procurar o arquivo 'persistence.xml'

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect"); // dialeto do BD default
		emfh.setJpaVendorAdapter(vendorAdapter);
		
		return emfh;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf)
	{
		return new JpaTransactionManager(emf);
	}
}
