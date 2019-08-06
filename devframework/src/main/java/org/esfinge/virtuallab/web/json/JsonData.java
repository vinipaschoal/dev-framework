package org.esfinge.virtuallab.web.json;

/**
 * Classe base representando dados do tipo JSON.
 */
public abstract class JsonData
{
	/**
	 * Retorna a string representando o JSON.
	 */
	protected abstract String toJsonString();
	
	/**
	 * Retorna a representacao JSON.
	 */
	public final String toString()
	{
		return this.toJsonString();
	}
}
