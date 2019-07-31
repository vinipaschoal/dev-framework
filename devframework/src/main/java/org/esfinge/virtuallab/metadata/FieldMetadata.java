package org.esfinge.virtuallab.metadata;

import java.lang.reflect.Field;

import org.esfinge.virtuallab.annotations.Label;

import net.sf.esfinge.metadata.annotation.container.AnnotationProperty;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ContainsAnnotation;
import net.sf.esfinge.metadata.annotation.container.ElementName;
import net.sf.esfinge.metadata.annotation.container.ReflectionReference;
import net.sf.esfinge.metadata.container.ContainerTarget;

/**
 * Extrai informacoes de metadados dos atributos.
 */
@ContainerFor(ContainerTarget.FIELDS)
public class FieldMetadata
{
	// indica se o atributo contem a anotacao @Label
	@ContainsAnnotation(Label.class)
	private boolean annotatedWithLabel;

	// informacoes da anotacao @Label (se utilizada)
	@AnnotationProperty(annotation = Label.class, property = "value")
	private String labelField;

	// atributo
	@ReflectionReference
	private Field field;

	// nome do atributo
	@ElementName
	private String fieldName;


	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public Field getField()
	{
		return field;
	}

	public void setField(Field field)
	{
		this.field = field;
	}

	public void setLabelField(String labelField)
	{
		this.labelField = labelField;
	}

	public void setAnnotatedWithLabel(boolean annotatedWithLabel)
	{
		this.annotatedWithLabel = annotatedWithLabel;
	}

	public String getLabeledFieldName()
	{
		if (annotatedWithLabel)
		{
			return labelField;
		}
		else
		{
			return fieldName;
		}
	}

	public String getDeclaredName()
	{
		return fieldName;
	}

}
