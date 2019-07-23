package org.esfinge.virtuallab.services;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.esfinge.virtuallab.annotations.container.ClassContainer;
import org.esfinge.virtuallab.annotations.container.FieldContainer;
import org.esfinge.virtuallab.annotations.container.MethodContainer;

import net.sf.esfinge.metadata.AnnotationReader;

public class InformationClassService {

	private static InformationClassService _instance;

	private AnnotationReader reader;

	private InformationClassService() {
		this.reader = new AnnotationReader();
	}

	public synchronized static InformationClassService getInstance() {
		if (_instance == null)
			_instance = new InformationClassService();

		return _instance;
	}

	public List<MethodContainer> getServiceMethods(Class clazz) throws Exception {
		ClassContainer container = reader.readingAnnotationsTo(clazz, ClassContainer.class);
		if (container.isTemAnotacaoServiceClass()) {
			return container.getMethodsWithServiceMethod();
		}
		return new ArrayList<MethodContainer>();
	}

	public List<FieldContainer> getFields(Class clazz) throws Exception {
		ClassContainer container = reader.readingAnnotationsTo(clazz, ClassContainer.class);
		return container.getFields();
	}

	public boolean isHtmlTableReturnPresent(Method method) throws Exception {
		MethodContainer container = reader.readingAnnotationsTo(method, MethodContainer.class);
		return container.isTemAnotacaoHtmlTableReturn();
	}

	public String getClassName(Class clazz) throws Exception {
		ClassContainer container = reader.readingAnnotationsTo(clazz, ClassContainer.class);
		if (container.isTemAnotacaoLabel()) {
			return container.getLabelClass();
		} else {
			return container.getNomeClass();
		}

	}

	public boolean isJsonReturnPresent(Method method) throws Exception {
		MethodContainer container = reader.readingAnnotationsTo(method, MethodContainer.class);
		return container.isTemAnotacaoJsonReturn();
	}
}
