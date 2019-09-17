package org.esfinge.virtuallab.web.json;
import java.util.LinkedHashMap;
import java.util.Map;

import org.esfinge.virtuallab.utils.JsonUtils;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Classe representando objetos JSON.
 */
public class JsonObject extends JsonData
{
	// armazena os elementos que serao transformados em JSON
	@JsonIgnore
	private Map<String,Object> jsonObject;
	
	
	/**
	 * Construtor padrao.
	 */
	public JsonObject()
	{
		// LinkedHashMap: mantem a ordem de insercao
		this.jsonObject = new LinkedHashMap<>();
	}

	/**
	 * Adiciona uma propriedade ao objeto JSON.
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
	protected String toJsonString()
	{
		return JsonUtils.stringify(this.jsonObject);
	}
}
