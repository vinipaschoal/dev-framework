package org.esfinge.virtuallab.exceptions;

/**
 * Excecao relacionada ao MetadataHelper.
 */
public class MetadataException extends RuntimeException
{
	private static final long serialVersionUID = 5007472243935339056L;

	
	/**
	 * Cria uma nova excecao com a mensagem informada.
	 */
	public MetadataException(String msg)
	{
		super(msg);
	}

	/**
	 * Cria uma nova excecao com a mensagem e causa informadas.
	 */
	public MetadataException(String msg, Throwable cause)
	{
		super(msg, cause);
	}
}
