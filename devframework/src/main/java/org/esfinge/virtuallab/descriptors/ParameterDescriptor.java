package org.esfinge.virtuallab.descriptors;

import org.esfinge.virtuallab.metadata.ParameterMetadata;
import org.esfinge.virtuallab.utils.Utils;

/**
 * Descritor de parametros de metodos.
 */
public class ParameterDescriptor
{
	// nome do parametro
	private String name;
	
	// tipo do parametro
	private String dataType;
	
	// posicao do parametro no metodo
	private int index;

	
	/**
	 * Construtor padrao.
	 */
	public ParameterDescriptor() 
	{		
	}	

	/**
	 * Construtor a partir dos metadados de uma classe.
	 */
	public ParameterDescriptor(ParameterMetadata parameterMetadata)
	{
		this();
		
		// se informado, utiliza o label do parametro; caso contrario, utiliza o nome do parametro
		this.name = Utils.isNullOrEmpty(parameterMetadata.getLabel()) ?
				parameterMetadata.getParameterName() : parameterMetadata.getLabel();
				
		this.dataType = parameterMetadata.getParameter().getType().getCanonicalName();		
		this.index = parameterMetadata.getIndex();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDataType()
	{
		return dataType;
	}

	public void setDataType(String dataType)
	{
		this.dataType = dataType;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}
}
