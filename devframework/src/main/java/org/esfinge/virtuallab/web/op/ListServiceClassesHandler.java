package org.esfinge.virtuallab.web.op;

import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.esfinge.virtuallab.descriptors.ClassDescriptor;
import org.esfinge.virtuallab.services.PersistenceService;
import org.esfinge.virtuallab.web.IJsonRequestHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Trata as requisicoes de listar as classes com servicos validos.
 */
public class ListServiceClassesHandler implements IJsonRequestHandler
{
	public JsonObject handleAsync(HttpServletRequest request) throws FileNotFoundException
	{
		// obtem a lista de classes validas
		List<ClassDescriptor> classesList = PersistenceService.getInstance().listServiceClasses();

		Gson gson = new GsonBuilder().create();
		JsonObject jsonObject = new JsonObject();

		try
		{
			JsonArray jarray = gson.toJsonTree(classesList).getAsJsonArray();
			jsonObject.add("clazzes", jarray);
			jsonObject.addProperty("message", "");
			jsonObject.addProperty("success", true);
		} 
		catch (Exception e)
		{
			// TODO: debug..
			e.printStackTrace();

			jsonObject.addProperty("message", "Erro: " + e.toString());
			jsonObject.addProperty("success", false);
		}

		return jsonObject;
	}
}
