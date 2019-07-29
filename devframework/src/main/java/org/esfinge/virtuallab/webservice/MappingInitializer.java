package org.esfinge.virtuallab.webservice;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.esfinge.virtuallab.services.PersistenceService;
import org.esfinge.virtuallab.utils.ClassLoaderUtils;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MappingInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		try {
			// carrega as classes/jar do diretorio de upload
			ClassLoaderUtils classLoader = ClassLoaderUtils.getInstance();
			
			for (File file : PersistenceService.getInstance().getUploadedFiles())
			{
				try {
					// caminho para o arquivo
					String filePath = file.getAbsolutePath();
					
					// classe
					if ( FilenameUtils.isExtension(filePath, "class") )
						classLoader.loadClass(filePath);
					
					// jar
					else
						classLoader.loadJar(filePath);
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return new Class[] { MappingConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
