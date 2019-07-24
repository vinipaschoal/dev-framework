package org.esfinge.virtuallab.op.listclasses;

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

/**
 * Trata as requisicoes de listar as classes salvas.
 */
public class ListClassesHandler implements IJsonRequestHandler {
	public JsonObject handleAsync(HttpServletRequest request) throws FileNotFoundException {
		//carrega todas as classes do diretorio
		
		// obtem a lista de classes validas
		List<ClassDescriptor> classesList = PersistenceService.getInstance().list();

		Gson gson = new GsonBuilder().create();
		JsonObject jsonObject = new JsonObject();
		
		try
		{
			JsonArray jarray = gson.toJsonTree(classesList).getAsJsonArray();
			jsonObject.add("clazzes", jarray);
			jsonObject.addProperty("message", "");
			jsonObject.addProperty("success", true);
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
