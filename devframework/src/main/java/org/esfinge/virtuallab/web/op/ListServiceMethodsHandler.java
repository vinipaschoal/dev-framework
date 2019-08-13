package org.esfinge.virtuallab.web.op;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.services.PersistenceService;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.utils.Utils;
import org.esfinge.virtuallab.web.IJsonRequestHandler;
import org.esfinge.virtuallab.web.JsonReturn;
import org.esfinge.virtuallab.web.json.JsonArray;

/**
 * Trata as requisicoes de listar os metodos de servicos das classes salvas.
 */
public class ListServiceMethodsHandler implements IJsonRequestHandler
{
	public JsonReturn handleAsync(HttpServletRequest request)
	{
		JsonReturn jsonReturn = new JsonReturn();

		try
		{
			// obtem a string do objeto JSON do request
			String jsonString = this.getJsonParameter(request);

			// obtem a lista de servicos validos da classe informada
			String clazzQualifiedName = JsonUtils.getProperty(jsonString, "clazz");
			List<MethodDescriptor> methodList = PersistenceService.getInstance().listServiceMethods(clazzQualifiedName);

			if (!Utils.isNullOrEmpty(methodList))
			{
				jsonReturn.setData(new JsonArray<MethodDescriptor>(methodList));
				jsonReturn.setSuccess(true);
				jsonReturn.setMessage("");
			}
			else
			{
				jsonReturn.setSuccess(false);
				jsonReturn.setMessage("Nenhum serviço encontrado para a classe: " + clazzQualifiedName);
			}
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
