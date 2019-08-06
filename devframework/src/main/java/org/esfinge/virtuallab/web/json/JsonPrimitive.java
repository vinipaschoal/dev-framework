package org.esfinge.virtuallab.web.json;

import org.esfinge.virtuallab.utils.JsonUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Classe representando tipos escalares JSON (string, number, boolean e null).
 */
public class JsonPrimitive<E> extends JsonData
{
	// armazena o elemento que sera transformado em JSON
	private E value;

	
	/**
	 * Construtor padrao.
	 */
	public JsonPrimitive()
	{
		
	}
	
	/**
	 * Cria o primitivo JSON com o valor informado.
	 */
	@JsonCreator
	public JsonPrimitive(E value)
	{
		this.setValue(value);
	}

	/**
	 * Atribui o valor do JSON.
	 */
	public void setValue(E value)
	{
		try
		{
			// transforma o valor para JSON
			JsonNode jsonNode = JsonUtils.fromObjectToJsonNode(value);
			
			// verifica se eh um primitivo valido
			if ( (jsonNode != null) && !jsonNode.isValueNode() )
				throw new Exception("Valor informado não é um primitivo JSON valido!");
			
			// valor OK
			this.value = value;
		}
		catch ( Exception e )
		{
			throw new JsonDataException(e);
		}
	}
	
	/**
	 * Retorna o valor do JSON.
	 */
	@JsonValue
	public Object getValue()
	{
		return this.value;
	}
	
	/**
	 * Retorna a representacao string desse primitivo JSON.
	 */
	protected String toJsonString()
	{
		return JsonUtils.stringify(this.value);
	}

}
