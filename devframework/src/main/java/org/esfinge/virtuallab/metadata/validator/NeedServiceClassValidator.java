package org.esfinge.virtuallab.metadata.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.esfinge.virtuallab.annotations.ServiceClass;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;

/**
 * Verifica se a classe do metodo anotado com @ServiceMethod contem a anotacao @ServiceClass.
 */
public class NeedServiceClassValidator implements AnnotationValidator
{
	@Override
	public void initialize(Annotation self)
	{
	}

	@Override
	public void validate(Annotation toValidate, AnnotatedElement annotated) throws AnnotationValidationException
	{
		if (((Method) annotated).getDeclaringClass().getAnnotation(ServiceClass.class) == null)
		{
			throw new AnnotationValidationException("A classe '" + ((Method) annotated).getDeclaringClass().getCanonicalName()
					+ "' não possui a anotação @ServiceClass");
		}
	}
}
