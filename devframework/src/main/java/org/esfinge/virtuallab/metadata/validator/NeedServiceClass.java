package org.esfinge.virtuallab.metadata.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.esfinge.metadata.annotation.validator.ToValidate;

/**
 * Utilizado na anotacao @ServiceMethod.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@ToValidate(NeedServiceClassValidator.class)
public @interface NeedServiceClass
{
}