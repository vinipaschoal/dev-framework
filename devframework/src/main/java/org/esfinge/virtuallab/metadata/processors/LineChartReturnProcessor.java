package org.esfinge.virtuallab.metadata.processors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.esfinge.virtuallab.api.annotations.LineChartReturn;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.utils.Utils;
import org.esfinge.virtuallab.web.json.JsonData;
import org.esfinge.virtuallab.web.json.JsonObject;

/**
 * Processa o retorno de um metodo ao formato de grafico de linhas para ser apresentando na UI. 
 */
@SuppressWarnings("unused")
public class LineChartReturnProcessor extends MethodReturnProcessor<LineChartReturn>
{
	@Override
	public JsonData process(Object value) throws Exception {
		// objeto de retorno
		
		ChartObject returnObj = new ChartObject(this.annotation);
		
		
			
		// popula as informacoes
		returnObj.setType(this.annotation.horizontal() ? "horizontalBar" : annotation.typeOfChart());
		returnObj.getOptions().populate(this.annotation);

		
		if(this.annotation.multipleDataset()) {
			if(value instanceof Map)
			{
				
				Map<String, Object> value2 = (Map<String, Object>) value;
				Map<String, Map<String, ?>> formatedValue = getMulltipleValuesMap(value2);
				System.out.println(formatedValue.toString());
				System.out.println("+++====+++");
				List<DatasetObject> dataset = this.createDataSetTime(formatedValue);
				returnObj.data.datasets = dataset;				
				System.out.println(formatedValue.toString());
				System.out.println("++++++");
			}
			else if(value instanceof List)
			{
				System.out.println("==========");
				Map<String, Map<String, ?>> formatedValue = getMulltipleValues(value);
				List<DatasetObject> dataset = this.createDataSetTime(formatedValue);
				returnObj.data.datasets = dataset;	
				System.out.println("==========");
			}
		}
				
		
		JsonData xxx = JsonUtils.fromObjectToJsonData(returnObj);
		System.out.println(xxx);
		return JsonUtils.fromObjectToJsonData(returnObj);
		
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Map<String, ?>> getMulltipleValues(Object value) {
		// TODO Auto-generated method stub
		
		Map<String, Map<String, ?>> addNumber =new HashMap<String, Map<String, ?>>();
		if(this.annotation.yAxis().length>1)
		{
			for (String name: this.annotation.yAxis()) {
				System.out.println(name);
				Map<String, Object> lx = new HashMap<String, Object>(); 
				List<Object> numberY = new ArrayList<Object>();
				List<Object> numberX = new ArrayList<Object>();
				for(Object  obj :(List<Object>)value) {
					try {
						Object nix = ReflectionUtils.getFieldValue(obj,this.annotation.xAxis()[0]);
							if(nix instanceof Calendar);
							{
								Calendar c = (Calendar) nix;
								nix = c.getTimeInMillis();
							}
							
						Object niy = ReflectionUtils.getFieldValue(obj,name);
						numberX.add(nix);
						numberY.add(niy);
						
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				lx.put("x", numberX);
				lx.put("y", numberY);
				addNumber.put(name, lx);
			}
		}
		return addNumber;
	}

	
	private Map<String, Map<String, ?>> getMulltipleValuesMap(Map<String,Object> value) {
		// TODO Auto-generated method stub
		
		Map<String,Map<String,?>>  addNumber = new HashMap<String, Map<String,?>>();
		
		
		if(this.annotation.temporalSeries()) {
				for (String element : annotation.yAxis()) {
					System.out.println(element);
					List<?> numberY =  this.getValues(value.get(element));
					List<?> numberX =  this.getValues(value.get(this.annotation.xAxis()[0]));
					Map<String,Object> elements= new HashMap<String, Object>();
						elements.put("x", numberX);
						
						elements.put("y",numberY);
					addNumber.put(element, elements);
				}
		
		}
		System.out.println(addNumber.toString());
		return addNumber;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	private List<String> getLabels(Object value)
	{
		//
		List<String> labels = new ArrayList<>();
		
 		// verifica se foi especificada a propriedade "labels" da anotacao
	 	if (! Utils.isNullOrEmpty(this.annotation.dataLabels()) )
	 		if(value instanceof Map)
	 		{
	 			if(this.annotation.temporalSeries())
	 			{
	 				System.out.println("ENTROU AQUI");
	 				Object x = ((Map) value).get(this.annotation.dataLabels());
	 				
	 				
	 				System.out.println("++++++++++++++++++++++++++++++");
	 				System.out.println(x.toString());
	 				if(x instanceof List)
	 				{

	 					for (Object object : (List<?>)x) {
							System.out.println(object);
							labels.add(object.toString());
						}
	 				}
	 				
	 				System.out.println("++++++++++++++++++++++++++++++");
	 				
	 				
	 				//labels.addAll((Collection<? extends String>) );
	 				//System.out.println(labels.toString());
	 				
	 			}	
	 			
	 		}
	 		else
	 			labels.addAll(Arrays.asList(this.annotation.dataLabels()));
		
		// se for uma mapa, usa as chaves como labels
	 	
	 	else if ( value instanceof List )
		{
	 		List<?> collection = List.class.cast(value);
	 		
	 		// obtem o label em cada objeto da colecao
 	 		for ( int i = 0; i < collection.size(); i++ )
 	 			try
	 	 		{
 	 				// tenta acessar a propriedade do objeto
 	 				//labels.add(ReflectionUtils.getFieldValue(collection.get(i), this.annotation.dataLabelsField()).toString());
	 	 		}
	 	 		catch ( Exception e )
	 	 		{
	 	 			// texto generico (DATA 01, DATA 02, DATA 03..)
	 	 			//labels.add(String.format("DATA %02d", i+1));
	 	 		}
		}
	 	
	 	return labels;
	}
	
	private List<DatasetObject> createDataSetTime(List<?> value)
	{
			
		return null;
	}
	
	private List<DatasetObject> createDataSetTime(Map<String, Map<String, ?>> values)
	{
		List<DatasetObject> datasetList = new ArrayList<LineChartReturnProcessor.DatasetObject>();
		
		if (! Utils.isNullOrEmpty(values) ) {
			for(String yAxis:this.annotation.yAxis()) {
				DatasetObject dataset = new DatasetObject();

				List<?> xArr = (List<?>) values.get(yAxis).get("x");
				List<?> yArr = (List<?>) values.get(yAxis).get("y");
				if (! Utils.isNullOrEmpty(xArr)&&! Utils.isNullOrEmpty(yArr) )
				{
					System.out.println("entrou");
					for ( int i = 0; i < xArr.size(); i++ )
					{
						Object xValue = xArr.get(i);
						Object yValue = yArr.get(i);
						
						if ( (xValue != null) && (yValue != null))
						{	
							
							org.esfinge.virtuallab.metadata.processors.LineChartReturnProcessor.Data data = new org.esfinge.virtuallab.metadata.processors.LineChartReturnProcessor.Data();
							data.setX(Number.class.cast(xValue));
							data.setY(Number.class.cast(yValue));
							dataset.data.add(data);

							//dataset.dataY.add(Number.class.cast(xValue));
							//dataset.dataX.add(Number.class.cast(yValue));

						}
						
					}
					
				}
				
				dataset.borderColor = String.format("rgba(%d, %d, %d, 1)", RandomUtils.nextInt(0, 256), RandomUtils.nextInt(0, 256), RandomUtils.nextInt(0, 256));
				datasetList.add(dataset);
			}
		}
		
		return datasetList;
	}
	
	
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
						if(this.annotation.temporalSeries())
						{
							//dataset.data.add(Number.class.cast(value));
						}
						else
						{
							//dataset.dataY.add(Number.class.cast(value));
						}
						dataset.borderColor = String.format("rgba(%d, %d, %d, 1)", RandomUtils.nextInt(0, 256), RandomUtils.nextInt(0, 256), RandomUtils.nextInt(0, 256));
						
						
						continue;
					}
				
				// valor invalido, usa 0 como default
				//dataset.backgroundColor.add(colors.get(i % colors.size()));
			}
		}
		
		return dataset;
	}

	
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
	 	 			colors.add(String.format("rgba(%d, %d, %d, 1)", RandomUtils.nextInt(0, 256), RandomUtils.nextInt(0, 256), RandomUtils.nextInt(0, 256)));
	 	 		}
		}
	 	
	 	//
	 	return colors;
	}
	
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
	

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "CHART";
	}
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private class DatasetObject
	{
		private String label;
		private List<Data> data;
		private boolean fill;
		private String pointStyle;
		private String borderColor;
		private Double  tension;
		
		public DatasetObject()
		{
			this.data = new ArrayList<Data>();;
			this.fill =false;
			this.pointStyle = "line";
			this.borderColor = String.format("rgba(%d, %d, %d, 1)", RandomUtils.nextInt(0, 256), RandomUtils.nextInt(0, 256), RandomUtils.nextInt(0, 256));
			this.tension = 0d;
		}

		
	


		public List<Data> getData() {
			return data;
		}




		public void setDataX(List<Data> data) {
			this.data = data;
		}




		public Double getTension() {
			return tension;
		}




		public void setTension(Double tension) {
			this.tension = tension;
		}



		public String getBorderColor() {
			return borderColor;
		}




		public void setBorderColor(String borderColor) {
			this.borderColor = borderColor;
		}




		public String getPointStyle() {
			return pointStyle;
		}




		public void setPointStyle(String pointStyle) {
			this.pointStyle = pointStyle;
		}




		public String getLabel()
		{
			return label;
		}

		public void setLabel(String label)
		{
			this.label = label;
		}

	

		public boolean isFill() {
			return fill;
		}

		public void setFill(boolean fill) {
			this.fill = fill;
		}
		
		
		/*
		public List<String> getBackgroundColor()
		{
			return backgroundColor;
		}
		public void setBackgroundColor(List<String> backgroundColor)
		{
			this.backgroundColor = backgroundColor;
		}*/
	}
	

	public class Data{
		private Number x,y;

		public Data() {
			}

		public Number getX() {
			return x;
		}

		public void setX(Number x) {
			this.x = x;
		}
		
		public Number getY() {
			return y;
		}

		public void setY(Number y) {
			this.y = y;
		}
		
		  
		
	}
	
	private class ChartObject
	{
		private String type;
		private DataObject data;
		private OptionsObject options;
		
		
		public ChartObject(LineChartReturn object)
		{
			this.type = object.typeOfChart();
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


	
	
	
	
	
	private class DataObject
	{
		private List<DatasetObject> datasets;
		
		
		public DataObject()
		{
			this.datasets = new ArrayList<>();
			
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
	
	

	//OPTIONS
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
		
		public void populate(LineChartReturn annotation)
		{
			// eixo X
			JsonObject xGridLines = new JsonObject();
			xGridLines.addProperty("display", annotation.xAxisShowGridlines());
			
			JsonObject xAxes = new JsonObject();
			xAxes.addProperty("type", "time");
			xAxes.addProperty("responsive", true);
			if (! Utils.isNullOrEmpty(annotation.xAxisLabel()) )
			{
				System.out.println("============Utils==============");
				JsonObject scaleLabel = new JsonObject();
				scaleLabel.addProperty("display", true);
				
				scaleLabel.addProperty("labelString", annotation.xAxisLabel());
					
				scaleLabel.addProperty("fontSize", annotation.axisFontSize());
				
				JsonObject time = new JsonObject();
				JsonObject displayFormats = new JsonObject();
				displayFormats.addProperty("minute", "DD/MM/YYYY HH:mm");
				time.addProperty("unit", "minute");
				time.addProperty("displayFormats", displayFormats);
				time.addProperty("tooltipFormat",displayFormats );
				//xAxes.addProperty("distribution", "series");
				xAxes.addProperty("time", time);
				xAxes.addProperty("scaleLabel", scaleLabel);
				System.out.println("============Utils==============");
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
			ticks.addProperty("min", 10e-10f);
			ticks.addProperty("max", 10e-2f);

			if ( annotation.horizontal() )
				xAxes.addProperty("ticks", ticks);
			else {
				yAxes.addProperty("ticks", ticks); 
				
			}
			
			yAxes.addProperty("type", "logarithmic");
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