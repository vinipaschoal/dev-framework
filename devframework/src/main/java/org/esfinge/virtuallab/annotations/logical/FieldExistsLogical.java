package org.esfinge.virtuallab.annotations.logical;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.esfinge.virtuallab.annotations.TableStructure;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;

public class FieldExistsLogical implements AnnotationValidator {

	@Override
	public void initialize(Annotation self) {

	}

	@Override
	public void validate(Annotation toValidate, AnnotatedElement annotated) throws AnnotationValidationException {
		for (String fieldName : ((TableStructure) toValidate).fields()) {
			try {
				((Class<?>) annotated).getDeclaredField(fieldName);
			} catch (NoSuchFieldException | SecurityException e) {
				throw new AnnotationValidationException(
						"O campo " + fieldName + " da anotado em @TableStructure na classe "
								+ ((Class<?>) annotated).getName() + " não existe");
			}
		}
	}

}
