package org.esfinge.virtuallab.metadata.processors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.esfinge.virtuallab.api.annotations.BarChartReturn;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.utils.Utils;
import org.esfinge.virtuallab.web.json.JsonData;
import org.esfinge.virtuallab.web.json.JsonObject;

/**
 * Processa o retorno de um metodo ao formato de grafico de barras para ser apresentando na UI. 
 */
@SuppressWarnings("unused")
public class BarChartReturnProcessor implements MethodReturnProcessor<BarChartReturn>
{
	// anotacao utilizada no metodo
	private BarChartReturn annotation;
	
	
	@Override
	public void initialize(BarChartReturn annotation)
	{
		this.annotation = annotation;
	}

	@Override
	public JsonData process(Object value) throws Exception
	{
		// objeto de retorno
		BarChartObject returnObj = new BarChartObject();

		// popula as informacoes
		returnObj.setType(this.annotation.horizontal() ? "horizontalBar" : "bar");
		returnObj.getOptions().populate(this.annotation);;
		returnObj.getData().getLabels().addAll(Arrays.asList(annotation.labels()));

		List<?> values = Utils.isNullOrEmpty(value) ? new ArrayList<>() : new ArrayList<>((Collection<?>) value);
		List<String> colors = Utils.isNullOrEmpty(annotation.colors()) ? new ArrayList<>() : Arrays.asList(this.annotation.colors()); 
		returnObj.getData().getDatasets().add(this.createDataSet(values, colors));
		
		// retorna o objeto JSON
		return JsonUtils.fromObjectToJsonData(returnObj);
	}
	
	@Override
	public String getType()
	{
		return "CHART_BAR";
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
			// verifica se foram especificadas as cores
			if ( Utils.isNullOrEmpty(colors) )
				colors = Collections.nCopies(values.size(), "rgba(0, 0, 0, 0.2)");  // preto translucido

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
			xGridLines.addProperty("display", annotation.xAxisGridLines());
			
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
			
			this.scales.addProperty("xAxes", Arrays.asList(xAxes));

			
			// eixo Y
			JsonObject ticks = new JsonObject();
			ticks.addProperty("beginAtZero", true);
			
			JsonObject yGridLines = new JsonObject();
			yGridLines.addProperty("display", annotation.yAxisGridLines());
				
			JsonObject yAxes = new JsonObject();
			yAxes.addProperty("ticks", ticks);
			yAxes.addProperty("gridLines", yGridLines);
				
			if (! Utils.isNullOrEmpty(annotation.yAxisLabel()) )
			{
				JsonObject scaleLabel = new JsonObject();
				scaleLabel.addProperty("display", true);
				scaleLabel.addProperty("labelString", annotation.yAxisLabel());
				scaleLabel.addProperty("fontSize", annotation.axisFontSize());
				
				yAxes.addProperty("scaleLabel", scaleLabel);
			}
			
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
