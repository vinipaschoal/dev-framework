package org.esfinge.virtuallab.web.json;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.esfinge.virtuallab.utils.JsonUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Classe representando arrays JSON.
 */
public class JsonArray<E> extends JsonData
{
	// armazena os elementos que serao transformados em JSON
	private List<E> jsonArray;

	
	/**
	 * Construtor padrao.
	 */
	public JsonArray()
	{
		this.jsonArray = new ArrayList<>();
	}
	
	/**
	 * Cria o array JSON com os valores informados.
	 */
	@JsonCreator
	@SuppressWarnings("unchecked")
	public JsonArray(E... values)
	{
		this();
		this.add(values);
	}
	
	/**
	 * Adiciona os elementos ao array JSON.
	 */
	public JsonArray(Collection<E> values)
	{
		this();
		this.jsonArray.addAll(values);
	}
	
	/**
	 * Adiciona os elementos ao array JSON.
	 */
	@SuppressWarnings("unchecked")
	public void add(E... values)
	{
		this.jsonArray.addAll(Arrays.asList(values));
	}
	
	/**
	 * Remove um elemento do array JSON.
	 */
	public E remove(int index)
	{
		return this.jsonArray.remove(index);
	}

	/**
	 * Retorna um elemento o array JSON.
	 */
	public E getAt(int index)
	{
		return this.jsonArray.get(index);
	}

	/**
	 * Retorna os elementos do array JSON em forma de lista.
	 */
	public List<E> toList()
	{
		return this.jsonArray;
	}

	/**
	 * Retorna os elementos do array JSON em forma de array JAVA.
	 */
	@SuppressWarnings("unchecked")
	@JsonValue
	public E[] toArray()
	{
		return (E[]) this.jsonArray.toArray();
	}

	/**
	 * Retorna a representacao string desse array JSON.
	 */
	protected String toJsonString()
	{
		return JsonUtils.stringify(this.jsonArray);
	}
}
