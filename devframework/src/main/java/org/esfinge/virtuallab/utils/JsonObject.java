package org.esfinge.virtuallab.utils;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * Classe representando objetos JSON.
 */
public class JsonObject
{
	// armazena os elementos que serao transformados em JSON
	private Map<String,Object> jsonObject;
	
	
	/**
	 * Construtor padrao.
	 */
	public JsonObject()
	{
		this.jsonObject = new HashMap<>();
	}
	
	/**
	 * Adiciona uma propriedade no objeto JSON.
	 */
	@JsonAnySetter
	public void addProperty(String name, Object value)
	{
		this.jsonObject.put(name, value);
	}

	/**
	 * Remove uma propriedade do objeto JSON.
	 */
	public Object removeProperty(String name)
	{
		return jsonObject.remove(name);
	}
	
	/**
	 * Retorna uma propriedade do objeto JSON.
	 */
	public Object getProperty(String name)
	{
		return jsonObject.get(name);
	}
	
	/**
	 * Retorna os elementos do objeto JSON em forma de mapa.
	 */
	@JsonAnyGetter
	public Map<String,Object> toMap()
	{
		return this.jsonObject; 
	}
	
	/**
	 * Retorna a representacao string desse objeto JSON.
	 */
	public String toString()
	{
		try
		{
			return JsonUtils.stringify(this.jsonObject);
		}
		catch ( Exception e )
		{
			throw new RuntimeException(e);
		}
	}
}
