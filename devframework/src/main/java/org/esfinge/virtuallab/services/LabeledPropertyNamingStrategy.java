package org.esfinge.virtuallab.services;

import org.esfinge.virtuallab.metadata.ClassMetadata;
import org.esfinge.virtuallab.metadata.ContainerFactory;
import org.esfinge.virtuallab.metadata.FieldMetadata;
import org.esfinge.virtuallab.metadata.TypeContainer;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

public class LabeledPropertyNamingStrategy extends PropertyNamingStrategy {

	private static final long serialVersionUID = 1L;

	@Override
	public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
		FieldMetadata fieldContainer;
		try {
			fieldContainer = ContainerFactory.create(field.getAnnotated(), TypeContainer.FIELD_CONTAINER);
			return fieldContainer.getLabeledFieldName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultName;
	}

	@Override
	public String nameForGetterMethod(MapperConfig config, AnnotatedMethod method, String defaultName) {
		try {
			ClassMetadata classContainer = ContainerFactory.create(method.getAnnotated().getDeclaringClass(),
					TypeContainer.CLASS_CONTAINER);
			FieldMetadata fieldContainer = classContainer.getDeclaredField(defaultName);
			if (fieldContainer != null) {
				return fieldContainer.getLabeledFieldName();
			} else {
				return defaultName;
			}
		} catch (Exception e) {
		}
		return defaultName;
	}

}