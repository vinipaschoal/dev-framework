package devframework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

/**
 * Classe com metodos utilitarios para a aplicacao.
 *
 */
public class Utils
{
	// Singleton
	private static Utils _instance;
	
	// arquivo de propriedades
	private Properties properties;
	
	
	/**
	 * Retorna a instancia da classe utilitaria.
	 */
	public static Utils getInstance()
	{
		if ( _instance == null )
			_instance = new Utils();
		
		return _instance;
	}
	
	/**
	 * Construtor privado.
	 */
	private Utils()
	{
		try
		{
			// carrega o arquivo de propriedades
			this.properties = new Properties();
			this.properties.load(this.getClass().getClassLoader().getResourceAsStream("devframework.properties"));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Retorna o valor da chave especificada no arquivo de propriedades, ou o valor default especificado 
	 * caso o a chave nao seja encontrada. 
	 */
	public String getProperty(String prop, String defaultValue)
	{
		return this.properties.getProperty(prop, defaultValue);
	}

	/**
	 * Retorna o valor da chave especificada no arquivo de propriedades, ou o valor default especificado 
	 * caso o a chave nao seja encontrada. 
	 */
	public int getPropertyAsInt(String prop, int defaultValue)
	{
		try
		{
			return Integer.parseInt(this.properties.getProperty(prop));
		}
		catch ( Exception e)
		{
			return defaultValue;
		}
	}
	
	public JavaClass getClassInfo(String classPath) throws ClassFormatException, IOException
	{
		return new ClassParser(classPath).parse();
	}
	
	public JavaClass getClassInfo(InputStream inputStream, String fileName) throws ClassFormatException, IOException
	{
		return new ClassParser(inputStream, fileName).parse();
	}
	
	public void setProperty(String prop, String defaultValue)
	{
		this.properties.setProperty(prop, defaultValue);
	}
}
