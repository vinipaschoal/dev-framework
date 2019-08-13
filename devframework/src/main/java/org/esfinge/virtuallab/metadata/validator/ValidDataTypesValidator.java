package org.esfinge.virtuallab.metadata.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.esfinge.virtuallab.utils.ReflectionUtils;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;

/**
 * Verifica se os parametros e o retorno do metodo anotado com @ServiceMethod possuem tipos validos.
 */
public class ValidDataTypesValidator implements AnnotationValidator
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
		
		// verifica o tipo do retorno
		Class<?> returnClass = method.getReturnType();
		
		// tipos validos: basicos / array / colecao / objeto JSON valido
		if (! ReflectionUtils.isBasicType(returnClass) )
			if (! ReflectionUtils.isArray(returnClass) )
				if (! ReflectionUtils.isCollection(returnClass) )
					if (! ReflectionUtils.isValidJsonObject(returnClass) )
						throw new AnnotationValidationException("O metodo '" + method.getName()
								+ "' retorna um objeto não suportado (" + returnClass.getCanonicalName() + ")");
		
		// verifica os tipos dos parametros
		// tipos validos: basico / objeto JSON valido
		for ( Class<?> paramClass : method.getParameterTypes() )
			if (! ReflectionUtils.isBasicType(paramClass) )
				if (! ReflectionUtils.isValidJsonObject(paramClass) )
					throw new AnnotationValidationException("O metodo '" + method.getName()
							+ "' possui um parâmetro não suportado (" + paramClass.getCanonicalName() + ")");
	}
}
