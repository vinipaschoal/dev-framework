package org.esfinge.virtuallab.metadata.processors;

import java.lang.annotation.Annotation;

import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.web.json.JsonData;

/**
 * Retorna o proprio objeto retornado pelo metodo.  
 */
public class DefaultReturnProcessor extends MethodReturnProcessor<Annotation>
{	
	// instancia unica da classe
	private static DefaultReturnProcessor _instance;
	
	
	/**
	 * Singleton.
	 */
	public static DefaultReturnProcessor getInstance()
	{
		if ( _instance == null )
			_instance = new DefaultReturnProcessor();
		
		return _instance;
	}
	
	/**
	 * Construtor interno.
	 */
	private DefaultReturnProcessor()
	{		
	}
	
	@Override
	public JsonData process(Object value) throws Exception
	{
		// retorna o JSON do proprio objeto
		return JsonUtils.fromObjectToJsonData(value);
	}

	@Override
	public String getType()
	{
		return "PLAIN";
	}
}
