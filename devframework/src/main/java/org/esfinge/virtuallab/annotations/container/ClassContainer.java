package org.esfinge.virtuallab.annotations.container;

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
public class ClassContainer {

	@AllMethodsWith(ServiceMethod.class)
	private List<MethodContainer> methodsWithServiceMethod;
	
	@ProcessFields
	private List<FieldContainer> fields;
	
	@AnnotationProperty(annotation = Label.class, property = "name")
	private String labelClass;

	@ReflectionReference
	private Class<?> classe;

	@ElementName
	private String nomeClass;

	@ContainsAnnotation(ServiceClass.class)
	private boolean temAnotacaoServiceClass;

	@ContainsAnnotation(Label.class)
	private boolean temAnotacaoLabel;

	public Class<?> getClasse() {
		return classe;
	}

	public void setClasse(Class<?> classe) {
		this.classe = classe;
	}

	public String getNomeClass() {
		return nomeClass;
	}

	public void setNomeClass(String nomeClass) {
		this.nomeClass = nomeClass;
	}

	public boolean isTemAnotacaoServiceClass() {
		return temAnotacaoServiceClass;
	}

	public void setTemAnotacaoServiceClass(boolean temAnotacaoServiceClass) {
		this.temAnotacaoServiceClass = temAnotacaoServiceClass;
	}

	public List<MethodContainer> getMethodsWithServiceMethod() {
		return methodsWithServiceMethod;
	}

	public void setMethodsWithServiceMethod(List<MethodContainer> methodsWithServiceMethod) {
		this.methodsWithServiceMethod = methodsWithServiceMethod;
	}

	public boolean isTemAnotacaoLabel() {
		return temAnotacaoLabel;
	}

	public void setTemAnotacaoLabel(boolean temAnotacaoLabel) {
		this.temAnotacaoLabel = temAnotacaoLabel;
	}

	public String getLabelClass() {
		return labelClass;
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
	
	

}
