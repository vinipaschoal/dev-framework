package org.esfinge.virtuallab.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.esfinge.virtuallab.descriptors.ClassDescriptor;
import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.metadata.ClassMetadata;
import org.esfinge.virtuallab.metadata.ContainerFactory;
import org.esfinge.virtuallab.metadata.MethodMetadata;
import org.esfinge.virtuallab.metadata.TypeContainer;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Invoca metodos em classes de servico. 
 */
public class InvokerService
{
	// instancia unica da classe
	private static InvokerService _instance;

	
 	/**
	 * Construtor interno.
	 */
	private InvokerService()
	{
		
	}
	
	/**
	 * Singleton.
	 */
	public static InvokerService getInstance()
	{
		if (_instance == null)
			_instance = new InvokerService();

		return _instance;
	}
	
	/**
	 * Invoca o metodo com os parametros informados.
	 */
	public Object call(MethodDescriptor methodDescriptor, Map<String, Object> parametersMap)
	{
		
	}

	

	private TransformationService transformationService = new TransformationService();
	private List<Object> convertedValues = new ArrayList<Object>();

	public Object call(String classQualifiedName, String methodName, LinkedHashMap<String, Object> allParameters)
			throws Exception
	{
		ClassDescriptor classDesc = PersistenceService.getInstance().getClass(classQualifiedName);
		if (classDesc != null)
		{
			Class<?> clazz = classDesc.getClassClass();
			Object instanceOfClass = clazz.newInstance();
			List<MethodMetadata> methods = searchMatchingMethod(clazz, methodName, allParameters.keySet());
			if (methods != null && !methods.isEmpty())
			{
				MethodMetadata selectedMethod = checkParameterCompatibility(methods, allParameters);
				return callMethodWithSpecificReturn(selectedMethod, instanceOfClass);
			}
			throw new Exception("erro ao executar metodo");
		}
		throw new Exception("erro ao executar metodo");
	}

	private MethodMetadata checkParameterCompatibility(List<MethodMetadata> methods,
			LinkedHashMap<String, Object> allParameters)
	{
		for (MethodMetadata methodContainer : methods)
		{
			List<String> parameterNames = methodContainer.getLabeledParameterNames();
			boolean errorOccurred = false;
			convertedValues.clear();
			for (int i = 0; i < methodContainer.getMethod().getParameterCount(); i++)
			{
				try
				{
					if (methodContainer.getMethod().getParameters()[i].getType().isPrimitive())
					{
						TransformationService transformationService = new TransformationService();
						convertedValues.add(transformationService.wrapperPrimitive(
								methodContainer.getMethod().getParameters()[i].getType(),
								String.valueOf(allParameters.get(parameterNames.get(i)))));
					}
					else
					{
						convertedValues.add(methodContainer.getMethod().getParameters()[i].getType()
								.cast(allParameters.get(parameterNames.get(i))));
					}
				}
				catch (Exception e)
				{
					errorOccurred = true;
					break;
				}
			}
			if (!errorOccurred)
			{
				return methodContainer;
			}
		}
		return null;
	}

	private List<MethodMetadata> searchMatchingMethod(Class<?> clazz, String methodName, Set<String> parameterNames)
			throws Exception
	{
		List<MethodMetadata> methodsContainer = new ArrayList<MethodMetadata>();
		ClassMetadata classContainer = ContainerFactory.create(clazz, TypeContainer.CLASS_CONTAINER);
		for (MethodMetadata methodContainer : classContainer.getMethodsWithServiceMethod())
		{
			if (methodContainer.getMethodName().equals(methodName)
					&& methodContainer.getMethod().getParameterCount() == parameterNames.size()
					&& matchingParameterNames(methodContainer.getLabeledParameterNames(), parameterNames))
			{
				methodsContainer.add(methodContainer);
			}
		}
		if (methodsContainer.isEmpty())
		{
			return null;
		}
		else
		{
			return methodsContainer;
		}

	}

	private boolean matchingParameterNames(List<String> methodParameters, Set<String> urlParameters)
	{
		if (methodParameters.size() != urlParameters.size())
		{
			return false;
		}
		for (String parametroDaUrl : urlParameters)
		{
			if (!methodParameters.contains(parametroDaUrl))
			{
				return false;
			}
		}
		return true;
	}

	private Object callMethodWithSpecificReturn(MethodMetadata methodContainer, Object instanceOfClass)
			throws JsonProcessingException, Exception
	{
		Object returnValue;
		if (methodContainer.getNumberOfParameters() > 0)
		{
			returnValue = methodContainer.getMethod().invoke(instanceOfClass, convertedValues.toArray());
		}
		else
		{
			returnValue = methodContainer.getMethod().invoke(instanceOfClass);
		}
		if (methodContainer.isAnnotatedWithJsonReturn())
		{
			return transformationService.transformToJson(returnValue);
		}
		else if (methodContainer.isAnnotatedWithHtmlTableReturn())
		{
			return transformationService.transformToHtml((List<?>) returnValue);
		}
		else
		{
			return returnValue;
		}
	}

}