package org.esfinge.virtuallab.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.esfinge.virtuallab.metadata.validator.NeedDefaultConstructor;

/**
 * Deve ser utilizada nas classes que irao disponibilizar metodos como servicos.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@NeedDefaultConstructor
public @interface ServiceClass
{
	// texto informativo sobre os tipos de servicos que a classe disponibiliza
	String description() default "";
}