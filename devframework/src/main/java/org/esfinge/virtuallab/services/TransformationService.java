package org.esfinge.virtuallab.services;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		return mapper.writeValueAsString(object);
	}

	public String transformToHtml(List<?> objects) throws Exception{
		HtmlService htmlMaker = new HtmlService();
		return htmlMaker.makeHTML(objects);
	}
	
}
