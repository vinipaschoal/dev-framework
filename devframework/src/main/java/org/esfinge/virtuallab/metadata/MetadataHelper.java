package org.esfinge.virtuallab.metadata;

import java.lang.reflect.Method;

import net.sf.esfinge.metadata.AnnotationReader;

/**
 * Responsavel em manipular metadados de classes, metodos e atributos.
 */
public class MetadataHelper
{
	// EsfingeMetadata: leitor de anotacoes
	private AnnotationReader reader;
	
	// instancia unica da classe
	private static MetadataHelper _instance;

	
	/**
	 * Singleton.
	 */
	public static MetadataHelper getInstance()
	{
		if ( _instance == null )
			_instance = new MetadataHelper();
		
		return _instance;
	}
	
	/**
	 * Construtor interno.
	 */
	private MetadataHelper()
	{
		this.reader = new AnnotationReader();
	}
	
	/**
	 * Retorna as informacoes de metadado da classe informada.
	 */
	public ClassMetadata getClassMetadata(Class<?> clazz) throws Exception
	{ 
		return reader.readingAnnotationsTo(clazz, ClassMetadata.class);	
	}
	
	/**
	 * Retorna as informacoes de metadado do metodo informado.
	 */
	public MethodMetadata getMethodMetadata(Method method) throws Exception
	{
		return reader.readingAnnotationsTo(method, MethodMetadata.class);	
	}
}
