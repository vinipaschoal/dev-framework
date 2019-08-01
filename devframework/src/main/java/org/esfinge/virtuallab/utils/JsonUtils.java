package org.esfinge.virtuallab.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Metodos utilitarios para manipular strings representando objetos JSON.
 */
public class JsonUtils
{
	// objeto responsavel pelo mapeamento JSON
	private static final ObjectMapper _mapper = new ObjectMapper();
	
	
	/**
	 * Retorna a string JSON mapeado em nodes.
	 */
	public static JsonNode getJsonNode(String jsonString) throws Exception
	{
		return _mapper.readTree(jsonString);
	}

	/**
	 * Converte a string JSON para a classe informada. 
	 */
	public static <E> E convertTo(String jsonString, Class<E> typeClass) throws Exception
	{
		return _mapper.readValue(jsonString, typeClass);
	}
	
	/**
	 * Converte a string JSON para um mapa. 
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
	 * Converte a string JSON para um objeto JsonObject.
	 */
	public static JsonObject convertToJsonObject(String jsonString) throws Exception
	{
		return convertTo(jsonString, JsonObject.class);
	}
	
	/**
	 * Converte a string JSON para uma lista do tipo da classe informada.
	 */
	public static <E> List<E> convertToList(String jsonString, Class<E> typeClass) throws Exception
	{
		return _mapper.readValue(jsonString, 
				_mapper.getTypeFactory().constructCollectionType(List.class, typeClass));
	}
	
	/**
	 * Converte a string JSON para um array do tipo da classe informada.
	 */
	public static <E> E[] convertToArray(String jsonString, Class<E> typeClass) throws Exception
	{
		return _mapper.readValue(jsonString, 
				_mapper.getTypeFactory().constructArrayType(typeClass));
	}

	/**
	 * Converte a string JSON para um objeto JsonArray do tipo da classe informada.
	 */
	public static <E> JsonArray<E> convertToJsonArray(String jsonString, Class<E> typeClass) throws Exception
	{
		return _mapper.readValue(jsonString, 
				_mapper.getTypeFactory().constructParametricType(JsonArray.class, typeClass));
	}
	
	/**
	 * Retorna o valor de uma propriedade contida na string JSON.
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
	 * Retorna o valor de uma propriedade contida na string JSON convertido para mapa. 
	 */
	public static Map<String,String> getPropertyAsMap(String jsonString, String property) throws Exception
	{
		return convertToMap(getProperty(jsonString, property));
	}
	
	/**
	 * Retorna o valor de uma propriedade continda na string JSON convertido para um objeto JsonObject.
	 */
	public static JsonObject getPropertyAsJsonObject(String jsonString, String property) throws Exception
	{
		return convertToJsonObject(getProperty(jsonString, property));
	}
	
	/**
	 * Retorna o valor de uma propriedade contida na string JSON convertido para uma lista do tipo da classe informada.
	 */
	public static <E> List<E> getPropertyAsList(String jsonString, String property, Class<E> typeClass) throws Exception
	{
		return convertToList(getProperty(jsonString, property), typeClass);
	}

	
	/**
	 * Retorna o valor de uma propriedade contida na string JSON convertido para um array do tipo da classe informada
	 */
	public static <E> E[] getPropertyAsArray(String jsonString, String property, Class<E> typeClass) throws Exception
	{
		return convertToArray(getProperty(jsonString, property), typeClass);
	}
	
	/**
	 * Retorna o valor de uma propriedade contida na string JSON convertido para um objeto JsonArray do tipo da classe informada
	 */
	public static <E> JsonArray<E> getPropertyAsJsonArray(String jsonString, String property, Class<E> typeClass) throws Exception
	{
		return convertToJsonArray(getProperty(jsonString, property), typeClass);
	}

	/**
	 * Retorna o valor de uma propriedade contida na string JSON convertido para a classe informada. 
	 */
	public static <E> E getPropertyAs(String jsonString, String property, Class<E> typeClass) throws Exception
	{
		return _mapper.readValue(getProperty(jsonString, property), typeClass);
	}

	/**
	 * Converte um objeto para sua representacao JSON.
	 */
	public static String stringify(Object obj) throws Exception
	{
		return _mapper.writeValueAsString(obj);
	}
}
