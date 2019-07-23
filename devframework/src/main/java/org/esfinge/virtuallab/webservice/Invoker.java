package org.esfinge.virtuallab.webservice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.esfinge.virtuallab.annotations.container.MethodContainer;
import org.esfinge.virtuallab.domain.ClassDescriptor;
import org.esfinge.virtuallab.services.InformationClassService;
import org.esfinge.virtuallab.services.PersistenceService;
import org.esfinge.virtuallab.services.TransformationService;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Invoker extends ClassLoader {

	private Object[] valoresConvertidos;
	private TransformationService transformationService = new TransformationService();

	public Object call(String classQualifiedName, String methodName, LinkedHashMap<String, Object> allParameters)
			throws Exception {

		ClassDescriptor classDesc = PersistenceService.getInstance().getClass(classQualifiedName);

		if (classDesc != null) {
			Class<?> clazz = classDesc.getClassClass();
			Object instanceOfClass = clazz.newInstance();
			Method method = procuraMetodoCorrespondente(clazz, methodName, allParameters.keySet());
			if (method != null) {
				return executaChamadaMetodoComRetornoEspecifico(method, instanceOfClass, valoresConvertidos);
			}
			throw new Exception("erro ao executar metodo");
		}
		throw new Exception("erro ao executar metodo");
	}

	private Method procuraMetodoCorrespondente(Class clazz, String methodName, Set<String> nomesParametros)
			throws Exception {
		List<Method> metodos = new ArrayList<Method>();
		for (MethodContainer methodContainer : InformationClassService.getInstance().getServiceMethods(clazz)) {
			if (methodContainer.getNomeMethod().equals(methodName)
					&& methodContainer.getMethod().getParameterCount() == nomesParametros.size() &&
					nomesParametrosCorrespondentes(methodContainer.getNomeParametros(),nomesParametros)) {
				metodos.add(methodContainer.getMethod());
			}
		}
		if (metodos.isEmpty()) {
			return null;
		} else {
			return metodos.get(0);
		}

	}

	private boolean nomesParametrosCorrespondentes(List<String> parametrosDoMetodo, Set<String> parametrosDaUrl) {
		for(String parametroDaUrl:parametrosDaUrl ) {
			if(!parametrosDoMetodo.contains(parametroDaUrl)) {
				return false;
			}
		}
		return true;
	}

	private Object executaChamadaMetodoComRetornoEspecifico(Method method, Object instanceOfClass, Object... params)
			throws JsonProcessingException, Exception {
		if (method.getReturnType().isPrimitive()) {
			return method.invoke(instanceOfClass, params);
		} else if (InformationClassService.getInstance().isJsonReturnPresent(method)) {
			return transformationService.transformToJson(method.invoke(instanceOfClass, params));
		} else if (InformationClassService.getInstance().isHtmlTableReturnPresent(method)) {
			return transformationService.transformToHtml((List) method.invoke(instanceOfClass, params));
		} else {
			return method.invoke(instanceOfClass, params).toString();
		}
	}

}