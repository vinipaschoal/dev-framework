package org.esfinge.virtuallab.web.json;

/**
 * Classe representando Schema de arrays JSON.
 */
public class JsonSchemaArray extends JsonSchemaElement
{
	// definicoes de schema do tipo dos elementos do array
	private JsonSchema itemsInfo;

	
	@Override
	public String getType()
	{
		return "array";
	}

	public JsonSchema getItemsInfo()
	{
		return itemsInfo;
	}

	public void setItemsInfo(JsonSchema itemsInfo)
	{
		this.itemsInfo = itemsInfo;
	}

	public String toString() throws JsonDataException
	{
		if ( this.itemsInfo == null )
			throw new JsonDataException("JsonSchema do tipo dos elementos do array (itemsInfo) nao poder ser nulo!");
			
		StringBuilder builder = new StringBuilder(super.toString());

		// remove o ultimo }
		builder.deleteCharAt(builder.length() - 1);

		builder.append(", \"items\" : ");
		builder.append(itemsInfo.toString());
		builder.append(" }");

		return builder.toString();
	}
}
