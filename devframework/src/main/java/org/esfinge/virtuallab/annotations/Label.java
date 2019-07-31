package org.esfinge.virtuallab.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Permite definir um apelido para uma classe, metodo ou parametro de servico.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Label
{
	String value();
}