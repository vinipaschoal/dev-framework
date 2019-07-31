package org.esfinge.virtuallab.metadata.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.esfinge.virtuallab.annotations.TableStructure;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;

/**
 * Verifica se os campos informados na anotacao @TableStructure existem na classe. 
 */
public class FieldExistsValidator implements AnnotationValidator
{
	@Override
	public void initialize(Annotation self)
	{
	}

	@Override
	public void validate(Annotation toValidate, AnnotatedElement annotated) throws AnnotationValidationException
	{
		for (String fieldName : ((TableStructure) toValidate).fields())
		{
			try
			{
				((Class<?>) annotated).getDeclaredField(fieldName);
			}
			catch (NoSuchFieldException | SecurityException e)
			{
				throw new AnnotationValidationException(
						"O campo " + fieldName + " informado em @TableStructure não existe na classe "
								+ ((Class<?>) annotated).getName());
			}
		}
	}
}
