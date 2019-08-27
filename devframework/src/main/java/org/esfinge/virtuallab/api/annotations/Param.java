package org.esfinge.virtuallab.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Permite definir metadados para os parametros dos metodos.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Param
{
	// rotulo para o parametroØ
	// por padrao usa o nome do parametro
	String label() default "";
	
	// os metadados para os campos da classe do parametro (se houver)
	ParamAttribute[] fields() default {};
	
	// se o parametro eh obrigatorio ou permite que o valor nao seja informado, assumindo NULL.
	// por padrao todos os parametros sao obrigatorios
	boolean required() default true;	
}
