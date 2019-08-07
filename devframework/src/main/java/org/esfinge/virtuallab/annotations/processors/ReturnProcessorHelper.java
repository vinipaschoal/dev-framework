package org.esfinge.virtuallab.annotations.processors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.esfinge.virtuallab.annotations.CustomReturn;
import org.esfinge.virtuallab.annotations.TableReturn;
import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.metadata.MetadataHelper;
import org.esfinge.virtuallab.metadata.MethodMetadata;
import org.esfinge.virtuallab.metadata.validator.UniqueReturn;
import org.esfinge.virtuallab.utils.ReflectionUtils;

/**
 * Responsavel em preparar os objetos retornados dos metodos para o formato a ser renderizado na UI. 
 */
public class ReturnProcessorHelper
{
	// instancia unica da classe
	private static ReturnProcessorHelper _instance;
	
	
	/**
	 * Singleton.
	 */
	public static ReturnProcessorHelper getInstance()
	{
		if ( _instance == null )
			_instance = new ReturnProcessorHelper();
		
		return _instance;
	}
	
	/**
	 * Construtor interno.
	 */
	private ReturnProcessorHelper()
	{		
	}
	
	/**
	 * Retorna o processador apropriado para o retorno do metodo. 
	 */
	public ReturnProcessor<?> findProcessor(MethodDescriptor methodDescriptor) throws Exception
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
		return new NullReturnProcessor();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ReturnProcessor<?> getProcessor(Method method, Class<? extends Annotation> annotationClass) throws Exception
	{
		// obtem a anotacao do metodo
		Annotation annotation = method.getAnnotation(annotationClass);
		
		// recupera o return processor 
		ReturnProcessor returnProcessor = annotation.annotationType()
				.getAnnotation(UniqueReturn.class)
				.processor()
				.newInstance();
		
		// inicializa com a anotacao do metodo
		returnProcessor.initialize(annotation);
		
		//
		return returnProcessor;
	}
}
