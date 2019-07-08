package devframework.webservice;

import java.io.File;
import java.lang.reflect.Method;

import com.fasterxml.jackson.databind.ObjectMapper;

import devframework.annotations.JsonReturn;
import devframework.utils.Utils;
import devframework.validation.ClassValidationService;

public class Invoker extends ClassLoader {

	public Object call(String className, String methodName, Object... params) {
		try {
			String pathClassName = className.replace(".", "\\");
			String filePath = Utils.getInstance().getProperty("upload.dir", System.getProperty("java.io.tmpdir"))
					+ File.separator;
			Class clazz = ClassValidationService.getInstance().isClassValid(filePath, pathClassName+".class");
			if (clazz != null) {
				Object instanceOfClass = clazz.newInstance();
				Method method = procuraMetodoComQuantidadeDeParametrosPassado(clazz, methodName, params);

				if (method != null) {
					return executaChamadaMetodoComRetornoEspecifico(method, instanceOfClass, params);
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private Method procuraMetodoComQuantidadeDeParametrosPassado(Class clazz, String methodName, Object[] params) {
		try {
			return clazz.getMethod(methodName);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object executaChamadaMetodoComRetornoEspecifico(Method method, Object instanceOfClass, Object... params) {
		try {
			if (method.getReturnType().isPrimitive()) {
				return method.invoke(instanceOfClass);
			} else if (ClassValidationService.getInstance().isAnnotationPresent(method, JsonReturn.class)) {
				ObjectMapper mapper = new ObjectMapper();
				return mapper.writeValueAsString(method.invoke(instanceOfClass));
			} else {
				return method.invoke(instanceOfClass).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
