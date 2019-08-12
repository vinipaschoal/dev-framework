package org.esfinge.virtuallab.metadata.processors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.esfinge.virtuallab.api.annotations.CustomReturn;
import org.esfinge.virtuallab.api.annotations.TableReturn;
import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.metadata.MetadataHelper;
import org.esfinge.virtuallab.metadata.MethodMetadata;
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
		
		// obtem os metadados do descritor
		MethodMetadata methodMetadata = MetadataHelper.getInstance().getMethodMetadata(method);
		
		// 
		if ( methodMetadata.isAnnotatedWithCustomReturn() )
			return this.getProcessor(method, CustomReturn.class);
		
		if ( methodMetadata.isAnnotatedWithTableReturn() )
			return this.getProcessor(method, TableReturn.class);
		
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
