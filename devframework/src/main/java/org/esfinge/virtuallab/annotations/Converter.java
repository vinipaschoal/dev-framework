package org.esfinge.virtuallab.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Permite especificar um conversor para um parametro. 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Converter
{
	Class<? extends org.esfinge.virtuallab.converters.Converter<?>> value();
}
