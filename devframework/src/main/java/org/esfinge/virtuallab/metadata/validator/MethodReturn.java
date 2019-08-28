package org.esfinge.virtuallab.metadata.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.esfinge.metadata.annotation.validator.ToValidate;

/**
 * Utilizado nas anotacoes que renderizam o retorno de metodos, como por exemplo @TableReturn.
 * Nao pode exister mais do que um renderizador de retorno para um metodo. 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@ToValidate(MethodReturnValidator.class)
public @interface MethodReturn
{
	Class<? extends org.esfinge.virtuallab.metadata.processors.MethodReturnProcessor<?>> processor();
}
