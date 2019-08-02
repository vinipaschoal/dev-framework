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
	 * Converte a string JSON para JsonNode.
	 */
	public static JsonNode fromStringToJsonNode(String jsonString) throws JsonDataException
	{
		try
		{
			return _mapper.readTree(jsonString);	
		}
		catch (Exception e)
		{
			throw new JsonDataException(e);
		}
	}

	/**
	 * Converte o objeto para JsonNode.
	 */
	public static JsonNode fromObjectToJsonNode(Object object) throws JsonDataException
	{
		try
		{
			return _mapper.valueToTree(object);
		}
		catch (Exception e)
		{
			throw new JsonDataException(e);
		}
	}
	
	/**
	 * Converte o objeto para JsonData.
	 */
	public static JsonData fromObjectToJsonData(Object object) throws JsonDataException
	{
		return convertToJsonData(stringify(object));
	}

	/**
	 * Converte a string JSON para a classe informada. 
	 */
	public static <E> E convertTo(String jsonString, Class<E> typeClass) throws JsonDataException
	{
		try
		{
			return _mapper.readValue(jsonString, typeClass);
		}
		catch (Exception e)
		{
			throw new JsonDataException(e);
		}
	}
	
	/**
	 * Converte a string JSON para um mapa. 
	 */
	public static Map<String,String> convertToMap(String jsonString) throws JsonDataException
	{
		Map<String,String> map = new HashMap<>();
		
		// lambda nao repassa excecoes checadas
		fromStringToJsonNode(jsonString).fieldNames().forEachRemaining((k) -> {
			try
			{
				map.put(k, getProperty(jsonString, k));
			}
			catch (JsonDataException e)
			{
				throw e;
			}
		});
		
		// 
		return map;
	}
	
	/**
	 * Converte a string JSON para uma lista do tipo da classe informada.
	 */
	public static <E> List<E> convertToList(String jsonString, Class<E> typeClass) throws JsonDataException
	{
		try
		{
			return _mapper.readValue(jsonString, 
					_mapper.getTypeFactory().constructCollectionType(List.class, typeClass));
		}
		catch (Exception e)
		{
			throw new JsonDataException(e);
		}
	}
	
	/**
	 * Converte a string JSON para um array do tipo da classe informada.
	 */
	public static <E> E[] convertToArray(String jsonString, Class<E> typeClass) throws JsonDataException
	{
		try
		{
			return _mapper.readValue(jsonString, 
					_mapper.getTypeFactory().constructArrayType(typeClass));
		}
		catch (Exception e)
		{
			throw new JsonDataException(e);
		}
	}
	
	/**
	 * Converte a string JSON para um objeto JsonData.
	 */
	public static JsonData convertToJsonData(String jsonString) throws JsonDataException
	{
		// obtem o JsonNode
		JsonNode jsonNode = fromStringToJsonNode(jsonString);
		
		// verifica qual o tipo de JSON representa
		if ( jsonNode.isValueNode() )
			return convertToJsonPrimitive(jsonString, Object.class);
		
		if ( jsonNode.isArray() )
			return convertToJsonArray(jsonString, Object.class);
		
		if ( jsonNode.isObject() )
			return convertToJsonObject(jsonString);
		
		// tipo nao suportado
		throw new JsonDataException("Tipo nao suportado de dado JSON!");
	}
	
	/**
	 * Converte a string JSON para um objeto JsonPrimitive do tipo da classe informada.
	 */
	public static <E> JsonPrimitive<E> convertToJsonPrimitive(String jsonString, Class<E> typeClass) throws JsonDataException
	{
		try
		{
			return _mapper.readValue(jsonString, 
					_mapper.getTypeFactory().constructParametricType(JsonPrimitive.class, typeClass));
		}
		catch (Exception e)
		{
			throw new JsonDataException(e);
		}
	}

	/**
	 * Converte a string JSON para um objeto JsonArray do tipo da classe informada.
	 */
	public static <E> JsonArray<E> convertToJsonArray(String jsonString, Class<E> typeClass) throws JsonDataException
	{
		try
		{
			return _mapper.readValue(jsonString, 
					_mapper.getTypeFactory().constructParametricType(JsonArray.class, typeClass));
		}
		catch (Exception e)
		{
			throw new JsonDataException(e);
		}
	}
	
	/**
	 * Converte a string JSON para um objeto JsonObject.
	 */
	public static JsonObject convertToJsonObject(String jsonString) throws JsonDataException
	{
		return convertTo(jsonString, JsonObject.class);
	}
	
	/**
	 * Retorna o valor de uma propriedade contida na string JSON.
	 */
	public static String getProperty(String jsonString, String property) throws JsonDataException
	{
		JsonNode prop = fromStringToJsonNode(jsonString).get(property); 

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
	public static Map<String,String> getPropertyAsMap(String jsonString, String property) throws JsonDataException
	{
		return convertToMap(getProperty(jsonString, property));
	}
	
	/**
	 * Retorna o valor de uma propriedade contida na string JSON convertido para uma lista do tipo da classe informada.
	 */
	public static <E> List<E> getPropertyAsList(String jsonString, String property, Class<E> typeClass) throws JsonDataException
	{
		return convertToList(getProperty(jsonString, property), typeClass);
	}

	
	/**
	 * Retorna o valor de uma propriedade contida na string JSON convertido para um array do tipo da classe informada.
	 */
	public static <E> E[] getPropertyAsArray(String jsonString, String property, Class<E> typeClass) throws JsonDataException
	{
		return convertToArray(getProperty(jsonString, property), typeClass);
	}

	/**
	 * Retorna o valor de uma propriedade contida na string JSON convertido para um objeto JsonData.
	 */
	public static JsonData getPropertyAsJsonData(String jsonString, String property) throws JsonDataException
	{
		return convertToJsonData(getProperty(jsonString, property));
	}
	
	/**
	 * Retorna o valor de uma propriedade contida na string JSON convertido para um objeto JsonPrimitive do tipo da classe informada.
	 */
	public static <E> JsonPrimitive<E> getPropertyAsJsonPrimitive(String jsonString, String property, Class<E> typeClass) throws JsonDataException
	{
		return convertToJsonPrimitive(getProperty(jsonString, property), typeClass);
	}

	/**
	 * Retorna o valor de uma propriedade contida na string JSON convertido para um objeto JsonArray do tipo da classe informada.
	 */
	public static <E> JsonArray<E> getPropertyAsJsonArray(String jsonString, String property, Class<E> typeClass) throws JsonDataException
	{
		return convertToJsonArray(getProperty(jsonString, property), typeClass);
	}
	
	/**
	 * Retorna o valor de uma propriedade continda na string JSON convertido para um objeto JsonObject.
	 */
	public static JsonObject getPropertyAsJsonObject(String jsonString, String property) throws JsonDataException
	{
		return convertToJsonObject(getProperty(jsonString, property));
	}
	
	/**
	 * Retorna o valor de uma propriedade contida na string JSON convertido para a classe informada. 
	 */
	public static <E> E getPropertyAs(String jsonString, String property, Class<E> typeClass) throws JsonDataException
	{
		try
		{
			return _mapper.readValue(getProperty(jsonString, property), typeClass);
		}
		catch (Exception e)
		{
			throw new JsonDataException(e);
		}
	}

	/**
	 * Converte um objeto para sua representacao JSON.
	 */
	public static String stringify(Object obj) throws JsonDataException
	{
		try
		{
			return _mapper.writeValueAsString(obj);
		}
		catch (Exception e)
		{
			throw new JsonDataException(e);
		}
	}
}
