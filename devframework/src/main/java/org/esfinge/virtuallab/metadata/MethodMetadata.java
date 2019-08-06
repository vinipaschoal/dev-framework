package org.esfinge.virtuallab.metadata;

import java.lang.reflect.Method;
import java.util.List;

import org.esfinge.virtuallab.annotations.CustomReturn;
import org.esfinge.virtuallab.annotations.Label;
import org.esfinge.virtuallab.annotations.ServiceMethod;
import org.esfinge.virtuallab.annotations.TableReturn;

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

	// indica se o metodo contem a anotacao @JsonReturn
	@ContainsAnnotation(CustomReturn.class)
	private boolean annotatedWithJsonReturn;

	// indica se o metodo contem a anotacao @HtmlTableReturn
	@ContainsAnnotation(TableReturn.class)
	private boolean annotatedWithHtmlTableReturn;
	
	// texto informativo sobre o metodo
	@AnnotationProperty(annotation=ServiceMethod.class, property = "description")
	private String description;

	// indica se o metodo contem a anotacao @Label
	@ContainsAnnotation(Label.class)
	private boolean annotatedWithLabel;

	// informacoes da anotacao @Label (se utilizada)
	@AnnotationProperty(annotation = Label.class, property = "value")
	private String label;
	
	// metodo
	@ReflectionReference
	private Method method;

	// nome do metodo
	@ElementName
	private String methodName;

	// parametros do metodo
	@ProcessParameters
	private List<ParameterMetadata> parameters;

	
	public boolean isAnnotatedWithServiceMethod()
	{
		return annotatedWithServiceMethod;
	}

	public void setAnnotatedWithServiceMethod(boolean annotatedWithServiceMethod)
	{
		this.annotatedWithServiceMethod = annotatedWithServiceMethod;
	}

	public boolean isAnnotatedWithJsonReturn()
	{
		return annotatedWithJsonReturn;
	}

	public void setAnnotatedWithJsonReturn(boolean annotatedWithJsonReturn)
	{
		this.annotatedWithJsonReturn = annotatedWithJsonReturn;
	}

	public boolean isAnnotatedWithHtmlTableReturn()
	{
		return annotatedWithHtmlTableReturn;
	}

	public void setAnnotatedWithHtmlTableReturn(boolean annotatedWithHtmlTableReturn)
	{
		this.annotatedWithHtmlTableReturn = annotatedWithHtmlTableReturn;
	}

	public boolean isAnnotatedWithLabel()
	{
		return annotatedWithLabel;
	}

	public void setAnnotatedWithLabel(boolean annotatedWithLabel)
	{
		this.annotatedWithLabel = annotatedWithLabel;
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
