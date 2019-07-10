package devframework.webservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devframework.utils.Utils;

@RestController

@Controller
public class MapeamentoRestController {

	private Invoker invoker = new Invoker();

	@RequestMapping("/services/{className}/{methodName}")
	public Object message(@PathVariable String className, @PathVariable String methodName, @RequestParam Object ...params) throws Exception {
		String filePath = Utils.getInstance().getProperty("upload.dir", System.getProperty("java.io.tmpdir"));
		return invoker.call(filePath, className, methodName, params);
	}
}
