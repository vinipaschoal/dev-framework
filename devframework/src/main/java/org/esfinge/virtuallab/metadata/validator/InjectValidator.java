package org.esfinge.virtuallab.metadata.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

import org.esfinge.virtuallab.metadata.ClassMetadata;
import org.esfinge.virtuallab.metadata.MetadataHelper;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;

/**
 * Verifica o tipo do campo anotado com @InvokerProxy.
 */
public class InjectValidator implements AnnotationValidator
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

		try
		{
			// obtem os metadados do tipo do campo
			ClassMetadata metadata = MetadataHelper.getInstance().getClassMetadata(field.getType());
			
			// verifica se eh uma @ServiceClass ou @ServiceDAO
			if (! (metadata.isServiceClass() || metadata.isServiceDAO()) )
				throw new AnnotationValidationException("O campo '" + field.getName() + "' da classe '"
						+ field.getDeclaringClass().getName() + "' anotado com @Inject deve ser uma classe do tipo @ServiceClass ou @ServiceDAO");
			
		}
		catch ( Exception exc )
		{
			throw new AnnotationValidationException("Erro ao validar o campo '" + field.getName() + "' da classe '"
					+ field.getDeclaringClass().getName() + "' anotado com @Inject: \n" + exc.toString());
		}
	}
}