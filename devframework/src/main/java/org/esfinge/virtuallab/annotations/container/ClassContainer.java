package org.esfinge.virtuallab.annotations.container;

import java.lang.reflect.Field;
import java.util.List;

import org.esfinge.virtuallab.annotations.Label;
import org.esfinge.virtuallab.annotations.ServiceClass;
import org.esfinge.virtuallab.annotations.ServiceMethod;

import net.sf.esfinge.metadata.annotation.container.AllMethodsWith;
import net.sf.esfinge.metadata.annotation.container.AnnotationProperty;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ContainsAnnotation;
import net.sf.esfinge.metadata.annotation.container.ElementName;
import net.sf.esfinge.metadata.annotation.container.ProcessFields;
import net.sf.esfinge.metadata.annotation.container.ReflectionReference;
import net.sf.esfinge.metadata.container.ContainerTarget;

@ContainerFor(ContainerTarget.TYPE)
public class ClassContainer implements IContainer {

	@AllMethodsWith(ServiceMethod.class)
	private List<MethodContainer> methodsWithServiceMethod;

	@ProcessFields
	private List<FieldContainer> fields;

	@AnnotationProperty(annotation = Label.class, property = "name")
	private String labelClass;

	@ReflectionReference
	private Class<?> clazz;

	@ElementName
	private String className;

	@ContainsAnnotation(ServiceClass.class)
	private boolean annotatedWithServiceClass;

	@ContainsAnnotation(Label.class)
	private boolean annotatedWithLabel;

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean isAnnotatedWithServiceClass() {
		return annotatedWithServiceClass;
	}

	public void setAnnotatedWithServiceClass(boolean annotatedWithServiceClass) {
		this.annotatedWithServiceClass = annotatedWithServiceClass;
	}

	public List<MethodContainer> getMethodsWithServiceMethod() {
		return methodsWithServiceMethod;
	}

	public void setMethodsWithServiceMethod(List<MethodContainer> methodsWithServiceMethod) {
		this.methodsWithServiceMethod = methodsWithServiceMethod;
	}

	public void setAnnotatedWithLabel(boolean annotatedWithLabel) {
		this.annotatedWithLabel = annotatedWithLabel;
	}

	public void setLabelClass(String labelClass) {
		this.labelClass = labelClass;
	}

	public List<FieldContainer> getFields() {
		return fields;
	}

	public void setFields(List<FieldContainer> fields) {
		this.fields = fields;
	}

	public String getLabeledClassName() {
		if (annotatedWithLabel) {
			return labelClass;
		} else {
			return className;
		}
	}
	
	public FieldContainer getDeclaredField(String name) {
		for(FieldContainer fieldContainer :fields){
			if(fieldContainer.getDeclaredName().equals(name)) {
				return fieldContainer;
			}
		}
		return null;
	}
}
