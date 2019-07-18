package devframework.annotations.logical;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;

public class NeedReturnListLogical implements AnnotationValidator {

	@Override
	public void initialize(Annotation self) {
	}

	@Override
	public void validate(Annotation toValidate, AnnotatedElement annotated) throws AnnotationValidationException {
		Class<?> returnedType= ((Method) annotated).getReturnType();
		if (!(returnedType.isArray()||returnedType.equals(List.class))) {
			throw new AnnotationValidationException(
					"O método " + ((Method) annotated).getName() + " deve possuir uma lista como retorno");
		}

	}

}
