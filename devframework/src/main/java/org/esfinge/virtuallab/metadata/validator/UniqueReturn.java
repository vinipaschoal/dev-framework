package org.esfinge.virtuallab.metadata.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.esfinge.metadata.annotation.validator.ToValidate;

/**
 * Utilizado nas anotacoes que manipulam o retorno de metodos, como por exemplo @TableReturn.
 * Nao pode exister mais do que um manipulador de retorno para um metodo. 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@ToValidate(UniqueReturnValidator.class)
public @interface UniqueReturn
{

}
