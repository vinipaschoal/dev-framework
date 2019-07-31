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
	// nome do metodo
	private String name;
	
	// tipo do retorno do metodo
	private String returnType;

	// lista de descritores dos parametros do metodo
	private List<ParameterDescriptor> parameters;

	
	/**
	 * Construtor padrao.
	 */
	public MethodDescriptor()
	{
		this.parameters = new ArrayList<ParameterDescriptor>();
	}

	/**
	 * Construtor a partir dos metadados de um metodo.
	 */
	public MethodDescriptor(MethodMetadata methodMetadata)
	{
		this();
		
		// se informado, utiliza o label do metodo; caso contrario, utiliza o nome do metodo
		this.name = Utils.isNullOrEmpty(methodMetadata.getLabel()) ?
				methodMetadata.getMethodName() : methodMetadata.getLabel();
				
		this.returnType = methodMetadata.getMethod().getReturnType().getCanonicalName();
		
		for (ParameterMetadata param : methodMetadata.getParameters())
			this.parameters.add(new ParameterDescriptor(param));
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getReturnType()
	{
		return returnType;
	}

	public void setReturnType(String returnType)
	{
		this.returnType = returnType;
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
