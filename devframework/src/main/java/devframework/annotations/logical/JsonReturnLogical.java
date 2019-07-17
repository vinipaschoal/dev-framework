package devframework.annotations.logical;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;

public class JsonReturnLogical implements AnnotationValidator{

	@Override
	public void initialize(Annotation self) {
		
	}

	@Override
	public void validate(Annotation toValidate, AnnotatedElement annotated) throws AnnotationValidationException {
		if(((Method)annotated).getReturnType() ==null) {
			System.out.println("teste");
			throw new AnnotationValidationException("O método não possui retorno");
		}
	}

}
