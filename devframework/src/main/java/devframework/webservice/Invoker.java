package devframework.webservice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import devframework.annotations.JsonReturn;
import devframework.annotations.container.MethodContainer;
import devframework.domain.ClassDescriptor;
import devframework.services.ClassValidationService;
import devframework.services.PersistenceService;
import devframework.services.TransformationService;

public class Invoker extends ClassLoader {

	private Object[] valoresConvertidos;
	private TransformationService transformationService = new TransformationService();

	public Object call(String classQualifiedName, String methodName, Object... params) throws Exception {

		ClassDescriptor classDesc = PersistenceService.getInstance().getClass(classQualifiedName);

		if (classDesc != null) {
			Class<?> clazz = classDesc.getClassClass();
			Object instanceOfClass = clazz.newInstance();
			Method method = procuraMetodoCorrespondente(clazz, methodName, params);
			if (method != null) {
				return executaChamadaMetodoComRetornoEspecifico(method, instanceOfClass, valoresConvertidos);
			}
			throw new Exception("erro ao executar metodo");
		}
		throw new Exception("erro ao executar metodo");
	}

	private Method procuraMetodoCorrespondente(Class clazz, String methodName, Object[] params) throws Exception {
		List<Method> metodos = new ArrayList<Method>();
		for (MethodContainer methodContainer : ClassValidationService.getInstance().getServiceMethods(clazz)) {
			String alias = methodContainer.getAliasMethod();
			if ((!"".equals(alias) && alias.equals(methodName))
					|| ("".equals(alias) && methodContainer.getNomeMethod().equals(methodName))) {
				metodos.add(methodContainer.getMethod());
			}
		}
		if (metodos.isEmpty()) {
			return null;
		} else {
			return testaTiposDeParametros(metodos, params);
		}

	}

	private Method testaTiposDeParametros(List<Method> metodos, Object[] params) throws Exception {
		for (Method method : metodos) {
			if (method.getParameterTypes().length != params.length) {
				continue;
			} else {
				valoresConvertidos = new Object[params.length];
				boolean invalid = false;
				for (int i = 0; i < method.getParameterTypes().length; i++) {
					try {
						if (method.getParameterTypes()[i].isPrimitive()) {
							valoresConvertidos[i] = transformationService.wrapperPrimitive(method.getParameterTypes()[i], (String) params[i]);
						} else {
							valoresConvertidos[i] = method.getParameterTypes()[i].cast(params[i]);
						}
					} catch (Exception e) {
						invalid = true;
						break;
					}
				}
				if (!invalid) {
					return method;
				}
			}
		}
		throw new Exception("nao existe metodo correspodendente para os parametros informados");
	}

	private Object executaChamadaMetodoComRetornoEspecifico(Method method, Object instanceOfClass, Object... params)
			throws JsonProcessingException, Exception {
		if (method.getReturnType().isPrimitive()) {
			return method.invoke(instanceOfClass, params);
		} else if (ClassValidationService.getInstance().isJsonReturnPresent(method)) {
			return transformationService.transformToJson(method.invoke(instanceOfClass, params));
		}else if(ClassValidationService.getInstance().isHtmlTableReturnPresent(method)) {
			return transformationService.transformToHtml((List<Object>) method.invoke(instanceOfClass, params));
		}
		else {
			return method.invoke(instanceOfClass, params).toString();
		}
	}

	
}