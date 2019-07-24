package org.esfinge.virtuallab.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.esfinge.virtuallab.annotations.container.ClassContainer;
import org.esfinge.virtuallab.annotations.container.ContainerFactory;
import org.esfinge.virtuallab.annotations.container.MethodContainer;
import org.esfinge.virtuallab.annotations.container.TypeContainer;
import org.esfinge.virtuallab.domain.ClassDescriptor;

import com.fasterxml.jackson.core.JsonProcessingException;

public class InvokerService extends ClassLoader {

	private TransformationService transformationService = new TransformationService();
	private List<Object> convertedValues = new ArrayList<Object>();

	public Object call(String classQualifiedName, String methodName, LinkedHashMap<String, Object> allParameters)
			throws Exception {
		ClassDescriptor classDesc = PersistenceService.getInstance().getClass(classQualifiedName);
		if (classDesc != null) {
			Class<?> clazz = classDesc.getClassClass();
			Object instanceOfClass = clazz.newInstance();
			List<MethodContainer> methods = searchMatchingMethod(clazz, methodName, allParameters.keySet());
			if (methods != null && !methods.isEmpty()) {
				MethodContainer selectedMethod = checkParameterCompatibility(methods, allParameters);
				return callMethodWithSpecificReturn(selectedMethod, instanceOfClass);
			}
			throw new Exception("erro ao executar metodo");
		}
		throw new Exception("erro ao executar metodo");
	}

	private MethodContainer checkParameterCompatibility(List<MethodContainer> methods,
			LinkedHashMap<String, Object> allParameters) {
		for (MethodContainer methodContainer : methods) {
			List<String> parameterNames = methodContainer.getLabeledParameterNames();
			boolean errorOccurred = false;
			convertedValues.clear();
			for (int i = 0; i < methodContainer.getMethod().getParameterCount(); i++) {
				try {
					if (methodContainer.getMethod().getParameters()[i].getType().isPrimitive()) {
						TransformationService transformationService = new TransformationService();
						convertedValues.add(transformationService.wrapperPrimitive(
								methodContainer.getMethod().getParameters()[i].getType(),
								String.valueOf(allParameters.get(parameterNames.get(i)))));
					} else {
						convertedValues.add(methodContainer.getMethod().getParameters()[i].getType()
								.cast(allParameters.get(parameterNames.get(i))));
					}
				} catch (Exception e) {
					errorOccurred = true;
					break;
				}
			}
			if (!errorOccurred) {
				return methodContainer;
			}
		}
		return null;
	}

	private List<MethodContainer> searchMatchingMethod(Class<?> clazz, String methodName, Set<String> parameterNames)
			throws Exception {
		List<MethodContainer> methodsContainer = new ArrayList<MethodContainer>();
		ClassContainer classContainer = ContainerFactory.create(clazz, TypeContainer.CLASS_CONTAINER);
		for (MethodContainer methodContainer : classContainer.getMethodsWithServiceMethod()) {
			if (methodContainer.getMethodName().equals(methodName)
					&& methodContainer.getMethod().getParameterCount() == parameterNames.size()
					&& matchingParameterNames(methodContainer.getLabeledParameterNames(), parameterNames)) {
				methodsContainer.add(methodContainer);
			}
		}
		if (methodsContainer.isEmpty()) {
			return null;
		} else {
			return methodsContainer;
		}

	}

	private boolean matchingParameterNames(List<String> methodParameters, Set<String> urlParameters) {
		if(methodParameters.size()!=urlParameters.size()) {
			return false;
		}
		for (String parametroDaUrl : urlParameters) {
			if (!methodParameters.contains(parametroDaUrl)) {
				return false;
			}
		}
		return true;
	}

	private Object callMethodWithSpecificReturn(MethodContainer methodContainer, Object instanceOfClass)
			throws JsonProcessingException, Exception {
		Object returnValue;
		if (methodContainer.getNumberOfParameters() > 0) {
			returnValue = methodContainer.getMethod().invoke(instanceOfClass, convertedValues.toArray());
		} else {
			returnValue = methodContainer.getMethod().invoke(instanceOfClass);
		}
		if (methodContainer.isAnnotatedWithJsonReturn()) {
			return transformationService.transformToJson(returnValue);
		} else if (methodContainer.isAnnotatedWithHtmlTableReturn()) {
			return transformationService.transformToHtml((List<?>) returnValue);
		} else {
			return returnValue;
		}
	}

}