package org.esfinge.virtuallab.web.op;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.esfinge.virtuallab.converters.ConverterHelper;
import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.services.InvokerService;
import org.esfinge.virtuallab.utils.JsonObject;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.web.IJsonRequestHandler;

/**
 * Trata as requisicoes para invocar um metodo de servico.
 */
public class InvokeMethodHandler implements IJsonRequestHandler
{
	public JsonObject handleAsync(HttpServletRequest request) throws FileNotFoundException
	{
		JsonObject jsonReturn = new JsonObject();

		try
		{
			// obtem a string do objeto JSON do request
			String jsonString = this.getJsonParameter(request);
			
			// obtem o descritor do metodo
			MethodDescriptor methodDescriptor = JsonUtils.getPropertyAs(jsonString, "methodDescriptor", MethodDescriptor.class);
			
			// obtem os valores dos parametros
			Map<String,String> paramValues = JsonUtils.convertToMap(JsonUtils.getProperty(jsonString, "paramValues"));
			
			// converte os valores para a ordem e tipos corretos
			List<Object> values = methodDescriptor.getParameters()
					.stream()
					.sorted()
					.map((p) -> ConverterHelper.getInstance().convertFromString(p.getDataType(), paramValues.get(p.getName())))
					.collect(Collectors.toList());
			
			// invoca o metodo
			Object result = InvokerService.getInstance().call(methodDescriptor, values.toArray());
		
			jsonReturn.addProperty("result", JsonUtils.stringify(result));
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