package org.esfinge.virtuallab.web.op;

import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.services.PersistenceService;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.utils.Utils;
import org.esfinge.virtuallab.web.IJsonRequestHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Trata as requisicoes de listar os metodos de servicos das classes salvas.
 */
public class ListServiceMethodsHandler implements IJsonRequestHandler
{
	public JsonObject handleAsync(HttpServletRequest request) throws FileNotFoundException
	{
		Gson gson = new GsonBuilder().create();
		JsonObject jsonReturn = new JsonObject();

		try
		{
			// obtem a string do objeto JSON do request
			String jsonString = this.getJsonParameter(request);

			// obtem a lista de servicos validos da classe informada
			String clazzQualifiedName = JsonUtils.getProperty(jsonString, "clazz");
			List<MethodDescriptor> methodList = PersistenceService.getInstance().listServiceMethods(clazzQualifiedName);

			if (!Utils.isNullOrEmpty(methodList))
			{
				JsonArray jarray = gson.toJsonTree(methodList).getAsJsonArray();
				jsonReturn.add("methods", jarray);
				jsonReturn.addProperty("message", "");
				jsonReturn.addProperty("success", true);
			}
			else
			{
				jsonReturn.addProperty("message", "Classe pesquisada não encontrada!");
				jsonReturn.addProperty("success", false);
			}
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
