package devframework.services;

import java.lang.reflect.Field;
import java.util.List;

import devframework.annotations.container.FieldContainer;

public class HtmlMaker {

	public String makeHTML(List objects) throws Exception {
		String html = "<table border=\"1\">";
		if (objects.isEmpty()) {
			return html + "</table>";
		}
		html += adicionaCabecalho(objects.get(0).getClass());
		html+=adicionaDados(objects);
		html += "</table>";
		return html;
	}

	private String adicionaDados(List objects) throws Exception {
		String dados = "";
		for(Object object: objects) {
			dados+="<tr>";
			for (FieldContainer fieldContainer : ClassValidationService.getInstance().getFields(object.getClass())) {
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

	private String adicionaCabecalho(Class clazz) throws Exception {
		String cabecalho = "<tr><th>";
		cabecalho += ClassValidationService.getInstance().getClassName(clazz);
		cabecalho += "</th></tr><tr>";
		for (FieldContainer field : ClassValidationService.getInstance().getFields(clazz)) {
			cabecalho += "<th>";
			if (field.isTemAnotacaoLabel()) {
				cabecalho += field.getLabelField();
			} else {
				cabecalho += field.getNameField();
			}
			cabecalho += "</th>";
		}
		cabecalho += "</tr>";
		return cabecalho;
	}
}