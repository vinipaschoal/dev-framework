package org.esfinge.virtuallab.webservice;

import org.esfinge.virtuallab.utils.ClassLoaderUtils;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MappingInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		try {
			ClassLoaderUtils.getInstance().loadAll();
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
