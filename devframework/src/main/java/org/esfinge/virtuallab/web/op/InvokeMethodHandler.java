package org.esfinge.virtuallab.web.op;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.esfinge.virtuallab.converters.ConverterHelper;
import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.services.InvokerService;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.web.IJsonRequestHandler;
import org.esfinge.virtuallab.web.JsonReturn;

/**
 * Trata as requisicoes para invocar um metodo de servico.
 */
public class InvokeMethodHandler implements IJsonRequestHandler
{
	public JsonReturn handleAsync(HttpServletRequest request) 
	{
		JsonReturn jsonReturn = new JsonReturn();

		try
		{
			// obtem a string do objeto JSON do request
			String jsonString = this.getJsonParameter(request);
			
			// obtem o descritor do metodo
			MethodDescriptor methodDescriptor = JsonUtils.getPropertyAs(jsonString, "methodDescriptor", MethodDescriptor.class);
			
			// obtem os valores dos parametros
			Map<String,String> paramValues = JsonUtils.getPropertyAsMap(jsonString, "paramValues");
			
			// converte os valores para a ordem e tipos corretos
			List<Object> values = methodDescriptor.getParameters()
					.stream()
					.sorted()
					.map((p) -> ConverterHelper.getInstance().convertFromString(p.getDataType(), paramValues.get(p.getName())))
					.collect(Collectors.toList());
			
			// invoca o metodo
			Object result = InvokerService.getInstance().call(methodDescriptor, values.toArray());
			
			jsonReturn.setData(JsonUtils.fromObjectToJsonData(result));
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