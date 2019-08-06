package org.esfinge.virtuallab.web.op;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.esfinge.virtuallab.descriptors.ClassDescriptor;
import org.esfinge.virtuallab.services.PersistenceService;
import org.esfinge.virtuallab.web.IJsonRequestHandler;
import org.esfinge.virtuallab.web.JsonReturn;
import org.esfinge.virtuallab.web.json.JsonArray;

/**
 * Trata as requisicoes de listar as classes com servicos validos.
 */
public class ListServiceClassesHandler implements IJsonRequestHandler
{
	public JsonReturn handleAsync(HttpServletRequest request)
	{
		// obtem a lista de classes validas
		List<ClassDescriptor> classesList = PersistenceService.getInstance().listServiceClasses();
		JsonReturn jsonReturn = new JsonReturn();

		try
		{
			jsonReturn.setData(new JsonArray<ClassDescriptor>(classesList));
			jsonReturn.setSuccess(true);
			jsonReturn.setMessage("");
		} 
		catch (Exception e)
		{
			// TODO: debug..
			e.printStackTrace();

			jsonReturn.setSuccess(false);
			jsonReturn.setMessage("Erro: " + e.toString());
		}

		return jsonReturn;
	}
}
