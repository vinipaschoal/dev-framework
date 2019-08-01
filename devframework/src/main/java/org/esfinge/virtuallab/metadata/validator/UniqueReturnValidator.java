package org.esfinge.virtuallab.metadata.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;

/**
 * Verifica se o metodo anotado com @ServiceMethod possui mais do que um manipulador de retorno.
 */
public class UniqueReturnValidator implements AnnotationValidator
{

	@Override
	public void initialize(Annotation self)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void validate(Annotation toValidate, AnnotatedElement annotated) throws AnnotationValidationException
	{
		List<String> uniqueAnnotations = new ArrayList<>();
		
		for (Annotation an : ((Method) annotated).getAnnotations())
			if (an.annotationType().isAnnotationPresent(UniqueReturn.class))
				uniqueAnnotations.add("@" + an.annotationType().getSimpleName());
		
		if (uniqueAnnotations.size() > 1)
			throw new AnnotationValidationException("O método " + ((Method) annotated).getName() + " da classe "
					+ ((Method) annotated).getDeclaringClass().getName() + " possui mais de um manipulador de retorno: "
					+ uniqueAnnotations.toString());
	}
}
