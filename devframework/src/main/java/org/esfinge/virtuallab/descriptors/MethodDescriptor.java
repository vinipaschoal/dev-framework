package org.esfinge.virtuallab.descriptors;

import java.util.ArrayList;
import java.util.List;

import org.esfinge.virtuallab.metadata.MethodMetadata;
import org.esfinge.virtuallab.metadata.ParameterMetadata;
import org.esfinge.virtuallab.utils.Utils;

/**
 * Descritor de metodos.
 */
public class MethodDescriptor
{
	// nome da classe do metodo
	private String className;
	
	// nome do metodo
	private String name;
	
	// label do metodo
	private String label;
	
	// tipo do retorno do metodo
	private String returnType;
	
	// texto informativo sobre o metodo
	private String description;

	// lista de descritores dos parametros do metodo
	private List<ParameterDescriptor> parameters;

	
	/**
	 * Construtor padrao.
	 */
	public MethodDescriptor()
	{
		
	}
	
	/**
	 * Construtor a partir dos metadados de um metodo.
	 */
	public MethodDescriptor(MethodMetadata methodMetadata)
	{
		this.className = methodMetadata.getMethod().getDeclaringClass().getCanonicalName();
		this.name = methodMetadata.getMethodName();
		this.returnType = methodMetadata.getMethod().getReturnType().getCanonicalName();
		this.description = methodMetadata.getDescription();
		
		// verifica se foi informado um label para o metodo
		this.label = Utils.isNullOrEmpty(methodMetadata.getLabel()) ?
				this.name : methodMetadata.getLabel();

		// processa os parametros do metodo
		this.parameters = new ArrayList<ParameterDescriptor>();
		for (ParameterMetadata param : methodMetadata.getParameters())
			this.parameters.add(new ParameterDescriptor(param));
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getReturnType()
	{
		return returnType;
	}

	public void setReturnType(String returnType)
	{
		this.returnType = returnType;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}

	public List<ParameterDescriptor> getParameters()
	{
		return parameters;
	}

	public void setParameters(List<ParameterDescriptor> parameters)
	{
		this.parameters = parameters;
	}
}
