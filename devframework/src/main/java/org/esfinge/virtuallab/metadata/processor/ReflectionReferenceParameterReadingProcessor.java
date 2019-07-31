package org.esfinge.virtuallab.metadata.processor;

import static org.apache.commons.beanutils.PropertyUtils.setProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

import net.sf.esfinge.metadata.AnnotationReadingException;
import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.container.AnnotationReadingProcessor;
import net.sf.esfinge.metadata.container.ContainerTarget;

/**
 * Processa o tipo Reflection do parametro nos parametros de metodos.
 */
public class ReflectionReferenceParameterReadingProcessor implements AnnotationReadingProcessor
{
	// atributo anotado com @ReflectionReferenceParameter
	private Field fieldAnnoted;


	@Override
	public void initAnnotation(Annotation an, AnnotatedElement elementWithMetadata) throws AnnotationValidationException
	{
		// o atributo que recebera o tipo Reflection do parametro
		fieldAnnoted = (Field) elementWithMetadata;
	}

	@Override
	public void read(AnnotatedElement elementWithMetadata, Object container, ContainerTarget target)
			throws AnnotationReadingException
	{
		try
		{
			if (target == ContainerTarget.PARAMETER)
				setProperty(container, fieldAnnoted.getName(), elementWithMetadata);
		}
		catch ( Exception e)
		{
			throw new AnnotationReadingException(
					"Cannot read and record the reflectionReferenceParameter in the field " + fieldAnnoted.getName(), e);
		}
	}

}
