package org.esfinge.virtuallab.metadata.processors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.esfinge.virtuallab.api.annotations.MapReturn;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.utils.Utils;
import org.esfinge.virtuallab.web.json.JsonData;

/**
 * Processa o retorno de um metodo ao formato de grafico de barras para ser apresentando na UI. 
 */
@SuppressWarnings("unused")
public class MapReturnProcessor extends MethodReturnProcessor<MapReturn>
{
	private static final Double INVALID_COORDINATE = new Double(200);
	private static final double DEFAULT_LAT = -15.77972;
	private static final double DEFAULT_LONG = -47.92972;

	
	@Override
	public JsonData process(Object value) throws Exception
	{
		// objeto de retorno
		MapObject returnObj = new MapObject();

		// informacoes do mapa
		returnObj.setInitialZoom(this.annotation.mapZoom());
		
		// lat/long do centro do mapa
		Double mapLat = this.checkLatitude(this.annotation.mapCenterLat());
		Double mapLong = this.checkLongitude(this.annotation.mapCenterLong());
		returnObj.setLatitude(mapLat == INVALID_COORDINATE ? DEFAULT_LAT : mapLat.doubleValue());
		returnObj.setLongitude(mapLong == INVALID_COORDINATE ? DEFAULT_LONG : mapLong.doubleValue());

		
		// informacoes do(s) marcador(es)
		if ( value instanceof Collection )
		{
			for ( Object obj : Collection.class.cast(value) )
			{
				MarkerObject marker = this.parseMarker(obj);
				
				if ( marker != null )
					returnObj.getMarkers().add(marker);
			}
		}
		else
		{
			MarkerObject marker = this.parseMarker(value);
			
			if ( marker != null )
				returnObj.getMarkers().add(marker);
		}
		
		// retorna o objeto JSON
		return JsonUtils.fromObjectToJsonData(returnObj);
	}
	
	@Override
	public String getType()
	{
		return "MAP";
	}
	
	/**
	 * Verifica se a latitude eh valida.
	 */
	private Double checkLatitude(double latitude)
	{
		Double coord = new Double(latitude);
		
		if ( (coord.compareTo(new Double(90)) > 0) || (coord.compareTo(new Double(-90)) < 0) )
			return INVALID_COORDINATE;
		
		return latitude;
	}

	/**
	 * Verifica se a longitude eh valida.
	 */
	private Double checkLongitude(double longitude)
	{
		Double coord = new Double(longitude);
		
		if ( (coord.compareTo(new Double(180)) > 0) || (coord.compareTo(new Double(-180)) < 0) )
			return INVALID_COORDINATE;
		
		return longitude;
	}
	
	/**
	 * Retorna um objeto Marker lendo as propriedades do objeto.
	 */
	private MarkerObject parseMarker(Object obj)
	{
		// tenta obter a lat/long do objeto
		Double lat = this.getLatitude(this.annotation.markerLatField(), obj);
		Double lng = this.getLongitude(this.annotation.markerLongField(), obj);
		
		// coordenadas sao validas?
		if ( lat == INVALID_COORDINATE || lng == INVALID_COORDINATE )
			return null;
		
		MarkerObject marker = new MarkerObject();
		marker.setLatitude(lat.doubleValue());
		marker.setLongitude(lng.doubleValue());
		
		// popup do marcador
		StringBuilder popup = new StringBuilder();
		
		// titulo do marcador
		if (! Utils.isNullOrEmpty(this.annotation.markerTitle()) )
		{
			String title = this.parseEL(this.annotation.markerTitle(), obj);
			if ( title != null )
				popup.append(String.format("<b>%s</b><br/>", title.toString()));
		}

		// texto do marcador
		if (! Utils.isNullOrEmpty(this.annotation.markerText()) )
		{
			String text = this.parseEL(this.annotation.markerText(), obj);
			if ( text != null )
				popup.append(text.toString());
		}
		
		//
		marker.setPopup(popup.toString());
		return marker;
	}
	
