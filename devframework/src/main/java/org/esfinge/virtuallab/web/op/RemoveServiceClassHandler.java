package org.esfinge.virtuallab.web.op;

import javax.servlet.http.HttpServletRequest;

import org.esfinge.virtuallab.services.PersistenceService;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.web.IJsonRequestHandler;
import org.esfinge.virtuallab.web.JsonReturn;

/**
 * Trata as requisicoes de listar as classes com servicos validos.
 */
public class RemoveServiceClassHandler implements IJsonRequestHandler
{
	public JsonReturn handleAsync(HttpServletRequest request)
	{
		JsonReturn jsonReturn = new JsonReturn();

		try
		{
			// obtem a string do objeto JSON do request
			String jsonString = this.getJsonParameter(request);

			// obtem o nome da classe de servico a ser removida
			String clazzQualifiedName = JsonUtils.getProperty(jsonString, "clazz");
			
			// remove o servico
			boolean result = PersistenceService.getInstance().removeServiceClass(clazzQualifiedName);
			
			//
			if ( result )
			{
				jsonReturn.setSuccess(true);
				jsonReturn.setMessage("Módulo removido com sucesso!");
			}
			else
			{
				jsonReturn.setSuccess(false);
				jsonReturn.setMessage("Erro ao remover módulo: " + clazzQualifiedName);
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
