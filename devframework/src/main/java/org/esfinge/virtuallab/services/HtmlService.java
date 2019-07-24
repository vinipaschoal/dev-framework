package org.esfinge.virtuallab.services;

import java.lang.reflect.Field;
import java.util.List;

import org.esfinge.virtuallab.annotations.container.ClassContainer;
import org.esfinge.virtuallab.annotations.container.ContainerFactory;
import org.esfinge.virtuallab.annotations.container.FieldContainer;
import org.esfinge.virtuallab.annotations.container.TypeContainer;

public class HtmlService {

	public String makeHTML(List<?> objects) throws Exception {
		String html = "<table border=\"1\">";
		if (objects.isEmpty()) {
			return html + "</table>";
		}
		html += adicionaCabecalho(objects.get(0).getClass());
		html+=adicionaDados(objects);
		html += "</table>";
		return html;
	}

	private String adicionaDados(List<?> objects) throws Exception {
		String dados = "";
		for(Object object: objects) {
			dados+="<tr>";
			ClassContainer container = ContainerFactory.create(object.getClass(), TypeContainer.CLASS_CONTAINER);
			for (FieldContainer fieldContainer : container.getFields()) {
				dados+="<td>";
				Field field =fieldContainer.getField(); 
				field.setAccessible(true);
				dados+=field.get(object);
				dados+="</td>";
			}
			dados+="</tr>";
		}
		return dados;
	}

	private String adicionaCabecalho(Class<?> clazz) throws Exception {
		String cabecalho = "<tr><th>";
		ClassContainer container = ContainerFactory.create(clazz, TypeContainer.CLASS_CONTAINER);
		cabecalho += container.getLabeledClassName();
		cabecalho += "</th></tr><tr>";
		for (FieldContainer fieldContainer :container.getFields()) {
			cabecalho += "<th>";
			cabecalho += fieldContainer.getLabeledFieldName();
			cabecalho += "</th>";
		}
		cabecalho += "</tr>";
		return cabecalho;
	}
}