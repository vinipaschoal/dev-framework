package org.esfinge.virtuallab.exceptions;

/**
 * Excecao relacionada ao InvokerService.
 */
public class InvocationException extends RuntimeException
{
	private static final long serialVersionUID = -8561294521041466259L;

	
	/**
	 * Cria uma nova excecao com a mensagem informada.
	 */
	public InvocationException(String msg)
	{
		super(msg);
	}

	/**
	 * Cria uma nova excecao com a mensagem e causa informadas.
	 */
	public InvocationException(String msg, Throwable cause)
	{
		super(msg, cause);
	}
}
