package org.esfinge.virtuallab.services;

import java.lang.reflect.Field;
import java.util.List;

import org.esfinge.virtuallab.annotations.container.ClassContainer;
import org.esfinge.virtuallab.annotations.container.ContainerFactory;
import org.esfinge.virtuallab.annotations.container.FieldContainer;
import org.esfinge.virtuallab.annotations.container.TypeContainer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

@JsonNaming()
public class TransformationService {

	public Object wrapperPrimitive(Class<?> clazz, String value) throws Exception {
		if (boolean.class == clazz)
			return Boolean.parseBoolean(value);
		if (byte.class == clazz)
			return Byte.parseByte(value);
		if (short.class == clazz)
			return Short.parseShort(value);
		if (int.class == clazz)
			return Integer.parseInt(value);
		if (long.class == clazz)
			return Long.parseLong(value);
		if (float.class == clazz)
			return Float.parseFloat(value);
		if (double.class == clazz)
			return Double.parseDouble(value);
		throw new Exception();
	}

	public String transformToJson(Object object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(new AllLowerCasePropertyNamingStrategy());
		return mapper.writeValueAsString(object);
	}

	public String transformToHtml(List<?> objects) throws Exception {
		HtmlService htmlMaker = new HtmlService();
		return htmlMaker.makeHTML(objects);
	}

}

class AllLowerCasePropertyNamingStrategy extends PropertyNamingStrategy {

	private static final long serialVersionUID = 1L;

	@Override
	public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
		FieldContainer fieldContainer;
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
			ClassContainer classContainer = ContainerFactory.create(method.getAnnotated().getDeclaringClass(),
					TypeContainer.CLASS_CONTAINER);
			FieldContainer fieldContainer = classContainer.getDeclaredField(defaultName);
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