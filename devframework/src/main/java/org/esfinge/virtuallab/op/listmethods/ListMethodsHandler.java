package org.esfinge.virtuallab.op.listmethods;

import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.esfinge.virtuallab.domain.ClassDescriptor;
import org.esfinge.virtuallab.services.PersistenceService;
import org.esfinge.virtuallab.servlet.IJsonRequestHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Trata as requisicoes de listar os metodos das classes salvas.
 */
public class ListMethodsHandler implements IJsonRequestHandler {
	public JsonObject handleAsync(HttpServletRequest request) throws FileNotFoundException {
		
		String clazzQualifiedName = request.getParameter("clazz");

		Gson gson = new GsonBuilder().create();
		JsonObject jsonObject = new JsonObject();
		
		try
		{
			// obtem a classe requisitada
			ClassDescriptor classDescriptor = PersistenceService.getInstance().getClass(clazzQualifiedName);
			
			if (classDescriptor != null) {
				JsonParser parser = new JsonParser();
				JsonObject jObjectClass = parser.parse(gson.toJson(classDescriptor)).getAsJsonObject();
				jsonObject.add("clazz", jObjectClass);
			}
			
			jsonObject.addProperty("message", classDescriptor != null ? "" : "Classe pesquisada n�o existe.");
			jsonObject.addProperty("success", classDescriptor != null ? true : false);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			jsonObject.addProperty("message", "Erro: " + e.toString());
			jsonObject.addProperty("success", false);
		}

		return jsonObject;
	}
}
