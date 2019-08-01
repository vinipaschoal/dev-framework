package org.esfinge.virtuallab.web.op;

import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.esfinge.virtuallab.descriptors.ClassDescriptor;
import org.esfinge.virtuallab.services.PersistenceService;
import org.esfinge.virtuallab.utils.JsonArray;
import org.esfinge.virtuallab.utils.JsonObject;
import org.esfinge.virtuallab.web.IJsonRequestHandler;

/**
 * Trata as requisicoes de listar as classes com servicos validos.
 */
public class ListServiceClassesHandler implements IJsonRequestHandler
{
	public JsonObject handleAsync(HttpServletRequest request) throws FileNotFoundException
	{
		// obtem a lista de classes validas
		List<ClassDescriptor> classesList = PersistenceService.getInstance().listServiceClasses();
		JsonObject jsonReturn = new JsonObject();

		try
		{
			JsonArray<ClassDescriptor> jarray = new JsonArray<>(classesList);
			jsonReturn.addProperty("clazzes", jarray);
			jsonReturn.addProperty("message", "");
			jsonReturn.addProperty("success", true);
		} 
		catch (Exception e)
		{
			// TODO: debug..
			e.printStackTrace();

			jsonReturn.addProperty("message", "Erro: " + e.toString());
			jsonReturn.addProperty("success", false);
		}

		return jsonReturn;
	}
}
