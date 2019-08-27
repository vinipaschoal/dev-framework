package org.esfinge.virtuallab.metadata.processors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.metadata.validator.MethodReturn;
import org.esfinge.virtuallab.utils.ReflectionUtils;

/**
 * Responsavel em preparar os objetos retornados dos metodos para o formato a ser renderizado na UI. 
 */
public class MethodReturnProcessorHelper
{
	// instancia unica da classe
	private static MethodReturnProcessorHelper _instance;
	
	
	/**
	 * Singleton.
	 */
	public static MethodReturnProcessorHelper getInstance()
	{
		if ( _instance == null )
			_instance = new MethodReturnProcessorHelper();
		
		return _instance;
	}
	
	/**
	 * Construtor interno.
	 */
	private MethodReturnProcessorHelper()
	{		
	}
	
	/**
	 * Retorna o processador apropriado para o retorno do metodo. 
	 */
	public MethodReturnProcessor<?> findProcessor(MethodDescriptor methodDescriptor) throws Exception
	{
		// obtem o metodo a partir do descritor
		Method method = ReflectionUtils.getMethod(methodDescriptor);
		
		return this.findProcessor(method);
	}

	/**
	 * Retorna o processador apropriado para o retorno do metodo. 
	 */
	public MethodReturnProcessor<?> findProcessor(Method method) throws Exception
	{
		// verifica se tem alguma anotacao de MethodReturn
		for ( Annotation annotation : method.getAnnotations() )
			if ( annotation.annotationType().isAnnotationPresent(MethodReturn.class) )
				return this.getProcessor(method, annotation.annotationType());
		
		// processor default
		return DefaultReturnProcessor.getInstance();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private MethodReturnProcessor<?> getProcessor(Method method, Class<? extends Annotation> annotationClass) throws Exception
	{
		// obtem a anotacao do metodo
		Annotation annotation = method.getAnnotation(annotationClass);
		
		// recupera o return processor 
		MethodReturnProcessor returnProcessor = annotation.annotationType()
				.getAnnotation(MethodReturn.class)
				.processor()
				.newInstance();
		
		// inicializa com a anotacao do metodo
		returnProcessor.initialize(annotation);
		
		//
		return returnProcessor;
	}
}
