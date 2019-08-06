package org.esfinge.virtuallab.web.json;

/**
 * Classe representando informacoes basicas de Schema de elementos JSON.
 */
public class JsonSchemaElement implements JsonSchema
{
	// tipo do elemento
	private String type;
	
	// label do elemento
	private String title;
	
	// se o preenchimento do elemento eh obrigatorio
	private boolean required;
	
	// formato do tipo do elemento
	private String format;

	
	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public boolean isRequired()
	{
		return required;
	}

	public void setRequired(boolean required)
	{
		this.required = required;
	}

	public String getFormat()
	{
		return format;
	}

	public void setFormat(String format)
	{
		this.format = format;
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder("{ ");
		builder.append(String.format("\"type\" : \"%s\", ", this.type));
		builder.append(String.format("\"title\" : \"%s\", ", this.title));
		builder.append(String.format("\"required\" : \"%b\"", this.required));
		
		if ( this.format != null )
			builder.append(String.format(", \"format\" : \"%s\"", this.format));
		
		builder.append(" }");
		return builder.toString();
	}
}
