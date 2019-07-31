package org.esfinge.virtuallab.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.esfinge.virtuallab.metadata.validator.NoVoidReturn;

import net.sf.esfinge.metadata.annotation.validator.Prohibits;

/**
 * Indica que o retorno de um servico sera no formato JSON.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@NoVoidReturn
@Prohibits(TableReturn.class)
public @interface JsonReturn
{
}