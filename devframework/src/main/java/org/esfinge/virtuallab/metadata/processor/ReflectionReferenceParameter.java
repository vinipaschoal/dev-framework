package org.esfinge.virtuallab.metadata.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.esfinge.metadata.annotation.container.AnnotationReadingConfig;

/**
 * Permite retornar o tipo Reflection do parametro ao ler os metadados dos parametros dos metodos.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@AnnotationReadingConfig(ReflectionReferenceParameterReadingProcessor.class)
public @interface ReflectionReferenceParameter
{
}
