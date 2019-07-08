package devframework.webservice;

import java.io.File;
import java.lang.reflect.Method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import devframework.annotations.JsonReturn;
import devframework.validation.ClassValidationService;

public class Invoker extends ClassLoader {

	public Object call(String filePath, String className, String methodName, Object... params) throws Exception {
		String pathClassName = className.replace(".", File.separator);
		Class clazz = ClassValidationService.getInstance().isClassValid(filePath, pathClassName + ".class", methodName);
		if (clazz != null) {
			Object instanceOfClass = clazz.newInstance();
			Method method = procuraMetodoComQuantidadeDeParametrosPassado(clazz, methodName, params);

			if (method != null) {
				return executaChamadaMetodoComRetornoEspecifico(method, instanceOfClass, params);
			}
		}
		return null;
	}

	private Method procuraMetodoComQuantidadeDeParametrosPassado(Class clazz, String methodName, Object[] params)
			throws NoSuchMethodException, SecurityException {
		return clazz.getMethod(methodName);
	}

	private Object executaChamadaMetodoComRetornoEspecifico(Method method, Object instanceOfClass, Object... params) throws JsonProcessingException, Exception {
		if (method.getReturnType().isPrimitive()) {
			return method.invoke(instanceOfClass);
		} else if (ClassValidationService.getInstance().isAnnotationPresent(method, JsonReturn.class)) {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(method.invoke(instanceOfClass));
		} else {
			return method.invoke(instanceOfClass).toString();
		}
	}

}
