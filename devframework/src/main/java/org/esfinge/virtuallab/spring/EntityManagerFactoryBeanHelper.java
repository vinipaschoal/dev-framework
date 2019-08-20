package org.esfinge.virtuallab.spring;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

public class EntityManagerFactoryBeanHelper extends LocalContainerEntityManagerFactoryBean
{
	private static EntityManagerFactoryBeanHelper _instance;
	
	
	public static EntityManagerFactoryBeanHelper getInstance()
	{
		if ( _instance == null )
			_instance = new EntityManagerFactoryBeanHelper();
		
		return _instance;
	}
	
	protected void postProcessEntityManagerFactory(EntityManagerFactory emf, PersistenceUnitInfo pui) 
	{
		System.out.println("HELPER >> PUI managed classes: " + pui.getManagedClassNames());
		System.out.println("HELPER >> EMF metamodel entities: " + emf.getMetamodel().getEntities());
	}
}
