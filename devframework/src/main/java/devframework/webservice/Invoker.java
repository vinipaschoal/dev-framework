package devframework.webservice;

import java.lang.reflect.Method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import devframework.annotations.JsonReturn;
import devframework.domain.ClassDescriptor;
import devframework.services.PersistenceService;

public class Invoker extends ClassLoader {

	public Object call(String classQualifiedName, String methodName, Object... params) throws Exception 
	{
		// recupera a classe
		ClassDescriptor classDesc = PersistenceService.getInstance().getClass(classQualifiedName);
		
		if ( classDesc != null ) 
		{
			Class<?> clazz = classDesc.getClassClass();
			Object instanceOfClass = clazz.newInstance();
			Method method = procuraMetodoComQuantidadeDeParametrosPassado(clazz, methodName, params);

			if (method != null) {
				return executaChamadaMetodoComRetornoEspecifico(method, instanceOfClass, params);
			}
		}
		
		return null;
	}

	private Method procuraMetodoComQuantidadeDeParametrosPassado(Class<?> clazz, String methodName, Object[] params)
			throws NoSuchMethodException, SecurityException {
		return clazz.getMethod(methodName);
	}

	private Object executaChamadaMetodoComRetornoEspecifico(Method method, Object instanceOfClass, Object... params) throws JsonProcessingException, Exception {
		if (method.getReturnType().isPrimitive()) {
			return method.invoke(instanceOfClass);
		} else if (method.isAnnotationPresent(JsonReturn.class)) {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(method.invoke(instanceOfClass));
		} else {
			return method.invoke(instanceOfClass).toString();
		}
	}

}
