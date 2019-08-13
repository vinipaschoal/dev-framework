package org.esfinge.virtuallab.web.json;

/**
 * Excecao relacionada ao processamento de dados JSON.
 */
public class JsonDataException extends RuntimeException
{
	private static final long serialVersionUID = 6939972583850667522L;

	public JsonDataException(String msg)
	{
		super(msg);		
	}

	public JsonDataException(Exception cause)
	{
		this("Erro ao processar dados JSON", cause);
	}

	public JsonDataException(String msg, Exception cause)
	{
		super(msg, cause);
	}
}
