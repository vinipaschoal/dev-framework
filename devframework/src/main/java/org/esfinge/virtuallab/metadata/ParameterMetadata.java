package org.esfinge.virtuallab.metadata;

import java.lang.reflect.Parameter;

import org.esfinge.virtuallab.api.annotations.Param;
import org.esfinge.virtuallab.api.annotations.ParamAttribute;

import net.sf.esfinge.metadata.annotation.container.AnnotationProperty;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ContainsAnnotation;
import net.sf.esfinge.metadata.annotation.container.ElementName;
import net.sf.esfinge.metadata.annotation.container.ReflectionReference;
import net.sf.esfinge.metadata.container.ContainerTarget;

/**
 * Extrai informacoes de metadados dos parametros dos metodos.
 */
@ContainerFor(ContainerTarget.PARAMETER)
public class ParameterMetadata implements Comparable<ParameterMetadata>
{
	// indica se o parametro contem a anotacao @Param
	@ContainsAnnotation(Param.class)
	private boolean annotatedWithParam;
	
	// rotulo para o parametro
	@AnnotationProperty(annotation = Param.class, property = "label")
	private String label;

	// metadados para os campos da classe do parametro (se houver)
	@AnnotationProperty(annotation = Param.class, property = "fields")
	private ParamAttribute[] fieldsMetadata;

	// se o parametro eh obrigatorio ou nao
	@AnnotationProperty(annotation = Param.class, property = "required")
	private boolean required;

	// parametro
	@ReflectionReference
	private Parameter parameter;
	
	// nome do parametro
	@ElementName
	private String parameterName;
	
	// posicao do parametro no metodo
	private int index = -1;

	
	/**
	 * Construtor padrao.
	 */
	public ParameterMetadata()
	{
	}

	public boolean isAnnotatedWithParam()
	{
		return annotatedWithParam;
	}

	public void setAnnotatedWithParam(boolean annotatedWithParam)
	{
		this.annotatedWithParam = annotatedWithParam;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public ParamAttribute[] getFieldsMetadata()
	{
		return fieldsMetadata;
	}

	public void setFieldsMetadata(ParamAttribute[] fieldsMetadata)
	{
		this.fieldsMetadata = fieldsMetadata;
	}

	public boolean isRequired()
	{
		return required;
	}

	public void setRequired(boolean required)
	{
		this.required = required;
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

	public void setIndex(int index)
	{
		this.index = index;
	}

	@Override
	public int compareTo(ParameterMetadata o)
	{
		return Integer.valueOf(this.index).compareTo(o.index);
	}
}
