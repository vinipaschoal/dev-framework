package org.esfinge.virtuallab.services;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.esfinge.virtuallab.utils.Utils;
import org.esfinge.virtuallab.web.FrontControllerServlet;

/**
 * Carregador de classes.
 */
public class ClassLoaderService
{
	// metodo para carregamento de classes do ClassLoader do sistema
	private Method defineClassMethod;

	// instancia unica da classe
	private static ClassLoaderService _instance;

	/**
	 * Singleton.
	 */
	public static ClassLoaderService getInstance()
	{
		if (_instance == null)
			_instance = new ClassLoaderService();

		return _instance;
	}

	/**
	 * Cria um novo carregador de classes.
	 */
	private ClassLoaderService()
	{
		try
		{
			// tenta acessar o metodo de carregar classes do ClassLoader do sistema
			this.defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class,
					int.class, int.class);
			this.defineClassMethod.setAccessible(true);
		}
		catch (NoSuchMethodException | SecurityException e)
		{
			// TODO: debug..
			System.out.println("CLASS LOADER >> Erro ao acessar metodos protegidos do ClassLoader do sistema!");

			e.printStackTrace();
		}
	}

	/**
	 * Carrega a classe.
	 */
	public Class<?> loadClass(String classFilePath) throws Exception
	{
		return this.loadClass(FileUtils.openInputStream(new File(classFilePath)), classFilePath);
	}

	/**
	 * Carrega a classe.
	 */
	public Class<?> loadClass(InputStream classFileStream, String className) throws Exception
	{
		return this.loadClassInternal(
				new ClassParser(classFileStream, this.fixClassName(FilenameUtils.getName(className))).parse());
	}

	/**
	 * Carrega a classe de um arquivo Jar.
	 */
	public Class<?> loadClassFromJar(String jarFilePath, String classQualifiedName) throws Exception
	{
		try (
				// abre o arquivo jar
				JarFile jarFile = new JarFile(jarFilePath);)
		{
			// corrige para o nome qualificado da classe
			String className = this.fixClassName(classQualifiedName);

			// obtem a entrada para a classe a ser carregada
			JarEntry jarEntry = Utils.getFromCollection(Collections.list(jarFile.entries()),
					je -> !je.isDirectory() && this.fixClassName(je.getName()).equals(className));

			// encontrou a classe, carrega
			if (jarEntry != null)
				return this.loadClass(jarFile.getInputStream(jarEntry), className);

			// nao encontrou a classe
			return null;
		}
	}

	/**
	 * Carrega a classe de um arquivo Jar.
	 */
	public Class<?> loadClassFromJar(InputStream jarFileStream, String classQualifiedName) throws Exception
	{
		// cria um arquivo temporario para carregar o jar
		File tempJarFile = File.createTempFile("tempJar", ".tmp");
		FileUtils.copyToFile(jarFileStream, tempJarFile);

		try
		{
			return this.loadClassFromJar(tempJarFile.getAbsolutePath(), classQualifiedName);
		}
		finally
		{
			// apaga o arquivo temporario
			FileUtils.deleteQuietly(tempJarFile);
		}
	}

	/**
	 * Carrega as classes do arquivo Jar.
	 */
	public List<Class<?>> loadJar(String jarFilePath) throws Exception
	{
		try (
				// abre o arquivo jar
				JarFile jarFile = new JarFile(jarFilePath);)
		{
			// lista das classes contidas no jar
			List<Class<?>> classList = new ArrayList<Class<?>>();

			// obtem a entrada para a classe a ser carregada
			Enumeration<JarEntry> jarEntries = jarFile.entries();
			while (jarEntries.hasMoreElements())
			{
				JarEntry jarEntry = jarEntries.nextElement();

				// verifica se eh uma classe
				if (jarEntry.isDirectory() || !FilenameUtils.isExtension(jarEntry.getName(), "class"))
					continue;

				// encontrou uma classe
				Class<?> clazz = this.loadClass(jarFile.getInputStream(jarEntry),
						this.fixClassName(jarEntry.getName()));
				classList.add(clazz);
			}

			// verifica se encontrou alguma classe
			return classList.size() == 0 ? null : classList;
		}
	}

	/**
	 * Carrega as classes do arquivo Jar.
	 */
	public List<Class<?>> loadJar(InputStream jarFileStream) throws Exception
	{
		// cria um arquivo temporario para carregar o jar
		File tempJarFile = File.createTempFile("tempJar", ".tmp");
		FileUtils.copyToFile(jarFileStream, tempJarFile);

		try
		{
			return this.loadJar(tempJarFile.getAbsolutePath());
		}
		finally
		{
			// apaga o arquivo temporario
			FileUtils.deleteQuietly(tempJarFile);
		}
	}

	/**
	 * Carrega a classe.
	 */
	private Class<?> loadClassInternal(JavaClass javaClass) throws Exception
	{
		try
		{
			// verifica se a classe ja esta carregada
			return Class.forName(javaClass.getClassName());
		}
		catch (ClassNotFoundException e)
		{
			// tenta carregar a classe
			byte[] classBytecodes = javaClass.getBytes();
			return (Class<?>) this.defineClassMethod.invoke(FrontControllerServlet.class.getClassLoader(),
					javaClass.getClassName(), classBytecodes, 0, classBytecodes.length);
		}
	}

	/**
	 * Verifica o formato do nome da classe e corrige para o padrao se necessario.
	 */
	private String fixClassName(String className)
	{
		// troca os separadores por .
		if (FilenameUtils.isExtension(className, "class"))
			className = FilenameUtils.removeExtension(className);

		// retorna o nome padronizado com a extensao .class
		return className.replaceAll("[\\\\/]", ".") + ".class";
	}
}
