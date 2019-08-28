package org.esfinge.virtuallab.metadata.processors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.esfinge.virtuallab.api.annotations.MapReturn;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.utils.Utils;
import org.esfinge.virtuallab.web.json.JsonData;

/**
 * Processa o retorno de um metodo ao formato de grafico de barras para ser apresentando na UI. 
 */
@SuppressWarnings("unused")
public class MapReturnProcessor implements MethodReturnProcessor<MapReturn>
{
	private static final Double INVALID_COORDINATE = new Double(200);
	private static final double DEFAULT_LAT = -15.77972;
	private static final double DEFAULT_LONG = -47.92972;
			
	
	// anotacao utilizada no metodo
	private MapReturn annotation;
	
	
	@Override
	public void initialize(MapReturn annotation)
	{
		this.annotation = annotation;
	}

	@Override
	public JsonData process(Object value) throws Exception
	{
		// objeto de retorno
		MapObject returnObj = new MapObject();

		// informacoes do mapa
		returnObj.setInitialZoom(this.annotation.mapInitialZoom());
		
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
		Double lat = this.getLatitude(this.annotation.latField(), obj);
		Double lng = this.getLongitude(this.annotation.longField(), obj);
		
		// coordenadas sao validas?
		if ( lat == INVALID_COORDINATE || lng == INVALID_COORDINATE )
			return null;
		
		MarkerObject marker = new MarkerObject();
		marker.setLatitude(lat.doubleValue());
		marker.setLongitude(lng.doubleValue());
		
		// popup do marcador
		StringBuilder popup = new StringBuilder();
		
		// titulo do marcador
		if (! Utils.isNullOrEmpty(this.annotation.markerTitleField()) )
		{
			try
			{
				Object title = ReflectionUtils.getFieldValue(obj, this.annotation.markerTitleField());
				
				if ( title != null )
					popup.append(String.format("<b>%s</b><br/>", title.toString()));
			}
			catch ( Exception e )
			{				
			}
		}
		else if (! Utils.isNullOrEmpty(this.annotation.markerTitle()))
			popup.append(String.format("<b>%s</b><br/>", this.annotation.markerTitle()));
		
		// texto do marcador
		if (! Utils.isNullOrEmpty(this.annotation.markerTextField()) )
		{
			try
			{
				Object text = ReflectionUtils.getFieldValue(obj, this.annotation.markerTextField());
				
				if ( text != null )
					popup.append(text.toString());
			}
			catch ( Exception e )
			{				
			}
		}
		else if (! Utils.isNullOrEmpty(this.annotation.markerText()))
			popup.append(this.annotation.markerText());
		
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
			// tenta encontrar o campo lat ou latitude
			for ( String name : new String[]{"lat", "latitude"} )
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
			// tenta encontrar o campo lng, long ou longitude
			for ( String name : new String[]{"lng", "long", "longitude"} )
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
