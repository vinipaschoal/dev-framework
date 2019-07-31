package org.esfinge.virtuallab.metadata;

import java.util.List;

import org.esfinge.virtuallab.annotations.Label;
import org.esfinge.virtuallab.annotations.ServiceClass;
import org.esfinge.virtuallab.annotations.ServiceMethod;

import net.sf.esfinge.metadata.annotation.container.AllMethodsWith;
import net.sf.esfinge.metadata.annotation.container.AnnotationProperty;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ContainsAnnotation;
import net.sf.esfinge.metadata.annotation.container.ElementName;
import net.sf.esfinge.metadata.annotation.container.ReflectionReference;
import net.sf.esfinge.metadata.container.ContainerTarget;

/**
 * Extrai informacoes de metadados das classes.
 */
@ContainerFor(ContainerTarget.TYPE)
public class ClassMetadata
{
	// indica se a classe contem a anotacao @ServiceClass
	@ContainsAnnotation(ServiceClass.class)
	private boolean annotatedWithServiceClass;

	// indica se a classe contem a anotacao @Label
	@ContainsAnnotation(Label.class)
	private boolean annotatedWithLabel;

	// lista dos metodos anotados com @ServiceMethod
	@AllMethodsWith(ServiceMethod.class)
	private List<MethodMetadata> methodsWithServiceMethod;

	// texto informativo sobre a classe
	@AnnotationProperty(annotation=ServiceClass.class, property = "description")
	private String description;

	// informacoes da anotacao @Label (se utilizada)
	@AnnotationProperty(annotation = Label.class, property = "value")
	private String label;

	// classe da classe
	@ReflectionReference
	private Class<?> clazz;

	// nome da classe
	@ElementName
	private String className;
	
	
	public boolean isAnnotatedWithServiceClass()
	{
		return annotatedWithServiceClass;
	}

	public void setAnnotatedWithServiceClass(boolean annotatedWithServiceClass)
	{
		this.annotatedWithServiceClass = annotatedWithServiceClass;
	}

	public boolean isAnnotatedWithLabel()
	{
		return annotatedWithLabel;
	}

	public void setAnnotatedWithLabel(boolean annotatedWithLabel)
	{
		this.annotatedWithLabel = annotatedWithLabel;
	}

	public List<MethodMetadata> getMethodsWithServiceMethod()
	{
		return methodsWithServiceMethod;
	}

	public void setMethodsWithServiceMethod(List<MethodMetadata> methodsWithServiceMethod)
	{
		this.methodsWithServiceMethod = methodsWithServiceMethod;
	}

	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public Class<?> getClazz()
	{
		return clazz;
	}

	public void setClazz(Class<?> clazz)
	{
		this.clazz = clazz;
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}
}