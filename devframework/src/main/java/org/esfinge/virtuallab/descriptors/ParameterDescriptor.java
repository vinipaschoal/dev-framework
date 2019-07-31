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
	
	// label do parametro
	private String label;
	
	// tipo do parametro
	private String dataType;
	
	// posicao do parametro no metodo
	private int index;

	
	/**
	 * Construtor a partir dos metadados de uma classe.
	 */
	public ParameterDescriptor(ParameterMetadata parameterMetadata)
	{
		this.name = parameterMetadata.getParameterName();
		this.dataType = parameterMetadata.getParameter().getType().getCanonicalName();		
		this.index = parameterMetadata.getIndex();
		
		// verifica se foi informado um label para o parametro
		this.label = Utils.isNullOrEmpty(parameterMetadata.getLabel()) ?
				this.name : parameterMetadata.getLabel();
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
