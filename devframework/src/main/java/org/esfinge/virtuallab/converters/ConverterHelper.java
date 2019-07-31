package org.esfinge.virtuallab.converters;

import java.lang.reflect.Field;
import java.util.List;

import org.esfinge.virtuallab.metadata.ClassMetadata;
import org.esfinge.virtuallab.metadata.FieldMetadata;
import org.esfinge.virtuallab.services.CustomAnnotationIntrospector;
import org.esfinge.virtuallab.services.HtmlService;
import org.esfinge.virtuallab.services.LabeledPropertyNamingStrategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterHelper
{	
	/*
	private MethodMetadata checkParameterCompatibility(List<MethodMetadata> methods,
			LinkedHashMap<String, Object> allParameters)
	{
		for (MethodMetadata methodContainer : methods)
		{
			List<String> parameterNames = methodContainer.getLabeledParameterNames();
			boolean errorOccurred = false;
			convertedValues.clear();
			for (int i = 0; i < methodContainer.getMethod().getParameterCount(); i++)
			{
				try
				{
					if (methodContainer.getMethod().getParameters()[i].getType().isPrimitive())
					{
						TransformationService transformationService = new TransformationService();
						convertedValues.add(transformationService.wrapperPrimitive(
								methodContainer.getMethod().getParameters()[i].getType(),
								String.valueOf(allParameters.get(parameterNames.get(i)))));
					}
					else
					{
						convertedValues.add(methodContainer.getMethod().getParameters()[i].getType()
								.cast(allParameters.get(parameterNames.get(i))));
					}
				}
				catch (Exception e)
				{
					errorOccurred = true;
					break;
				}
			}
			if (!errorOccurred)
			{
				return methodContainer;
			}
		}
		return null;
	}
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
		mapper.setPropertyNamingStrategy(new LabeledPropertyNamingStrategy());
		mapper.setAnnotationIntrospector(new CustomAnnotationIntrospector());
		return mapper.writeValueAsString(object);
	}

	public String transformToHtml(List<?> objects) throws Exception {
		HtmlService htmlMaker = new HtmlService();
		return htmlMaker.makeHTML(objects);
	}
	
	*/
	
	public class HtmlService {

		public String makeHTML(List<?> objects) throws Exception {
			String html = "<table border=\"1\">";
			if (objects.isEmpty()) {
				return html + "</table>";
			}
			html += addHeader(objects.get(0).getClass());
			html+=addData(objects);
			html += "</table>";
			return html;
		}

		private String addData(List<?> objects) throws Exception {
			String data = "";
			for(Object object: objects) {
				data+="<tr>";
				ClassMetadata container = ContainerFactory.create(object.getClass(), TypeContainer.CLASS_CONTAINER);
				for (FieldMetadata fieldContainer : container.getFields()) {
					data+="<td>";
					Field field =fieldContainer.getField(); 
					field.setAccessible(true);
					data+=field.get(object);
					data+="</td>";
				}
				data+="</tr>";
			}
			return data;
		}

		private String addHeader(Class<?> clazz) throws Exception {
			String header = "<tr><th>";
			ClassMetadata container = ContainerFactory.create(clazz, TypeContainer.CLASS_CONTAINER);
			header += container.getLabeledClassName();
			header += "</th></tr><tr>";
			for (FieldMetadata fieldContainer :container.getFields()) {
				header += "<th>";
				header += fieldContainer.getLabeledFieldName();
				header += "</th>";
			}
			header += "</tr>";
			return header;
		}
	}
	
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
			mapper.setPropertyNamingStrategy(new LabeledPropertyNamingStrategy());
			mapper.setAnnotationIntrospector(new CustomAnnotationIntrospector());
			return mapper.writeValueAsString(object);
		}

		public String transformToHtml(List<?> objects) throws Exception {
			HtmlService htmlMaker = new HtmlService();
			return htmlMaker.makeHTML(objects);
		}

	}
}
