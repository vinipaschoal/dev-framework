package org.esfinge.virtuallab.metadata.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;

/**
 * Verifica se a classe anotada com @ServiceClass possui um construtor publico padrao (sem argumentos).
 */
public class NeedDefaultConstructorValidator implements AnnotationValidator
{

	@Override
	public void initialize(Annotation self)
	{
	}

	@Override
	public void validate(Annotation toValidate, AnnotatedElement annotated) throws AnnotationValidationException
	{
		try
		{
			// tenta criar uma instancia da classe utilizando o construtor padrao
			((Class<?>) annotated).newInstance();
		}
		catch ( Exception e )
		{
			throw new AnnotationValidationException("A classe '" + ((Class<?>) annotated).getCanonicalName() + "' não possui um construtor público padrão (sem argumentos)");
		}
	}
}
