package org.esfinge.virtuallab.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.descriptors.ParameterDescriptor;

/**
 * Metodos utilitarios para trabalhar com reflexao.
 */
public class ReflectionUtils
{
	// mapa das classes basicas (primitivos)
	private static Map<String, Class<?>> basicTypesMap;
	
	static {
		// mapeia as classes dos tipos basicos
		basicTypesMap = new HashMap<>();
		
		// cadastra as classes dos tipos basicos
		basicTypesMap.put(byte.class.getCanonicalName(), byte.class);
		basicTypesMap.put(Byte.class.getCanonicalName(), Byte.class);
		basicTypesMap.put(short.class.getCanonicalName(), short.class);
		basicTypesMap.put(Short.class.getCanonicalName(), Short.class);
		basicTypesMap.put(int.class.getCanonicalName(), int.class);
		basicTypesMap.put(Integer.class.getCanonicalName(), Integer.class);
		basicTypesMap.put(long.class.getCanonicalName(), long.class);
		basicTypesMap.put(Long.class.getCanonicalName(), Long.class);
		basicTypesMap.put(float.class.getCanonicalName(), float.class);
		basicTypesMap.put(Float.class.getCanonicalName(), Float.class);
		basicTypesMap.put(double.class.getCanonicalName(), double.class);
		basicTypesMap.put(Double.class.getCanonicalName(), Double.class);
		basicTypesMap.put(boolean.class.getCanonicalName(), boolean.class);
		basicTypesMap.put(Boolean.class.getCanonicalName(), Boolean.class);
		basicTypesMap.put(char.class.getCanonicalName(), char.class);
		basicTypesMap.put(Character.class.getCanonicalName(), Character.class);
		basicTypesMap.put(String.class.getCanonicalName(), String.class);
	}

	/**
	 * Retorna o metodo correspondente ao descritor informado.
	 */
	public static Method getMethod(MethodDescriptor methodDescriptor) throws Exception
	{
		// obtem a classe que contem o metodo
		Class<?> clazz = findClass(methodDescriptor.getClassName());
		
		// procura na classe o metodo que sera invocado
		for (Method m : clazz.getMethods())
			if ( m.getName().equals(methodDescriptor.getName()) && 
				 m.getParameterCount() == methodDescriptor.getParameters().size() )
			{
				// verifica se a ordem e os tipos dos parametros sao iguais
				Class<?>[] paramTypes = m.getParameterTypes();
				boolean found = true;
				for ( ParameterDescriptor p : methodDescriptor.getParameters() )
					found &= compareDataTypes(findClass(p.getDataType()), paramTypes[p.getIndex()]);
						
				// encontrou o metodo?
				if ( found )
					return m;
			}
		
		// nao encontrou o metodo!
		throw new IllegalArgumentException("Metodo '" + methodDescriptor.getName() + "' não encontrado na clase '" + clazz.getCanonicalName());
	}
	
	/**
	 * Verifica se os tipos informados sao iguais, considerando wrappers e primitivos.
	 */
	private static boolean compareDataTypes(Class<?> typeA, Class<?> typeB)
	{
		if ( ClassUtils.isPrimitiveOrWrapper(typeA) && ClassUtils.isPrimitiveOrWrapper(typeB) )
		{
			return ( typeA.isPrimitive() ? typeA : ClassUtils.wrapperToPrimitive(typeA) ) == 
				   ( typeB.isPrimitive() ? typeB : ClassUtils.wrapperToPrimitive(typeB) );
		}
		
		return typeA == typeB;
	}
	
	/**
	 * Retorna todos os campos da classe, incluindo os das superclasses.
	 */
	public static List<Field> getAllFields(Class<?> clazz)
	{
		return FieldUtils.getAllFieldsList(clazz);
	}
	
	/**
	 * Retorna todos os campos da classe, nao incluindo os das superclasses.
	 */
	public static List<Field> getDeclaredFields(Class<?> clazz)
	{
		return Arrays.asList(clazz.getDeclaredFields());
	}
	
	/**
	 *  Verifica se a classe possui o campo informado.
	 */
	public static boolean hasField(Class<?> clazz, String fieldName)
	{
		try
		{
			return getField(clazz, fieldName) != null; 
		}
		catch ( Exception e )
		{
			return false;
		}
	}
	
	public static Field getField(Class<?> clazz, String fieldName) throws Exception
	{
		// procura inclusive nas superclasses
		return FieldUtils.getField(clazz, fieldName, true);
	}
	
	/**
	 * Retorna o valor do campo especificado.
	 */
	public static Object getFieldValue(Object obj, String fieldName) throws Exception
	{
		// tenta ler o campo
		return FieldUtils.readField(obj, fieldName, true);	
	}
	
	/**
	 * Atribui o valor ao campo especificado.
	 */
	public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception
	{
		FieldUtils.writeField(obj, fieldName, value, true);
	}
	
	/**
	 * Retorna todos os campos da classe anotados com a anotacao informada, incluindo os das superclasses.
	 */
	public static List<Field> getAllFieldsAnnotatedWith(Class<?> clazz, Class<? extends Annotation> annotationClazz)
	{
		return getAllFields(clazz)
					.stream()
					.filter(f -> f.isAnnotationPresent(annotationClazz))
					.collect(Collectors.toList());
	}

	/**
	 * Retorna todos os campos da classe anotados com a anotacao informada, nao incluindo os das superclasses.
	 */
	public static List<Field> getDeclaredFieldsAnnotatedWith(Class<?> clazz, Class<? extends Annotation> annotationClazz)
	{
		return getDeclaredFields(clazz)
				.stream()
				.filter(f -> f.isAnnotationPresent(annotationClazz))
				.collect(Collectors.toList());
	}
	
	/**
	 * Retorna a classe pelo seu nome qualificado.
	 */
	public static Class<?> findClass(String classQualifiedName) throws ClassNotFoundException
	{
		if ( basicTypesMap.containsKey(classQualifiedName) )
			return basicTypesMap.get(classQualifiedName);
		
		return Class.forName(classQualifiedName);
	}
	
	/**
	 * Verifica se a classe eh do tipo primitivo ou seus wrappers 
	 * (boolean, byte, char, short, int, long, float, double) ou do tipo String.
	 */
	public static boolean isBasicType(Class<?> clazz)
	{
		return ( ClassUtils.isPrimitiveOrWrapper(clazz) || (clazz == String.class) );
	}
	
	/**
	 * Verifica se a classe eh do tipo colecao (List, Set, Queue, etc.)
	 */
	public static boolean isCollection(Class<?> clazz)
	{
		return Collection.class.isAssignableFrom(clazz);
	}

	/**
	 * Verifica se a classe eh um array.
	 */
	public static boolean isArray(Class<?> clazz)
	{
		return clazz.isArray();
	}
	
	/**
	 * Verifica se a classe eh uma objeto JSON valido.
	 * Regras:
	 * - ter um construtor padrao publico
	 * - possuir somente atributos basicos (primitivos/wrappers, String)
	 */
	public static boolean isValidJsonObject(Class<?> clazz)
	{
		try
		{
			// construtor padrao
			Object obj = clazz.newInstance();
			
			// atributos do tipo basico
			for (Field f : getAllFields(clazz) )
				if (! isBasicType(f.getType()) )
					return false;
			
			// serializavel pelo JsonUtils
			JsonUtils.fromObjectToJsonData(obj);
			
			return true;
		}
		catch (Exception e)
		{
			return false;
		}		
	}
}
