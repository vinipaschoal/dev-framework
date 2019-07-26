package org.esfinge.virtuallab.annotations.container;

import java.lang.reflect.Field;

import org.esfinge.virtuallab.annotations.Label;

import net.sf.esfinge.metadata.annotation.container.AnnotationProperty;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ContainsAnnotation;
import net.sf.esfinge.metadata.annotation.container.ElementName;
import net.sf.esfinge.metadata.annotation.container.ReflectionReference;
import net.sf.esfinge.metadata.container.ContainerTarget;

@ContainerFor(ContainerTarget.FIELDS)
public class FieldContainer implements IContainer {

	@ElementName
	private String fieldName;

	@ReflectionReference
	private Field field;

	@AnnotationProperty(annotation = Label.class, property = "name")
	private String labelField;

	@ContainsAnnotation(Label.class)
	private boolean annotatedWithLabel;

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public void setLabelField(String labelField) {
		this.labelField = labelField;
	}

	public void setAnnotatedWithLabel(boolean annotatedWithLabel) {
		this.annotatedWithLabel = annotatedWithLabel;
	}

	public String getLabeledFieldName() {
		if (annotatedWithLabel) {
			return labelField;
		} else {
			return fieldName;
		}
	}
	
	public String getDeclaredName() {
		return fieldName;
	}

}
