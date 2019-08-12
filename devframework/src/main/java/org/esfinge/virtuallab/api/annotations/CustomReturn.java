package org.esfinge.virtuallab.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.esfinge.virtuallab.metadata.processors.CustomReturnProcessor;
import org.esfinge.virtuallab.metadata.validator.MethodReturn;

/**
 * Permite customizar o retorno de um metodo.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@MethodReturn(processor = CustomReturnProcessor.class)
public @interface CustomReturn
{
	// os campos da classe retornada que serao utilizados
	// por padrao pega todos os atributos da classe 
	String[] fields() default {};
	
	// os labels para os campos da classe retornada
	// por padrao usa o nome do atributo
	String[] labels() default {};
}