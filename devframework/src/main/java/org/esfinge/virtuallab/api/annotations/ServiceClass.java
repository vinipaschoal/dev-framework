package org.esfinge.virtuallab.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.esfinge.virtuallab.metadata.validator.ValidServiceClass;

/**
 * Deve ser utilizada nas classes que irao disponibilizar metodos como servicos.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@ValidServiceClass
public @interface ServiceClass
{
	// rotulo para a classe
	// por padrao usa o nome da classe
	String label() default "";
	
	// texto informativo sobre os tipos de servicos que a classe disponibiliza
	String description() default "";
}