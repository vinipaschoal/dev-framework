package org.esfinge.virtuallab.metadata;

import java.lang.reflect.Parameter;

import org.esfinge.virtuallab.annotations.Label;
import org.esfinge.virtuallab.metadata.processor.ElementNameParameter;
import org.esfinge.virtuallab.metadata.processor.ReflectionReferenceParameter;

import net.sf.esfinge.metadata.annotation.container.AnnotationProperty;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ContainsAnnotation;
import net.sf.esfinge.metadata.container.ContainerTarget;

/**
 * Extrai informacoes de metadados dos parametros dos metodos.
 */
@ContainerFor(ContainerTarget.PARAMETER)
public class ParameterMetadata
{
	// indica se o metodo contem a anotacao @Label
	@ContainsAnnotation(Label.class)
	private boolean annotatedWithLabel;

	// informacoes da anotacao @Label (se utilizada)
	@AnnotationProperty(annotation = Label.class, property = "value")
	private String label;

	// parametro
	@ReflectionReferenceParameter
	private Parameter parameter;
	
	// nome do parametro
	@ElementNameParameter
	private String parameterName;
	
	// posicao do parametro no metodo
	private int index = -1;

	
	public boolean isAnnotatedWithLabel()
	{
		return annotatedWithLabel;
	}

	public void setAnnotatedWithLabel(boolean annotatedWithLabel)
	{
		this.annotatedWithLabel = annotatedWithLabel;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public Parameter getParameter()
	{
		return parameter;
	}

	public void setParameter(Parameter parameter)
	{
		this.parameter = parameter;
		
		// verifica a posicaso do parametro no metodo
		if ( this.parameter != null )
		{
			Parameter[] params = this.parameter.getDeclaringExecutable().getParameters();
			for (int i = 0; i < params.length; i++ )
				if ( this.parameter.equals(params[i]) )
				{
					this.index = i;
					break;
				}
		}
		else
			this.index = -1;
	}

	public String getParameterName()
	{
		return parameterName;
	}

	public void setParameterName(String parameterName)
	{
		this.parameterName = parameterName;
	}
	
	public int getIndex()
	{
		return index;
	}
}
