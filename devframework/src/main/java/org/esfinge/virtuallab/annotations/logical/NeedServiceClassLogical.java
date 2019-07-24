package org.esfinge.virtuallab.annotations.logical;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.esfinge.virtuallab.annotations.ServiceClass;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;

public class NeedServiceClassLogical implements AnnotationValidator {

	@Override
	public void initialize(Annotation self) {

	}

	@Override
	public void validate(Annotation toValidate, AnnotatedElement annotated) throws AnnotationValidationException {

		if (((Method) annotated).getDeclaringClass().getAnnotation(ServiceClass.class) == null) {
			throw new AnnotationValidationException(
					"A classe" + ((Method) annotated).getDeclaringClass().getName() + " não possui a anotação @serviceClass");
		}
	}

}
