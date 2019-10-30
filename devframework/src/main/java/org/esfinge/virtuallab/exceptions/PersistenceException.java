package org.esfinge.virtuallab.exceptions;

/**
 * Excecao relacionada ao PersistenceService.
 */
public class PersistenceException extends RuntimeException
{
	private static final long serialVersionUID = 6395251397295069505L;

	
	/**
	 * Cria uma nova excecao com a mensagem informada.
	 */
	public PersistenceException(String msg)
	{
		super(msg);
	}

	/**
	 * Cria uma nova excecao com a mensagem e causa informadas.
	 */
	public PersistenceException(String msg, Throwable cause)
	{
		super(msg, cause);
	}
}
