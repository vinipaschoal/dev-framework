package org.esfinge.virtuallab.metadata.processors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.esfinge.virtuallab.api.annotations.TableReturn;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.utils.Utils;
import org.esfinge.virtuallab.web.json.JsonData;

/**
 * Processa o retorno de um metodo ao formato de tabela para ser apresentando na UI. 
 */
public class TableReturnProcessor extends MethodReturnProcessor<TableReturn>
{
	@Override
	public JsonData process(Object value) throws Exception
	{
		// objeto de retorno
		TableReturnObject returnObj = new TableReturnObject();

		// verifica se nao eh nulo ou uma colecao vazia
		if (! Utils.isNullOrEmpty(value) )
		{
			// cast para collection
			List<?> collection = new ArrayList<>((Collection<?>) value);
			
			// tipo dos objetos
			Class<?> clazz = collection.get(0).getClass();
			
			// os campos que serao retornados
			String[] fields = this.annotation.fields().length > 0 
					? this.annotation.fields() 
					: ReflectionUtils.getAllFields(clazz)
						.stream()
						.map(f -> f.getName())
						.toArray(String[]::new);
			
			// os cabecalhos os campos
			String[] headers = this.annotation.headerLabels().length == fields.length  
					? this.annotation.headerLabels() 
					: Arrays.asList(fields)
						.stream()
						.map(s -> s.toUpperCase()) // transforma para UPPERCASE
						.toArray(String[]::new);
					
			// verifica se os campos sao validos
			List<String> returnFields = new ArrayList<>();
			for ( int i = 0; i < fields.length; i++ )
				if ( ReflectionUtils.hasField(clazz, fields[i]) )
				{
					// adiciona na lista de campos
					returnFields.add(fields[i]);
					
					// adiciona ao header
					returnObj.header.add(headers[i]);
				}
			
			// adiciona as linhas
			for ( Object obj : collection )
			{
				List<Object> row = new ArrayList<>();
							
				for ( String fieldName : returnFields )
					row.add(ReflectionUtils.getFieldValue(obj, fieldName));
				
				returnObj.addRow(row);
			}
			
			// mostrar cabecalho?
			returnObj.setShowHeader(this.annotation.showHeader());
		}
		
		// retorna o objeto JSON
		return JsonUtils.fromObjectToJsonData(returnObj);
	}
	
	@Override
	public String getType()
	{
		return "TABLE";
	}


	@SuppressWarnings("unused")
	private class TableReturnObject
	{
		private List<List<Object>> rows;
		private List<String> header;
		private boolean showHeader;
		
		
		public TableReturnObject()
		{
			this.rows = new ArrayList<>();
			this.header = new ArrayList<>();
		}

		public List<List<Object>> getRows()
		{
			return rows;
		}

		public void setRows(List<List<Object>> rows)
		{
			this.rows = rows;
		}
		
		public void addRow(List<Object> row)
		{
			this.rows.add(row);
		}

		public List<String> getHeader()
		{
			return header;
		}

		public void setHeader(List<String> header)
		{
			this.header = header;
		}

		public boolean isShowHeader()
		{
			return showHeader;
		}

		public void setShowHeader(boolean showHeader)
		{
			this.showHeader = showHeader;
		}
	}
}
