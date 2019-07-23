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
public class FieldContainer {

	@ElementName
	private String nameField;

	@ReflectionReference
	private Field field;

	@AnnotationProperty(annotation = Label.class, property = "name")
	private String labelField;

	@ContainsAnnotation(Label.class)
	private boolean temAnotacaoLabel;

	public String getNameField() {
		return nameField;
	}

	public void setNameField(String nameField) {
		this.nameField = nameField;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getLabelField() {
		return labelField;
	}

	public void setLabelField(String labelField) {
		this.labelField = labelField;
	}

	public boolean isTemAnotacaoLabel() {
		return temAnotacaoLabel;
	}

	public void setTemAnotacaoLabel(boolean temAnotacaoLabel) {
		this.temAnotacaoLabel = temAnotacaoLabel;
	}
}
