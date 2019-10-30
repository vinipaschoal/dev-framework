package org.esfinge.virtuallab.metadata.processors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.esfinge.virtuallab.api.annotations.BarChartReturn;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.utils.Utils;
import org.esfinge.virtuallab.web.json.JsonData;
import org.esfinge.virtuallab.web.json.JsonObject;

/**
 * Processa o retorno de um metodo ao formato de grafico de barras para ser apresentando na UI. 
 */
@SuppressWarnings("unused")
public class BarChartReturnProcessor extends MethodReturnProcessor<BarChartReturn>
{
	@Override
	public JsonData process(Object value) throws Exception
	{
		// objeto de retorno
		BarChartObject returnObj = new BarChartObject();

		// popula as informacoes
		returnObj.setType(this.annotation.horizontal() ? "horizontalBar" : "bar");
		returnObj.getOptions().populate(this.annotation);
		returnObj.getData().getLabels().addAll(this.getLabels(value));
		
		List<String> colors = this.getColors(value);
		List<?> values = this.getValues(value);
		returnObj.getData().getDatasets().add(this.createDataSet(values, colors));
		
		// retorna o objeto JSON
		return JsonUtils.fromObjectToJsonData(returnObj);
	}
	
	@Override
	public String getType()
	{
		return "CHART";
	}
	
	/**
	 * Retorna os nomes das categorias (barras) do grafico.
	 */
	@SuppressWarnings("unchecked")
	private List<String> getLabels(Object value)
	{
		//
		List<String> labels = new ArrayList<>();
		
 		// verifica se foi especificada a propriedade "labels" da anotacao
	 	if (! Utils.isNullOrEmpty(this.annotation.dataLabels()) )
			labels.addAll(Arrays.asList(this.annotation.dataLabels()));
		
		// se for uma mapa, usa as chaves como labels
	 	else if ( value instanceof Map )
	 		Map.class.cast(value).keySet().forEach(o -> labels.add(o.toString()));
		
		// se for lista, usa o campo indicado na propriedade "labelsField" da anotacao
	 	else if ( value instanceof List )
		{
	 		List<?> collection = List.class.cast(value);
	 		
	 		// obtem o label em cada objeto da colecao
 	 		for ( int i = 0; i < collection.size(); i++ )
 	 			try
	 	 		{
 	 				// tenta acessar a propriedade do objeto
 	 				labels.add(ReflectionUtils.getFieldValue(collection.get(i), this.annotation.dataLabelsField()).toString());
	 	 		}
	 	 		catch ( Exception e )
	 	 		{
	 	 			// texto generico (DATA 01, DATA 02, DATA 03..)
	 	 			labels.add(String.format("DATA %02d", i+1));
	 	 		}
		}
	 	
	 	//
	 	return labels;
	}
	
	/**
	 * Retorna as cores das categorias (barras) do grafico.
	 */
	@SuppressWarnings("unchecked")
	private List<String> getColors(Object value)
	{
		//
		List<String> colors = new ArrayList<>();
		
 		// verifica se foi especificada a propriedade "colors" da anotacao
	 	if (! Utils.isNullOrEmpty(this.annotation.dataColors()) )
	 		colors.addAll(Arrays.asList(this.annotation.dataColors()));

		// se for mapa ou colecao, usa o campo indicado na propriedade "colorsField" da anotacao
	 	else if (! Utils.isNullOrEmpty(value) )
		{
	 		List<?> collection = null;
	 		
	 		if ( value instanceof Map )
	 			collection = new ArrayList<>(Map.class.cast(value).values());
	 		
	 		else
	 			collection = List.class.cast(value);
	 		
	 		// obtem a cor em cada objeto da colecao
 	 		for ( int i = 0; i < collection.size(); i++ )
 	 			try
	 	 		{
 	 				// tenta acessar a propriedade do objeto
 	 				colors.add(ReflectionUtils.getFieldValue(collection.get(i), this.annotation.dataColorsField()).toString());
	 	 		}
	 	 		catch ( Exception e )
	 	 		{
	 	 			// cor aleatoria
	 	 			colors.add(String.format("rgba(%d, %d, %d, 0.7)", RandomUtils.nextInt(0, 256), RandomUtils.nextInt(0, 256), RandomUtils.nextInt(0, 256)));
	 	 		}
		}
	 	
	 	//
	 	return colors;
	}
	
	/**
	 * Retorna os valores das categorias (barras) do grafico.
	 */
	@SuppressWarnings("unchecked")
	private List<Number> getValues(Object value)
	{
		//
		List<Number> values = new ArrayList<>();
		
		if (! Utils.isNullOrEmpty(value) )
		{
	 		List<?> collection = null;
	 		
	 		if ( value instanceof Map )
	 			collection = new ArrayList<>(Map.class.cast(value).values());
	 		
	 		else
	 			collection = List.class.cast(value);
	 		
	 		// obtem o valor em cada objeto da colecao
	 		if ( Utils.isNullOrEmpty(this.annotation.dataValuesField()) && Number.class.isAssignableFrom(collection.get(0).getClass()) )
	 			collection.forEach(o -> values.add(Number.class.cast(o)));
	 		
	 		else
		 		for ( int i = 0; i < collection.size(); i++ )
		 			try
			 		{
		 				// tenta acessar a propriedade do objeto
		 				values.add(Number.class.cast(ReflectionUtils.getFieldValue(collection.get(i), this.annotation.dataValuesField())));
			 		}
			 		catch ( Exception e )
			 		{
			 			// campo / valor invalido no objeto, cancela e retorna lista vazia
			 			values.clear();
			 			break;
			 		}
		}
		
		//
		return values;
	}
	
