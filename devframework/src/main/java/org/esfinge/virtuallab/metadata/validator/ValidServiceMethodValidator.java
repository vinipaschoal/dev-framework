package org.esfinge.virtuallab.metadata.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.esfinge.virtuallab.services.ValidationService;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;

/**
 * Valida metodos anotados com @ServiceMethod.
 */
public class ValidServiceMethodValidator implements AnnotationValidator
{
	@Override
	public void initialize(Annotation self)
	{
	}

	@Override
	public void validate(Annotation toValidate, AnnotatedElement annotated) throws AnnotationValidationException
	{
		// cast para o metodo
		Method method = (Method) annotated;
		
		try
		{
			// valida o metodo
			ValidationService.getInstance().assertValidMethod(method);
		}
		catch ( Exception e )
		{
			throw new AnnotationValidationException(String.format("Falha ao validar o metodo '%s': \n%s", method.getName(), e));
		}
	}
}
