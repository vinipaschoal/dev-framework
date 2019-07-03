package devframework.utils;

import java.lang.reflect.Method;

import org.apache.bcel.classfile.JavaClass;

import devframework.servlet.FrontControllerServlet;

/**
 * Carregador de classes.
 */
public class ClassLoaderUtils
{
	// metodo para carregamento de classes do ClassLoader do sistema
	private Method defineClassMethod;
	
	// Singleton
	private static ClassLoaderUtils _instance;
	
	
	public static ClassLoaderUtils getInstance()
	{
		if ( _instance == null )
			_instance = new ClassLoaderUtils();
		
		return _instance;
	}	
	
	/**
	 * Cria um novo carregador de classes.
	 */
	private ClassLoaderUtils()
	{
		try
		{
			this.defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
			this.defineClassMethod.setAccessible(true);
		}
		catch (NoSuchMethodException | SecurityException e)
		{
			// TODO: debug..
			System.out.println("CLASS LOADER >> Erro ao recuperar o metodo 'ClassLoader.defineClass()'!");
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Carrega a classe.
	 */
	public Class<?> loadClass(String classPath) throws Exception
	{
		JavaClass javaClass = Utils.getInstance().getClassInfo(classPath);
		
		try
		{
			// verifica se a classe ja esta carregada
			 return Class.forName(javaClass.getClassName());
		}
		catch ( ClassNotFoundException e )
		{
			// tenta carregar a classe
			byte[] classBytecodes = javaClass.getBytes();
			return (Class<?>) this.defineClassMethod.invoke(FrontControllerServlet.class.getClassLoader(),
					javaClass.getClassName(), classBytecodes, 0, classBytecodes.length);
		}
	}
}
