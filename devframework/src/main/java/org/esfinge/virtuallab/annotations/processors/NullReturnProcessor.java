package org.esfinge.virtuallab.annotations.processors;

import java.lang.annotation.Annotation;

import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.web.json.JsonData;

/**
 * Retorna o proprio objeto retornado pelo metodo.  
 */
public class NullReturnProcessor implements ReturnProcessor<Annotation>
{	
	@Override
	public void initialize(Annotation annotation)
	{
		// ignora
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
