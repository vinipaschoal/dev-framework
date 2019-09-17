package org.esfinge.virtuallab.metadata.processors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.esfinge.virtuallab.api.annotations.CustomReturn;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.utils.Utils;
import org.esfinge.virtuallab.web.json.JsonData;
import org.esfinge.virtuallab.web.json.JsonObject;
import org.springframework.util.CollectionUtils;

/**
 * Processa o retorno de um metodo customizando-o para ser apresentando na UI. 
 */
public class CustomReturnProcessor extends MethodReturnProcessor<CustomReturn>
{
	@Override
	public JsonData process(Object value) throws Exception
	{
		// verifica se nao eh nulo ou uma colecao vazia
		if (! Utils.isNullOrEmpty(value) )
		{
			// tipo do objeto
			Class<?> clazz = value.getClass();
			
			
			// tipo basico?
			if ( ReflectionUtils.isBasicType(clazz) )
				 return JsonUtils.fromObjectToJsonData(value);
			
			// colecao ou array?
			else if ( ReflectionUtils.isArray(clazz) || ReflectionUtils.isCollection(clazz) )
			{
				// cast para colecao
				List<?> collection = ReflectionUtils.isArray(clazz) 
						? CollectionUtils.arrayToList(value) 
						: new ArrayList<>((Collection<?>) value);
						
				// colecao de tipo basico?
				clazz = collection.get(0).getClass();
				if ( ReflectionUtils.isBasicType(clazz) )
					return JsonUtils.fromObjectToJsonData(value);
				
				//
				Map<String,String> returnMap = this.getMapping(clazz);
				
				// processa cada item da colecao
				List<JsonObject> list = new ArrayList<>();
				for ( Object obj : collection )
					list.add(this.processObject(obj, returnMap));
				
				return JsonUtils.fromObjectToJsonData(list);
			}
			
			// objeto
			else
				return this.processObject(value, this.getMapping(clazz));
		}
		
		// retorna null
		return null;
	}
		
	/**
	 * Customiza o objeto utilizando os campos/labels especificados na anotacao @CustomReturn.
	 */
	private JsonObject processObject(Object obj, Map<String,String> returnMap) throws Exception
	{
		JsonObject jsonObj = new JsonObject();

		// adiciona os campos do objeto
		for ( String field : returnMap.keySet() )
			jsonObj.addProperty(returnMap.get(field),
					ReflectionUtils.getFieldValue(obj, field));
		
		return jsonObj;
	}
	
	/**
	 * Retorna os campos/labels validos para o objeto.
	 */
	private Map<String,String> getMapping(Class<?> clazz)
	{
		// os campos que serao retornados
		String[] fields = this.annotation.fields().length > 0 
				? this.annotation.fields() 
				: ReflectionUtils.getAllFields(clazz)
					.stream()
					.map(f -> f.getName())
					.toArray(String[]::new);
		
		// os labels dos campos
		String[] labels = this.annotation.labels().length == fields.length  
				? this.annotation.labels() 
				: ArrayUtils.clone(fields);
				
		// verifica se os campos sao validos
		Map<String,String> returnMap = new LinkedHashMap<>(); //LinkedHashMap: mantem a ordem dos campos especificados na anotacao
		for ( int i = 0; i < fields.length; i++ )
			if ( ReflectionUtils.hasField(clazz, fields[i]) )
				returnMap.put(fields[i], labels[i]);
		
		return returnMap;
	}
	
	@Override
	public String getType()
	{
		return "PLAIN";
	}
}
