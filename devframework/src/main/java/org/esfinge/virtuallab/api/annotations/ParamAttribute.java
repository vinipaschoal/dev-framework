package org.esfinge.virtuallab.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Permite definir metadados para os atributos das classes dos parametros dos metodos.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ParamAttribute
{
	// nome do campo da classe do parametro
	String name();
	
	// rotulo para o campo da classe do parametro
	// por padrao usa o nome do atributo
	String label () default "";
	
	// se o atributo eh obrigatorio ou permite que o valor nao seja informado, assumindo NULL.
	// por padrao todos os atributos sao obrigatorios
	boolean required() default true;
}
