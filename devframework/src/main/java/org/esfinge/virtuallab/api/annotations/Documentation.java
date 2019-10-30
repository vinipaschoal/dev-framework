package org.esfinge.virtuallab.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Utilizado para documentar elementos que terao 
 * a documentacao gerada automaticamente.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
public @interface Documentation
{
	// texto informativo sobre o elemento
	String value();
	
	// versao da documentacao (ou do elemento)
	float version() default 1.0f;
	
	// explicacao de como se utiliza o elemento
	String usage() default "";
	
	// valor padrao do elemento
	String defaultValue() default "";
	
	// se o elemento eh obrigatorio
	boolean required() default false;
	
	// lista de dependencias do elemento
	String[] dependencies() default {};
	
	// observacoes sobre o elemento
	String[] notes() default {};
}
