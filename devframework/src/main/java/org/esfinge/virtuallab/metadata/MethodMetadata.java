package org.esfinge.virtuallab.metadata;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.esfinge.virtuallab.api.annotations.ServiceMethod;

import net.sf.esfinge.metadata.annotation.container.AnnotationProperty;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ContainsAnnotation;
import net.sf.esfinge.metadata.annotation.container.ElementName;
import net.sf.esfinge.metadata.annotation.container.ProcessParameters;
import net.sf.esfinge.metadata.annotation.container.ReflectionReference;
import net.sf.esfinge.metadata.container.ContainerTarget;

/**
 * Extrai informacoes de metadados dos metodos.
 */
@ContainerFor(ContainerTarget.METHODS)
public class MethodMetadata
{
	// indica se o metodo contem a anotacao @ServiceMethod
	@ContainsAnnotation(ServiceMethod.class)
	private boolean annotatedWithServiceMethod;

	// rotulo para o metodo
	@AnnotationProperty(annotation = ServiceMethod.class, property = "label")
	private String label;
	
	// texto informativo sobre o metodo
	@AnnotationProperty(annotation=ServiceMethod.class, property = "description")
	private String description;

	// metodo
	@ReflectionReference
	private Method method;

	// nome do metodo
	@ElementName
	private String methodName;

	// parametros do metodo
	@ProcessParameters
	private List<ParameterMetadata> parameters;
	
	
	/**
	 * Construtor padrao.
	 */
	public MethodMetadata()
	{
		this.parameters = new ArrayList<>();
	}

	public boolean isAnnotatedWithServiceMethod()
	{
		return annotatedWithServiceMethod;
	}

	public void setAnnotatedWithServiceMethod(boolean annotatedWithServiceMethod)
	{
		this.annotatedWithServiceMethod = annotatedWithServiceMethod;
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

	public Method getMethod()
	{
		return method;
	}

	public void setMethod(Method method)
	{
		this.method = method;
	}

	public String getMethodName()
	{
		return methodName;
	}

	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}

	public List<ParameterMetadata> getParameters()
	{
		return parameters;
	}

	public void setParameters(List<ParameterMetadata> parameters)
	{
		this.parameters = parameters;
	}
}
