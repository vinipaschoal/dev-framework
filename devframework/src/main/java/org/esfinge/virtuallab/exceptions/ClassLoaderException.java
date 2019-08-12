package org.esfinge.virtuallab.exceptions;

/**
 * Excecao relacionada ao ClassLoaderService.
 */
public class ClassLoaderException extends RuntimeException
{
	private static final long serialVersionUID = 45692391362883L;

	
	/**
	 * Cria uma nova excecao com a mensagem informada.
	 */
	public ClassLoaderException(String msg)
	{
		super(msg);
	}

	/**
	 * Cria uma nova excecao com a mensagem e causa informadas.
	 */
	public ClassLoaderException(String msg, Throwable cause)
	{
		super(msg, cause);
	}
}
