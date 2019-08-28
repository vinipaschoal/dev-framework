package org.esfinge.virtuallab.metadata.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;

/**
 * Verifica se o metodo anotado com @ServiceMethod possui mais do que um renderizador de retorno.
 */
public class MethodReturnValidator implements AnnotationValidator
{

	@Override
	public void initialize(Annotation self)
	{
	}

	@Override
	public void validate(Annotation toValidate, AnnotatedElement annotated) throws AnnotationValidationException
	{
		List<String> returnAnnotations = new ArrayList<>();
		
		for (Annotation an : ((Method) annotated).getAnnotations())
			if (an.annotationType().isAnnotationPresent(MethodReturn.class))
				returnAnnotations.add("@" + an.annotationType().getSimpleName());
		
		if (returnAnnotations.size() > 1)
			throw new AnnotationValidationException("O método " + ((Method) annotated).getName() + " da classe "
					+ ((Method) annotated).getDeclaringClass().getName() + " possui mais de um manipulador de retorno: "
					+ returnAnnotations.toString());
	}
}
