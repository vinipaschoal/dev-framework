package org.esfinge.virtuallab.metadata.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.utils.Utils;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;

/**
 * Valida classes anotadas com @ServiceClass.
 */
public class ValidServiceClassValidator implements AnnotationValidator
{

	@Override
	public void initialize(Annotation self)
	{
	}

	@Override
	public void validate(Annotation toValidate, AnnotatedElement annotated) throws AnnotationValidationException
	{
		// cast para a classe
		Class<?> clazz = (Class<?>) annotated;
		
		try
		{
			// tenta criar uma instancia da classe utilizando o construtor padrao
			clazz.newInstance();
		}
		catch ( Exception e )
		{
			throw new AnnotationValidationException(String.format(
					"A classe '%s' nao possui um construtor publico padrao (sem argumentos)!", clazz.getCanonicalName()));
		}
		
		// verifica se tem metodos publicos anotados com @ServiceMethod
		List<Method> serviceMethods = Utils.filterFromCollection(
				ReflectionUtils.getAllMethodsAnnotatedWith(clazz, ServiceMethod.class), 
				m -> Modifier.isPublic(m.getModifiers()));
		
		if ( Utils.isNullOrEmpty(serviceMethods) )
			throw new AnnotationValidationException(String.format(
					"A classe '%s' nao possui metodos publicos anotados com @ServiceMethod!", clazz.getCanonicalName()));
	}
}
