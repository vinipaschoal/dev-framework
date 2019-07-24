package org.esfinge.virtuallab.webservice;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.esfinge.virtuallab.annotations.container.ClassContainer;
import org.esfinge.virtuallab.annotations.container.MethodContainer;
import org.esfinge.virtuallab.domain.ClassDescriptor;
import org.esfinge.virtuallab.services.PersistenceService;
import org.esfinge.virtuallab.services.TransformationService;

import com.fasterxml.jackson.core.JsonProcessingException;

import net.sf.esfinge.metadata.AnnotationReader;

public class Invoker extends ClassLoader {

	private AnnotationReader reader = new AnnotationReader();
	private TransformationService transformationService = new TransformationService();
	private List<Object> valoresConvertidos = new ArrayList<Object>();

	public Object call(String classQualifiedName, String methodName, LinkedHashMap<String, Object> allParameters)
			throws Exception {
		ClassDescriptor classDesc = PersistenceService.getInstance().getClass(classQualifiedName);
		if (classDesc != null) {
			Class<?> clazz = classDesc.getClassClass();
			Object instanceOfClass = clazz.newInstance();
			List<MethodContainer> methods = procuraMetodoCorrespondente(clazz, methodName, allParameters.keySet());
			if (methods != null && !methods.isEmpty()) {
				MethodContainer methodSelecionado = verificaCompatibilidadeDeParametros(methods, allParameters);
				return executaChamadaMetodoComRetornoEspecifico(methodSelecionado, instanceOfClass);
			}
			throw new Exception("erro ao executar metodo");
		}
		throw new Exception("erro ao executar metodo");
	}

	private MethodContainer verificaCompatibilidadeDeParametros(List<MethodContainer> methods,
			LinkedHashMap<String, Object> allParameters) {
		for (MethodContainer methodContainer : methods) {
			List<String> nomesParametrosdoMetodo = methodContainer.getLabeledParameterNames();
			boolean ocorreuErro = false;
			valoresConvertidos.clear();
			for (int i = 0; i < methodContainer.getMethod().getParameterCount(); i++) {
				try {
					if (methodContainer.getMethod().getParameters()[i].getType().isPrimitive()) {
						TransformationService service = new TransformationService();
						valoresConvertidos
								.add(service.wrapperPrimitive(methodContainer.getMethod().getParameters()[i].getType(),
										String.valueOf(allParameters.get(nomesParametrosdoMetodo.get(i)))));
					} else {
						valoresConvertidos.add(methodContainer.getMethod().getParameters()[i].getType()
								.cast(allParameters.get(nomesParametrosdoMetodo.get(i))));
					}
				} catch (Exception e) {
					ocorreuErro = true;
					break;
				}
			}
			if (!ocorreuErro) {
				return methodContainer;
			}
		}
		return null;
	}

	private List<MethodContainer> procuraMetodoCorrespondente(Class<?> clazz, String methodName,
			Set<String> nomesParametros) throws Exception {
		List<MethodContainer> metodos = new ArrayList<MethodContainer>();
		ClassContainer container = reader.readingAnnotationsTo(clazz, ClassContainer.class);
		for (MethodContainer methodContainer : container.getMethodsWithServiceMethod()) {
			if (methodContainer.getNomeMethod().equals(methodName)
					&& methodContainer.getMethod().getParameterCount() == nomesParametros.size()
					&& nomesParametrosCorrespondentes(methodContainer.getLabeledParameterNames(), nomesParametros)) {
				metodos.add(methodContainer);
			}
		}
		if (metodos.isEmpty()) {
			return null;
		} else {
			return metodos;
		}

	}

	private boolean nomesParametrosCorrespondentes(List<String> parametrosDoMetodo, Set<String> parametrosDaUrl) {
		for (String parametroDaUrl : parametrosDaUrl) {
			if (!parametrosDoMetodo.contains(parametroDaUrl)) {
				return false;
			}
		}
		return true;
	}

	private Object executaChamadaMetodoComRetornoEspecifico(MethodContainer methodContainer, Object instanceOfClass)
			throws JsonProcessingException, Exception {
		Object returnValue;
		if (methodContainer.getNumberOfParameters() > 0) {
			returnValue = methodContainer.getMethod().invoke(instanceOfClass, valoresConvertidos.toArray());
		} else {
			returnValue = methodContainer.getMethod().invoke(instanceOfClass);
		}
		if (methodContainer.isTemAnotacaoJsonReturn()) {
			return transformationService.transformToJson(returnValue);
		} else if (methodContainer.isTemAnotacaoHtmlTableReturn()) {
			return transformationService.transformToHtml((List<?>) returnValue);
		} else {
			return returnValue;
		}
	}

}