package org.esfinge.virtuallab.metadata;

import java.lang.reflect.Method;

import org.esfinge.virtuallab.exceptions.MetadataException;

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
	public ClassMetadata getClassMetadata(Class<?> clazz) throws MetadataException
	{ 
		try
		{
			// ServiceDAO
			if ( clazz.isInterface() )
				return reader.readingAnnotationsTo(clazz, ServiceDAOMetadata.class);	
				
			// ServiceClass
			else
				return reader.readingAnnotationsTo(clazz, ServiceClassMetadata.class);	
				
		}
		catch ( Exception e )
		{
			throw new MetadataException(String.format("Erro ao recuperar os metadados da classe '%s'!", clazz.getCanonicalName()), e);
		}
	}
	
	/**
	 * Retorna as informacoes de metadado do metodo informado.
	 */
	public MethodMetadata getMethodMetadata(Method method) throws MetadataException
	{
		try
		{
			return reader.readingAnnotationsTo(method, MethodMetadata.class);	
		}
		catch ( Exception e )
		{
			throw new MetadataException(String.format("Erro ao recuperar os metadados do metodo '%s'!", method.getName()), e);
		}
	}
}