	/**
	 * Tenta obter o valor da latitude do objeto.
	 * Retorna INVALID_COORDINATE caso a propriedade nao seja encontrada ou o valor seja invalido.
	 */
	private double getLatitude(String field, Object obj)
	{
		// verifica se foi especificado o campo
		if ( Utils.isNullOrEmpty(field) )
		{
			// tenta encontrar o campo de latitude
			for ( String name : new String[]{"latitude", "lat", "latid", "ltd"} )
			{
				try
				{
					// le o valor
					Object lat = ReflectionUtils.getFieldValue(obj, name);

					// troca virgula por ponto e verifica se o valor eh valido
					if ( lat != null )
						return this.checkLatitude(Double.valueOf(lat.toString().replace(',', '.')));
				}
				catch ( Exception e )
				{
				}
			}
		}
		else
		{
			try
			{
				// le o valor
				Object lat = ReflectionUtils.getFieldValue(obj, field);

				// troca virgula por ponto e verifica se o valor eh valido
				if ( lat != null )
					return this.checkLatitude(Double.valueOf(lat.toString().replace(',', '.')));
			}
			catch ( Exception e )
			{
			}
		}
		
		// 
		return INVALID_COORDINATE;
	}
	
	/**
	 * Tenta obter o valor da longitude do objeto.
	 * Retorna INVALID_COORDINATE caso a propriedade nao seja encontrada ou o valor seja invalido.
	 */
	private double getLongitude(String field, Object obj)
	{
		// verifica se foi especificado o campo
		if ( Utils.isNullOrEmpty(field) )
		{
			// tenta encontrar o campo de longitude
			for ( String name : new String[]{"longitude", "longit", "lng", "lgd"} )
			{
				try
				{
					// le o valor
					Object lng = ReflectionUtils.getFieldValue(obj, name);

					// troca virgula por ponto e verifica se o valor eh valido
					if ( lng != null )
						return this.checkLatitude(Double.valueOf(lng.toString().replace(',', '.')));
				}
				catch ( Exception e )
				{
				}
			}
		}
		else
		{
			try
			{
				// le o valor
				Object lng = ReflectionUtils.getFieldValue(obj, field);

				// troca virgula por ponto e verifica se o valor eh valido
				if ( lng != null )
					return this.checkLatitude(Double.valueOf(lng.toString().replace(',', '.')));
			}
			catch ( Exception e )
			{
			}
		}
		
		// 
		return INVALID_COORDINATE;
	}
	
	/**
	 * Processa as expressoes EL do texto informado.
	 */
	private String parseEL(String text, Object obj)
	{
		// procura por aspas simples (') e escapa (\')
		String textEL = text.trim().replaceAll("'", "\\\\'");

		// processa os campos de EL ${}
		textEL = textEL.replaceAll("\\$\\{(.+?)\\}", "' + $1 + '");
		
		// escapa o texto final
		textEL = "'" + textEL + "'";
		
		// Apache Commons JEXL
		JexlEngine jexl = new JexlBuilder().cache(512).silent(true).create();
		JexlExpression exp = jexl.createExpression(textEL);
		JexlContext context = new MapContext();
		context.set("obj", obj);
		
		return exp.evaluate(context).toString();
	}

	
	// objeto JSON Leaflet.js do tipo Map
	private class MapObject
	{
		private double latitude;
		private double longitude;
		private int initialZoom;
		private List<MarkerObject> markers;
		
		
		public MapObject()
		{
			this.markers = new ArrayList<>();
		}

		public double getLatitude()
		{
			return latitude;
		}

		public void setLatitude(double latitude)
		{
			this.latitude = latitude;
		}

		public double getLongitude()
		{
			return longitude;
		}

		public void setLongitude(double longitude)
		{
			this.longitude = longitude;
		}

		public int getInitialZoom()
		{
			return initialZoom;
		}

		public void setInitialZoom(int initialZoom)
		{
			this.initialZoom = initialZoom;
		}

		public List<MarkerObject> getMarkers()
		{
			return markers;
		}

		public void setMarkers(List<MarkerObject> markers)
		{
			this.markers = markers;
		}
	}

	// objeto JSON Leaflet.js do tipo Marker
	private class MarkerObject
	{
		private double latitude;
		private double longitude;
		private String popup;
		
		
		public double getLatitude()
		{
			return latitude;
		}
		public void setLatitude(double latitude)
		{
			this.latitude = latitude;
		}
		public double getLongitude()
		{
			return longitude;
		}
		public void setLongitude(double longitude)
		{
			this.longitude = longitude;
		}
		public String getPopup()
		{
			return popup;
		}
		public void setPopup(String popup)
		{
			this.popup = popup;
		}
	}
}
