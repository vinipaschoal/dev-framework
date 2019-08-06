package org.esfinge.virtuallab.web.json;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe representando Schema de objetos JSON.
 */
public class JsonSchemaObject extends JsonSchemaElement
{
	// schema dos atributos do objeto
	private Map<String, JsonSchema> propertiesInfo;
	
	
	public JsonSchemaObject()
	{
		this.propertiesInfo = new HashMap<>();
	}
	
	@Override
	public String getType()
	{
		return "object";
	}

	public Map<String, JsonSchema> getPropertiesInfo()
	{
		return propertiesInfo;
	}

	public void setPropertiesInfo(Map<String, JsonSchema> propertiesInfo)
	{
		this.propertiesInfo = propertiesInfo;
	}
	
	public void addPropertyInfo(String name, JsonSchema propInfo)
	{
		this.propertiesInfo.put(name, propInfo);
	}
	
	public String toString()
	{
		if ( this.propertiesInfo == null || this.propertiesInfo.isEmpty() )
			throw new JsonDataException("JsonSchema dos atributos do objeto (propertiesInfo) nao poder ser nulo ou vazio!");
		
		StringBuilder builder = new StringBuilder(super.toString());

		// remove o ultimo }
		builder.deleteCharAt(builder.length() - 1);

		builder.append(", \"properties\" : { ");
		
		for ( String prop : this.propertiesInfo.keySet() )
			builder.append(String.format("\"%s\" : %s, ", prop, this.propertiesInfo.get(prop).toString()));
		
		// remmove a ultima ,
		builder.delete(builder.lastIndexOf(", "), builder.length());
		builder.append(" } }");

		return builder.toString();
	}
}
