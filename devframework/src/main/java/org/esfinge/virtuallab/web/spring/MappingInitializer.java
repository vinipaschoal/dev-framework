package org.esfinge.virtuallab.web.spring;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.esfinge.virtuallab.services.ClassLoaderService;
import org.esfinge.virtuallab.services.PersistenceService;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Classe de configuracao do Spring Framework.
 */
public class MappingInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
	@Override
	protected Class<?>[] getRootConfigClasses()
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
		
		return new Class[] { MappingConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses()
	{
		return null;
	}

	@Override
	protected String[] getServletMappings()
	{
		return new String[] { "/" };
	}
}
