package org.esfinge.virtuallab.spring;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.esfinge.virtuallab.services.ClassLoaderService;
import org.esfinge.virtuallab.services.PersistenceService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@DependsOn({"entityManagerFactory", "queryBuilderEntityManagerProvider"})
public class StartupConfiguration
{
	@EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) 
	{
		try
		{
			// carrega as classes/jar do diretorio de upload
			// para ficarem disponiveis para a aplicacao
			ClassLoaderService classLoader = ClassLoaderService.getInstance();

			for (File file : PersistenceService.getInstance().getUploadedFiles())
			{
				try
				{
					// caminho para o arquivo
					String filePath = file.getAbsolutePath();

					// classe
					if (FilenameUtils.isExtension(filePath, "class"))
						classLoader.loadClass(filePath);

					// jar
					else
						classLoader.loadJar(filePath);
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
	}
}
