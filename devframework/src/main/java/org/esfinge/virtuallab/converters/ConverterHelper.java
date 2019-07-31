package org.esfinge.virtuallab.converters;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsavel em realizar conversoes de tipos. 
 */
public class ConverterHelper
{	
	// mapa de conversores cadastrados
	private Map<String, Converter<?>> converterMap;
	
	// instancia unica da classe
	private static ConverterHelper _instance;
	
	
	/**
	 * Singleton.
	 */
	public static ConverterHelper getInstance()
	{
		if ( _instance == null )
			_instance = new ConverterHelper();
		
		return _instance;
	}
	
	/**
	 * Construtor interno.
	 */
	private ConverterHelper()
	{
		// inicializa o mapa de conversores
		this.converterMap = new HashMap<>();
		
		// cadastra conversores para os tipos basicos
		Converter<?> converter = new ByteConverter();
		this.converterMap.put(byte.class.getCanonicalName(), converter);
		this.converterMap.put(Byte.class.getCanonicalName(), converter);
		
		converter = new ShortConverter();
		this.converterMap.put(short.class.getCanonicalName(), converter);
		this.converterMap.put(Short.class.getCanonicalName(), converter);
		
		converter = new IntConverter();
		this.converterMap.put(int.class.getCanonicalName(), converter);
		this.converterMap.put(Integer.class.getCanonicalName(), converter);
		
		converter = new LongConverter();
		this.converterMap.put(long.class.getCanonicalName(), converter);
		this.converterMap.put(Long.class.getCanonicalName(), converter);
				
		converter = new FloatConverter();
		this.converterMap.put(float.class.getCanonicalName(), converter);
		this.converterMap.put(Float.class.getCanonicalName(), converter);

		converter = new DoubleConverter();
		this.converterMap.put(double.class.getCanonicalName(), converter);
		this.converterMap.put(Double.class.getCanonicalName(), converter);

		converter = new BooleanConverter();
		this.converterMap.put(boolean.class.getCanonicalName(), converter);
		this.converterMap.put(Boolean.class.getCanonicalName(), converter);

		converter = new CharConverter();
		this.converterMap.put(char.class.getCanonicalName(), converter);
		this.converterMap.put(Character.class.getCanonicalName(), converter);

		converter = new StringConverter();
		this.converterMap.put(String.class.getCanonicalName(), converter);
	}
	
	/**
	 * Mapeia o conversor para o tipo informado. 
	 */
	public void addConverter(String type, Converter<?> converter)
	{
		this.converterMap.put(type, converter);
	}
	
	/**
	 * Conversao a string para um objeto do tipo informado.
	 */
	public Object convertFromString(String type, String value)
	{
		return this.findConverter(type).fromString(value);
	}

	/**
	 * Converte o objeto do tipo informado para sua representacao em string.
	 */
	@SuppressWarnings("unchecked")
	public String convertToString(String type, Object value)
	{
		return ((Converter<Object>) this.findConverter(type)).toString(value);
	}
	
	/**
	 * Retorna o conversor compativel com o tipo informado.
	 */
	private Converter<?> findConverter(String type)
	{
		// tenta obter o conversor para o tipo informado
		Converter<?> converter = this.converterMap.get(type);
		
		// verifica se encontrou um conversor compativel
		if (converter == null )
			converter = new NullConverter();
		
		return converter;
	}	
	
	
	/**
	 * Conversor para byte.
	 */
	private class ByteConverter implements Converter<Byte>
	{
		@Override
		public String toString(Byte obj)
		{
			return obj.toString();
		}

		@Override
		public Byte fromString(String value)
		{
			return Byte.valueOf(value);
		}
	}
	
	/**
	 * Conversor para short.
	 */
	private class ShortConverter implements Converter<Short>
	{
		@Override
		public String toString(Short obj)
		{
			return obj.toString();
		}

		@Override
		public Short fromString(String value)
		{
			return Short.valueOf(value);
		}
	}

	
	/**
	 * Conversor para inteiros.
	 */
	private class IntConverter implements Converter<Integer>
	{
		@Override
		public String toString(Integer obj)
		{
			return obj.toString();
		}

		@Override
		public Integer fromString(String value)
		{
			return Integer.valueOf(value);
		}
	}

	/**
	 * Conversor para long.
	 */
	private class LongConverter implements Converter<Long>
	{
		@Override
		public String toString(Long obj)
		{
			return obj.toString();
		}

		@Override
		public Long fromString(String value)
		{
			return Long.valueOf(value);
		}
	}
	
	/**
	 * Conversor para float.
	 */
	private class FloatConverter implements Converter<Float>
	{
		@Override
		public String toString(Float obj)
		{
			return obj.toString();
		}

		@Override
		public Float fromString(String value)
		{
			return Float.valueOf(value);
		}
	}
	
	/**
	 * Conversor para double.
	 */
	private class DoubleConverter implements Converter<Double>
	{
		@Override
		public String toString(Double obj)
		{
			return obj.toString();
		}

		@Override
		public Double fromString(String value)
		{
			return Double.valueOf(value);
		}
	}

	/**
	 * Conversor para boolean.
	 */
	private class BooleanConverter implements Converter<Boolean>
	{
		@Override
		public String toString(Boolean obj)
		{
			return obj.toString();
		}

		@Override
		public Boolean fromString(String value)
		{
			return Boolean.valueOf(value);
		}
	}

	/**
	 * Conversor para char.
	 */
	private class CharConverter implements Converter<Character>
	{
		@Override
		public String toString(Character obj)
		{
			return obj.toString();
		}

		@Override
		public Character fromString(String value)
		{
			return value.charAt(0);
		}
	}

	
	/**
	 * Conversor para string.
	 */
	private class StringConverter implements Converter<String>
	{
		@Override
		public String toString(String obj)
		{
			return obj;
		}

		@Override
		public String fromString(String value)
		{
			return value;
		}
	}
	
	/**
	 * Conversor generico.
	 */
	private class NullConverter implements Converter<Object>
	{
		@Override
		public String toString(Object obj)
		{
			return obj.toString();
		}

		@Override
		public Object fromString(String value)
		{
			return value;
		}
	}
}
