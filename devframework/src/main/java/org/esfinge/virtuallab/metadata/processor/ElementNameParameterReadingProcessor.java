package org.esfinge.virtuallab.metadata.processor;

import static org.apache.commons.beanutils.PropertyUtils.setProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

import net.sf.esfinge.metadata.AnnotationReadingException;
import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.container.AnnotationReadingProcessor;
import net.sf.esfinge.metadata.container.ContainerTarget;

/**
 * Processa o nome do parametro nos parametros de metodos.
 */
public class ElementNameParameterReadingProcessor implements AnnotationReadingProcessor
{
	// atributo anotado com @ElementNameParameter
	private Field fieldAnnoted;

	@Override
	public void initAnnotation(Annotation an, AnnotatedElement elementWithMetadata) throws AnnotationValidationException
	{
		// o atributo que recebera o nome do parametro
		fieldAnnoted = (Field) elementWithMetadata;
	}

	@Override
	public void read(AnnotatedElement elementWithMetadata, Object container, ContainerTarget target)
			throws AnnotationReadingException
	{
		try
		{
			if (target == ContainerTarget.PARAMETER)
				if (elementWithMetadata.getClass().equals(Parameter.class))
					setProperty(container, fieldAnnoted.getName(), ((Parameter) elementWithMetadata).getName());
		}
		catch ( Exception e)
		{
			throw new AnnotationReadingException(
					"Cannot read and record the elementNameParameter in the field " + fieldAnnoted.getName(), e);
		}
	}

}
