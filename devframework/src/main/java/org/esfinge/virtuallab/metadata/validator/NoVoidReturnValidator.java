package org.esfinge.virtuallab.metadata.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;

/**
 * Verifica se o metodo anotado com @ServiceMethod retorna algum valor.
 */
public class NoVoidReturnValidator implements AnnotationValidator
{
	@Override
	public void initialize(Annotation self)
	{

	}

	@Override
	public void validate(Annotation toValidate, AnnotatedElement annotated) throws AnnotationValidationException
	{
		if ("void".equals(((Method) annotated).getReturnType().getName()))
		{
			throw new AnnotationValidationException("O método " + ((Method) annotated).getName() + " da classe "
					+ ((Method) annotated).getDeclaringClass().getName() + " não possui retorno");
		}
	}
}
