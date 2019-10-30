package org.esfinge.virtuallab.spring;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.descriptors.ParameterDescriptor;
import org.esfinge.virtuallab.metadata.processors.MethodReturnProcessor;
import org.esfinge.virtuallab.metadata.processors.MethodReturnProcessorHelper;
import org.esfinge.virtuallab.services.InvokerService;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.web.JsonReturn;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

@Controller
public class MappingRestController {

	@RequestMapping("/")
	public String welcome() {
		return "Welcome to RestTemplate Example.";
	}

	@RequestMapping(value = "/services/{className}/{methodName}", method = RequestMethod.GET)
	public Object message(@PathVariable String className, @PathVariable String methodName,
			@RequestParam LinkedHashMap<String, Object> allRequestParams) throws Exception {
		MethodDescriptor methodDescriptor = new MethodDescriptor();
		methodDescriptor.setClassName(className);
		methodDescriptor.setName(methodName);
		methodDescriptor.setReturnType("String");
		int index = 0;
		List<ParameterDescriptor> parameters = new ArrayList<ParameterDescriptor>();
		List<Object> values = new ArrayList<Object>();
		for (Map.Entry<String, Object> requestParams : allRequestParams.entrySet()) {
			ParameterDescriptor parameter = new ParameterDescriptor();
			parameter.setDataType(requestParams.getKey());
			parameter.setIndex(index++);
			parameters.add(parameter);
			values.add(JsonUtils.convertTo(requestParams.getValue().toString(), ReflectionUtils.findClass(requestParams.getKey())));
		}
		methodDescriptor.setParameters(parameters);
	
		Object result = InvokerService.getInstance().call(methodDescriptor, values.toArray());
		MethodReturnProcessor<?> returnProcessor = MethodReturnProcessorHelper.getInstance()
				.findProcessor(methodDescriptor);
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setData(returnProcessor.process(result));
		jsonReturn.setType(returnProcessor.getType());
		return jsonReturn;
	}
}
