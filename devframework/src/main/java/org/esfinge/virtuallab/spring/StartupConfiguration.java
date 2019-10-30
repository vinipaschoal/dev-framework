package org.esfinge.virtuallab.spring;

import java.io.File;

import org.esfinge.virtuallab.metadata.MetadataHelper;
import org.esfinge.virtuallab.services.ClassLoaderService;
import org.esfinge.virtuallab.services.PersistenceService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

//@Component
public class StartupConfiguration
{
	/*
	@EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) 
	{
		try
		{
			// carrega as classes/jar de servico do diretorio de upload
			// para ficarem disponiveis para a aplicacao
			for (File file : PersistenceServiceNew.getInstance().getUploadedFiles())
			{
				try
				{
					// carrega o servico
					Class<?> serviceClass = ClassLoaderServiceNew.getInstance().loadService(file);

					// se for um servico DAO, informa ao EntityManagerFactoryHelper para mapear as entidades
					if ( MetadataHelper.getInstance().getClassMetadata(serviceClass).isServiceDAO() )
						EntityManagerFactoryHelper.getInstance().mapEntitiesFromJar(file.toURI().toURL());
				} 
				catch (Exception exc)
				{
					// TODO: debug..
					exc.printStackTrace();
				}
			}
		} 
		catch (Exception e1)
		{
			// TODO: debug..
			e1.printStackTrace();
		}
		finally
		{
			// carrega o EntityManagerFactory com as entidades mapeadas
			EntityManagerFactoryHelper.getInstance().loadEntities();
		}
	}
	*/
}