	/**
	 * Cria o dataset com os valores do retorno do metodo. 
	 */
	private DatasetObject createDataSet(List<?> values, List<String> colors)
	{
		DatasetObject dataset = new DatasetObject();
		dataset.setLabel(this.annotation.legend());
		
		// existem valores para o grafico?
		if (! Utils.isNullOrEmpty(values) )
		{
			// valor | cor
			for ( int i = 0; i < values.size(); i++ )
			{
				// verifica se o valor eh valido
				Object value = values.get(i);				
				if ( value != null )
					if ( Number.class.isAssignableFrom(value.getClass()) )
					{
						dataset.data.add(Number.class.cast(value));
						dataset.backgroundColor.add(colors.get(i % colors.size()));
						continue;
					}
				
				// valor invalido, usa 0 como default
				dataset.data.add(0);
				dataset.backgroundColor.add(colors.get(i % colors.size()));
			}
		}
		
		return dataset;
	}
	
	// objeto JSON Chartjs do tipo BarChart
	private class BarChartObject
	{
		private String type;
		private DataObject data;
		private OptionsObject options;
		
		
		public BarChartObject()
		{
			this.type = "bar";
			this.data = new DataObject();
			this.options = new OptionsObject();
		}

		public String getType()
		{
			return type;
		}

		public void setType(String type)
		{
			this.type = type;
		}

		public DataObject getData()
		{
			return data;
		}

		public void setData(DataObject data)
		{
			this.data = data;
		}

		public OptionsObject getOptions()
		{
			return options;
		}

		public void setOptions(OptionsObject options)
		{
			this.options = options;
		}
	}

	// propriedade DATA do JSON Chartjs BarChart
	private class DataObject
	{
		private List<String> labels;
		private List<DatasetObject> datasets;
		
		
		public DataObject()
		{
			this.labels = new ArrayList<>();
			this.datasets = new ArrayList<>();
		}
		
		public List<String> getLabels()
		{
			return labels;
		}
		
		public void setLabels(List<String> labels)
		{
			this.labels = labels;
		}
		
		public List<DatasetObject> getDatasets()
		{
			return datasets;
		}
		
		public void setDatasets(List<DatasetObject> datasets)
		{
			this.datasets = datasets;
		}
	}
	
	// propriedade DATA:DATASET do JSON Chartjs BarChart
	private class DatasetObject
	{
		private String label;
		private List<Number> data;
		private List<String> backgroundColor;
		
		
		public DatasetObject()
		{
			this.data = new ArrayList<>();
			this.backgroundColor = new ArrayList<>();
		}

		public String getLabel()
		{
			return label;
		}

		public void setLabel(String label)
		{
			this.label = label;
		}

		public List<Number> getData()
		{
			return data;
		}

		public void setData(List<Number> data)
		{
			this.data = data;
		}

		public List<String> getBackgroundColor()
		{
			return backgroundColor;
		}

		public void setBackgroundColor(List<String> backgroundColor)
		{
			this.backgroundColor = backgroundColor;
		}
	}

	// propriedade OPTIONS do JSON Chartjs BarChart
	private class OptionsObject
	{
		private JsonObject scales;
		private JsonObject title;
		private JsonObject legend;
		
		public OptionsObject()
		{
			this.scales = new JsonObject();
			this.title = new JsonObject();
			this.legend = new JsonObject();
		}
		
		public void populate(BarChartReturn annotation)
		{
			// eixo X
			JsonObject xGridLines = new JsonObject();
			xGridLines.addProperty("display", annotation.xAxisShowGridlines());
			
			JsonObject xAxes = new JsonObject();
			xAxes.addProperty("gridLines", xGridLines);

			if (! Utils.isNullOrEmpty(annotation.xAxisLabel()) )
			{
				JsonObject scaleLabel = new JsonObject();
				scaleLabel.addProperty("display", true);
				scaleLabel.addProperty("labelString", annotation.xAxisLabel());
				scaleLabel.addProperty("fontSize", annotation.axisFontSize());
				
				xAxes.addProperty("scaleLabel", scaleLabel);
			}
			
			// eixo Y
			JsonObject yGridLines = new JsonObject();
			yGridLines.addProperty("display", annotation.yAxisShowGridlines());
				
			JsonObject yAxes = new JsonObject();
			yAxes.addProperty("gridLines", yGridLines);
				
			if (! Utils.isNullOrEmpty(annotation.yAxisLabel()) )
			{
				JsonObject scaleLabel = new JsonObject();
				scaleLabel.addProperty("display", true);
				scaleLabel.addProperty("labelString", annotation.yAxisLabel());
				scaleLabel.addProperty("fontSize", annotation.axisFontSize());
				
				yAxes.addProperty("scaleLabel", scaleLabel);
			}
			
			// ticks (comecar escala do 0)
			JsonObject ticks = new JsonObject();
			ticks.addProperty("beginAtZero", true);

			if ( annotation.horizontal() )
				xAxes.addProperty("ticks", ticks);
			else
				yAxes.addProperty("ticks", ticks); 

			this.scales.addProperty("xAxes", Arrays.asList(xAxes));
			this.scales.addProperty("yAxes", Arrays.asList(yAxes));
			
			// titulo
			this.title.addProperty("text", annotation.title());
			this.title.addProperty("fontSize", annotation.titleFontSize());
			this.title.addProperty("display", !Utils.isNullOrEmpty(annotation.title()));
			
			// legenda
			this.legend.addProperty("display", false);
			this.legend.addProperty("position", "top");
		}
	}
}
