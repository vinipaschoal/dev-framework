package org.esfinge.virtuallab.webservice;

import java.util.LinkedHashMap;

import org.esfinge.virtuallab.services.InvokerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

@Controller
public class MappingRestController {

	private InvokerService invoker = new InvokerService();

	@RequestMapping(value= "/services/{className}/{methodName}", method = RequestMethod.GET)
	public Object message(@PathVariable String className, @PathVariable String methodName,
			@RequestParam LinkedHashMap<String,Object> allRequestParams) throws Exception {
		return invoker.call(className, methodName, allRequestParams);
	}
}