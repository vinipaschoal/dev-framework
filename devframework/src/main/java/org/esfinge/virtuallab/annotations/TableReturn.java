package org.esfinge.virtuallab.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import org.esfinge.virtuallab.annotations.processors.TableReturnProcessor;
import org.esfinge.virtuallab.metadata.validator.UniqueReturn;

import net.sf.esfinge.metadata.annotation.validator.method.ValidMethodReturn;

/**
 * Indica que o retorno de um metodo sera apresentado como uma tabela.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ValidMethodReturn(validTypesToReturn = { Collection.class })
@UniqueReturn(processor = TableReturnProcessor.class)
public @interface TableReturn
{
	// os campos do item da colecao que serao utilizados
	// por padrao pega todos os atributos do item
	String[] fields() default {};
	
	// os labels para o cabecalho da tabela
	// por padrao usa o nome do atributo
	String[] headerLabels() default {};
	
	// se a tabela deve conter o cabecalho ou nao
	// por padrao mostra o cabecalho
	boolean showHeader() default true;
}