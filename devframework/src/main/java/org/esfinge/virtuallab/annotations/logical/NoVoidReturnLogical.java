package org.esfinge.virtuallab.annotations.logical;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;

public class NoVoidReturnLogical implements AnnotationValidator {

	@Override
	public void initialize(Annotation self) {

	}

	@Override
	public void validate(Annotation toValidate, AnnotatedElement annotated) throws AnnotationValidationException {
		if ("void".equals(((Method) annotated).getReturnType().getName())) {
			throw new AnnotationValidationException("O método " + ((Method) annotated).getName() + " da classe "
					+ ((Method) annotated).getClass().getName() + " não possui retorno");
		}
	}

}
