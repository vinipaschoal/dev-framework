package org.esfinge.virtuallab.metadata.processors;

import java.lang.annotation.Annotation;

import org.esfinge.virtuallab.web.json.JsonData;

/**
 * Processa o retorno de um metodo adequando-o ao formato a ser apresentando na UI. 
 */
public abstract class MethodReturnProcessor<A extends Annotation>
{	
	// armazena a anotacao a ser processada
	protected A annotation;
	
	
	/**
	 * Recebe a anotacao utilizada no metodo para poder processar o objeto. 
	 */
	public void initialize(A annotation)	
	{
		this.annotation = annotation;
	}
	
	/**
	 * Processa o objeto, adequando-o ao formato a ser apresentado na UI.
	 */
	public abstract JsonData process(Object value) throws Exception;
	
	/**
	 * Retorna o tipo para que a UI possa identificar e renderizar corretamente o resultado processado.  
	 */
	public abstract String getType();
}
