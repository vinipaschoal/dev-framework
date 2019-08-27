package org.esfinge.virtuallab.metadata.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

import org.esfinge.virtuallab.api.InvokerProxy;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;

/**
 * Verifica o tipo do campo anotado com @InvokerProxy.
 */
public class InvokerProxyValidator implements AnnotationValidator
{
	@Override
	public void initialize(Annotation self)
	{
	}

	@Override
	public void validate(Annotation toValidate, AnnotatedElement annotated) throws AnnotationValidationException
	{
		// cast para o campo
		Field field = (Field) annotated;

		// verifica se o campo eh compativel com a interface InvokerProxy
		if (! InvokerProxy.class.isAssignableFrom(field.getType()) )
			throw new AnnotationValidationException("O campo '" + field.getName() + "' da classe '"
					+ field.getDeclaringClass().getName() + "' anotado com @InvokerProxy deve ser do tipo 'org.esfinge.virtuallab.api.InvokerProxy'");
	}
}