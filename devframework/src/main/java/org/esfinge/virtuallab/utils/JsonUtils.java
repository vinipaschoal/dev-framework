package org.esfinge.virtuallab.utils;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Metodos utilitarios para manipular objetos JSON.
 */
public class JsonUtils
{
	// objeto responsavel pelo mapeamento do JSON
	private static final ObjectMapper mapper = new ObjectMapper();
	
	
	/**
	 * Retorna o objeto JSON mapeado em nodes.
	 */
	public static JsonNode getJsonNode(String jsonString) throws Exception
	{
		return mapper.readTree(jsonString);
	}

	/**
	 * Converte o objeto JSON para um mapa. 
	 */
	public static Map<String,String> convertToMap(String jsonString) throws Exception
	{
		Map<String,String> map = new HashMap<>();
		
		// lambda nao repassa excecoes checadas
		getJsonNode(jsonString).fieldNames().forEachRemaining((k) -> {
			try
			{
				map.put(k, getProperty(jsonString, k));
			}
			catch ( Exception e)
			{
				throw new RuntimeException(e);
			}
		});
		
		// 
		return map;
	}
	
	/**
	 * Converte o objeto JSON para a classe informada. 
	 */
	public static <E> E convertTo(String jsonString, Class<E> typeClass) throws Exception
	{
		return mapper.readValue(jsonString, typeClass);
	}
	
	/**
	 * Retorna o valor de uma propriedade contida no objeto JSON.
	 */
	public static String getProperty(String jsonString, String property) throws Exception
	{
		JsonNode prop = getJsonNode(jsonString).get(property); 

		// verifica se a propriedade eh um container (array ou outro JSON)
		if (prop.isContainerNode())
		{
			// retorna a string no formato JSON
			return prop.toString();
		}
		
		// retorna o valor convertido em string
		return prop.asText();
	}
	
	/**
	 * Retorna o valor de uma propriedade contida no objeto JSON convertido para a classe informada. 
	 */
	public static <E> E getPropertyAs(String jsonString, String property, Class<E> typeClass) throws Exception
	{
		return mapper.readValue(getProperty(jsonString, property), typeClass);
	}
}
