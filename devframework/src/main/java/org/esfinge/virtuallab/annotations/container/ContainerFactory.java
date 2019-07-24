package org.esfinge.virtuallab.annotations.container;

import java.lang.reflect.AnnotatedElement;

import net.sf.esfinge.metadata.AnnotationReader;

public class ContainerFactory {
	
	private static AnnotationReader reader = new AnnotationReader();
	
	public static <E> E create(AnnotatedElement annotatedElement, TypeContainer typeContainer) throws Exception {
		switch(typeContainer) {
		case CLASS_CONTAINER:
			return (E) reader.readingAnnotationsTo(annotatedElement, ClassContainer.class);
		case METHOD_CONTAINER:
			return (E) reader.readingAnnotationsTo(annotatedElement, MethodContainer.class);
		case FIELD_CONTAINER:
			return (E) reader.readingAnnotationsTo(annotatedElement, FieldContainer.class);
		default:
			return null;
		}			
	}
}
