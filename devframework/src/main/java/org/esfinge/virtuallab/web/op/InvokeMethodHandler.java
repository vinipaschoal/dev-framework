package org.esfinge.virtuallab.web.op;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.metadata.processors.MethodReturnProcessor;
import org.esfinge.virtuallab.metadata.processors.MethodReturnProcessorHelper;
import org.esfinge.virtuallab.services.InvokerService;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.web.IJsonRequestHandler;
import org.esfinge.virtuallab.web.JsonReturn;

import com.fasterxml.jackson.databind.JsonNode;

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
			String jsonParamValues = JsonUtils.getProperty(jsonString, "paramValues");
			
			// converte os valores para a ordem e tipos corretos
			List<Object> values = methodDescriptor.getParameters()
					.stream()
					.sorted()
					.map((p) -> {
						try
						{
							// obtem o valor do parametro (como JSON)
							JsonNode jsonNode = JsonUtils.fromStringToJsonNode(jsonParamValues).get(p.getName());
							
							// converte o valor JSON para o tipo correto
							return JsonUtils.convertTo(jsonNode.toString(), ReflectionUtils.findClass(p.getDataType()));
						}
						catch ( Exception e )
						{
							e.printStackTrace();
							throw new RuntimeException(e);
						}
					})
					.collect(Collectors.toList());
			
			// invoca o metodo
			Object result = InvokerService.getInstance().call(methodDescriptor, values.toArray());
			
			// obtem o processor apropriado para o retorno do metodo
			MethodReturnProcessor<?> returnProcessor = MethodReturnProcessorHelper.getInstance().findProcessor(methodDescriptor);
			
			jsonReturn.setData(returnProcessor.process(result));
			jsonReturn.setType(returnProcessor.getType());
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