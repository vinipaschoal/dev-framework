package org.esfinge.virtuallab.web;

import org.esfinge.virtuallab.web.json.JsonData;

/**
 * Armazena as informacoes de resposta para as requisicoes, 
 * retornado para a UI como umo objeto JSON.
 */
public class JsonReturn
{
	// o tipo do objeto de resposta
	private String type;

	// o resultado da requisicao
	private boolean success;

	// mensagem de retorno relacionada a requisicao
	private String message;

	// os dados de retorno da requisicao
	private JsonData data;

	
	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public JsonData getData()
	{
		return data;
	}

	public void setData(JsonData data)
	{
		this.data = data;
	}
}
