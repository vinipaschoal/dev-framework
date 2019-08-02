package org.esfinge.virtuallab.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.esfinge.virtuallab.metadata.validator.UniqueReturn;

/**
 * Permite customizar o retorno de um servico.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@UniqueReturn
public @interface CustomReturn
{
	// os campos da classe retornada que serao utilizados
	// por padrao pega todos os atributos da classe 
	String[] fields() default {};
	
	// os labels para os campos da classe retornada
	// por padrao usa o nome do atributo
	String[] labels() default {};
}