package org.esfinge.virtuallab.services;

import java.lang.reflect.Field;
import java.util.List;

import org.esfinge.virtuallab.metadata.ClassMetadata;
import org.esfinge.virtuallab.metadata.ContainerFactory;
import org.esfinge.virtuallab.metadata.FieldMetadata;
import org.esfinge.virtuallab.metadata.TypeContainer;

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