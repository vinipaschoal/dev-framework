package org.esfinge.virtuallab.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.esfinge.virtuallab.metadata.validator.NeedServiceClass;

import net.sf.esfinge.metadata.annotation.validator.method.ForbiddenMethodReturn;
import net.sf.esfinge.metadata.annotation.validator.method.MethodVisibilityRequired;

/**
 * Deve ser utilizada nos metodos que serao disponibilizados como servicos.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@NeedServiceClass
@ForbiddenMethodReturn(invalidTypesToReturn = { void.class })
@MethodVisibilityRequired(itNeedsToHaveThisVisibility = "public")
public @interface ServiceMethod
{
	// texto informativo sobre o servico
	String description() default "";
}