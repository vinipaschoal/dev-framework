package org.esfinge.virtuallab.converters;

/**
 * Interface para converter entidades em String (e vice-versa).
 */
public interface Converter<E>
{
	/**
	 * Converte a entidade para uma representacao em String.
	 */
	public String toString(E obj);
	
	/**
	 * Restaura uma entidade da sua representacao em String.
	 */
	public E fromString(String value);
}
