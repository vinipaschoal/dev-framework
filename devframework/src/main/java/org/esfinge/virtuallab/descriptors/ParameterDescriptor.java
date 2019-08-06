package org.esfinge.virtuallab.descriptors;

import org.esfinge.virtuallab.metadata.ParameterMetadata;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.utils.Utils;

/**
 * Descritor de parametros de metodos.
 */
public class ParameterDescriptor implements Comparable<ParameterDescriptor>
{
	// nome do parametro
	private String name;

	// label do parametro
	private String label;

	// tipo do parametro
	private String dataType;
	
	// se o parametro eh obrigatorio ou nao
	private boolean required;

	// posicao do parametro no metodo
	private int index;
	
	// formato JSON Schema para o parametro (utilizado pelo jsonform)
	private String jsonSchema;

	
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
		this.name = parameterMetadata.getParameterName();
		this.dataType = parameterMetadata.getParameter().getType().getCanonicalName();
		this.required = !parameterMetadata.isAnnotatedWithNotRequired();
		this.index = parameterMetadata.getIndex();

		// verifica se foi informado um label para o parametro
		this.label = Utils.isNullOrEmpty(parameterMetadata.getLabel()) ? this.name : parameterMetadata.getLabel();
		
		this.jsonSchema = JsonUtils.getJsonSchema(parameterMetadata.getParameter().getType()).toString();
		System.out.println(this.jsonSchema);
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
	
	public boolean isRequired()
	{
		return required;
	}

	public void setRequired(boolean required)
	{
		this.required = required;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}
	
	public String getJsonSchema()
	{
		return jsonSchema;
	}

	public void setJsonSchema(String jsonSchema)
	{
		this.jsonSchema = jsonSchema;
	}

	@Override
	public int compareTo(ParameterDescriptor o)
	{
		return Integer.valueOf(this.index).compareTo(o.index);
	}
}
