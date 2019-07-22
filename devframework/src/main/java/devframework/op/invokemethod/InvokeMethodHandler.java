package devframework.op.invokemethod;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import devframework.domain.ClassDescriptor;
import devframework.domain.FormDescriptor;
import devframework.domain.MethodDescriptor;
import devframework.domain.ParameterDescriptor;
import devframework.services.PersistenceService;
import devframework.servlet.IJsonRequestHandler;

/**
 * Trata as requisicoes para retornar Json do form para envocar metodo.
 */
public class InvokeMethodHandler implements IJsonRequestHandler {
	public JsonObject handleAsync(HttpServletRequest request) throws FileNotFoundException {
		
		Gson gson = new GsonBuilder().create();
		JsonObject jsonObject = new JsonObject();
		
		try {
		
			ClassDescriptor clazzRequest = setClassDescriptor(request);
			
			Method method = PersistenceService.getInstance().getMethod(clazzRequest.getQualifiedName(), clazzRequest.getMethods().get(0));
			
			if (method == null) {
				jsonObject.addProperty("message", "Método não encontrado.");
				jsonObject.addProperty("success", false);
				return jsonObject;
			}
			
			FormDescriptor formDescriptor = new FormDescriptor(method);
			
			if (formDescriptor != null) {
				JsonParser parser = new JsonParser();
				JsonObject jObjectClass = parser.parse(gson.toJson(formDescriptor)).getAsJsonObject();
				jsonObject.add("parameters", jObjectClass);
			}
			
			jsonObject.addProperty("message", formDescriptor != null ? "" : "Método não encontrado.");
			jsonObject.addProperty("success", formDescriptor != null ? true : false);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			jsonObject.addProperty("message", "Erro: " + e.toString());
			jsonObject.addProperty("success", false);
		}

		return jsonObject;
	}
	
	private ClassDescriptor setClassDescriptor(HttpServletRequest request) {
		
		ClassDescriptor clazz = new ClassDescriptor();
		MethodDescriptor method = new MethodDescriptor();
		
		clazz.setQualifiedName(request.getParameter("clazz"));
		method.setName(request.getParameter("methodName"));
		method.setReturnType(request.getParameter("methodReturnType"));
		
		for (int i=0; i<=100; i++) {
			
			if (request.getParameter("parameters[" + i + "][name]") == null)
				break;
			
			ParameterDescriptor param = new ParameterDescriptor();
			param.setName(request.getParameter("parameters[" + i + "][name]"));
			param.setDataType(request.getParameter("parameters[" + i + "][dataType]"));
			method.addParameter(param);
		}
		
		clazz.addMethods(method);
		
		return clazz;
	}
	
}