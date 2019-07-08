package devframework.webservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@Controller
public class MapeamentoRestController {

	private Invoker invoker = new Invoker();

	@RequestMapping("/services/{className}/{methodName}")
	public Object message(@PathVariable String className, @PathVariable String methodName) {
		return invoker.call(className, methodName);
	}
}
