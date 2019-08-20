package org.esfinge.virtuallab.metadata;

import java.util.ArrayList;
import java.util.List;

import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;

import net.sf.esfinge.metadata.annotation.container.AllMethodsWith;
import net.sf.esfinge.metadata.annotation.container.AnnotationProperty;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ContainsAnnotation;
import net.sf.esfinge.metadata.annotation.container.ElementName;
import net.sf.esfinge.metadata.annotation.container.ReflectionReference;
import net.sf.esfinge.metadata.container.ContainerTarget;

/**
 * Extrai informacoes de metadados das classes de servico.
 */
@ContainerFor(ContainerTarget.TYPE)
public class ServiceClassMetadata implements ClassMetadata
{
	// indica se a classe contem a anotacao @ServiceClass
	@ContainsAnnotation(ServiceClass.class)
	private boolean serviceClass;

	// lista dos metodos anotados com @ServiceMethod
	@AllMethodsWith(ServiceMethod.class)
	private List<MethodMetadata> methodsWithServiceMethod;

	// rotulo para a classe
	@AnnotationProperty(annotation = ServiceClass.class, property = "label")
	private String label;

	// texto informativo sobre a classe
	@AnnotationProperty(annotation = ServiceClass.class, property = "description")
	private String description;

	// classe da classe
	@ReflectionReference
	private Class<?> clazz;

	// nome da classe
	@ElementName
	private String className;
	

	/**
	 * Construtor padrao.
	 */
	public ServiceClassMetadata()
	{
		this.methodsWithServiceMethod = new ArrayList<>();
	}

	public boolean isServiceClass()
	{
		return serviceClass;
	}

	public void setServiceClass(boolean serviceClass)
	{
		this.serviceClass = serviceClass;
	}

	public List<MethodMetadata> getMethodsWithServiceMethod()
	{
		return methodsWithServiceMethod;
	}

	public void setMethodsWithServiceMethod(List<MethodMetadata> methodsWithServiceMethod)
	{
		this.methodsWithServiceMethod = methodsWithServiceMethod;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
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