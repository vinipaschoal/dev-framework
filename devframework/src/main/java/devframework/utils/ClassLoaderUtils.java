package devframework.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import devframework.servlet.FrontControllerServlet;

/**
 * Carregador de classes.
 */
public class ClassLoaderUtils {
	// metodo para carregamento de classes do ClassLoader do sistema
	private Method defineClassMethod;

	// metodo para carregamento de jars do ClassLoader do sistema
	private Method addUrlMethod;

	// instancia unica da classe
	private static ClassLoaderUtils _instance;

	/**
	 * Singleton.
	 */
	public static ClassLoaderUtils getInstance() {
		if (_instance == null)
			_instance = new ClassLoaderUtils();

		return _instance;
	}

	/**
	 * Cria um novo carregador de classes.
	 */
	private ClassLoaderUtils() {
		try {
			this.defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class,
					int.class, int.class);
			this.defineClassMethod.setAccessible(true);

			this.addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
			this.addUrlMethod.setAccessible(true);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO: debug..
			System.out.println("CLASS LOADER >> Erro ao acessar metodos protegidos do ClassLoader do sistema!");

			e.printStackTrace();
		}
	}

	/**
	 * Carrega a classe.
	 */
	public Class<?> loadClass(String classFilePath) throws Exception {
		return this.loadClass(FileUtils.openInputStream(new File(classFilePath)), classFilePath);
	}

	/**
	 * Carrega a classe.
	 */
	public Class<?> loadClass(InputStream classFileStream, String className) throws Exception {
		return this.loadClassInternal(
				new ClassParser(classFileStream, this.fixClassName(FilenameUtils.getName(className))).parse());
	}

	/**
	 * Carrega a classe de um arquivo Jar.
	 */
	public Class<?> loadClassFromJar(String jarFilePath, String classQualifiedName) throws Exception {
		try (
				// abre o arquivo jar
				JarFile jarFile = new JarFile(jarFilePath);) {
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
	public Class<?> loadClassFromJar(InputStream jarFileStream, String classQualifiedName) throws Exception {
		// cria um arquivo temporario para carregar o jar
		File tempJarFile = File.createTempFile("tempJar", ".tmp");
		FileUtils.copyToFile(jarFileStream, tempJarFile);

		try {
			return this.loadClassFromJar(tempJarFile.getAbsolutePath(), classQualifiedName);
		} finally {
			// apaga o arquivo temporario
			FileUtils.deleteQuietly(tempJarFile);
		}
	}

	/**
	 * Carrega as classes do arquivo Jar.
	 */
	public List<Class<?>> loadJar(String jarFilePath) throws Exception {
		try (
				// abre o arquivo jar
				JarFile jarFile = new JarFile(jarFilePath);) {
			// lista das classes contidas no jar
			List<Class<?>> classList = new ArrayList<Class<?>>();

			// obtem a entrada para a classe a ser carregada
			Enumeration<JarEntry> jarEntries = jarFile.entries();
			while (jarEntries.hasMoreElements()) {
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
	public List<Class<?>> loadJar(InputStream jarFileStream) throws Exception {
		// cria um arquivo temporario para carregar o jar
		File tempJarFile = File.createTempFile("tempJar", ".tmp");
		FileUtils.copyToFile(jarFileStream, tempJarFile);

		try {
			return this.loadJar(tempJarFile.getAbsolutePath());
		} finally {
			// apaga o arquivo temporario
			FileUtils.deleteQuietly(tempJarFile);
		}
	}

	public Map<String, byte[]> getInputStreamClassesFromJar(String jarPath) throws IOException {
		Map<String, byte[]> inputs = new HashMap<String, byte[]>();
		try (JarFile jarFile = new JarFile(jarPath);) {
			Enumeration<JarEntry> jarEntries = jarFile.entries();
			while (jarEntries.hasMoreElements()) {
				JarEntry jarEntry = jarEntries.nextElement();
				if (jarEntry.isDirectory() || !FilenameUtils.isExtension(jarEntry.getName(), "class")) {
					continue;
				}
				inputs.put(this.fixClassName(jarEntry.getName()),
						IOUtils.toByteArray(jarFile.getInputStream(jarEntry)));
			}
			return inputs;
		}
	}

	/**
	 * Carrega a classe.
	 */
	private Class<?> loadClassInternal(JavaClass javaClass) throws Exception {
		try {
			// verifica se a classe ja esta carregada
			return Class.forName(javaClass.getClassName());
		} catch (ClassNotFoundException e) {
			// tenta carregar a classe
			byte[] classBytecodes = javaClass.getBytes();
			return (Class<?>) this.defineClassMethod.invoke(FrontControllerServlet.class.getClassLoader(),
					javaClass.getClassName(), classBytecodes, 0, classBytecodes.length);
		}
	}

	/**
	 * Verifica o formato do nome da classe e corrige para o padrao se necessario.
	 */
	private String fixClassName(String className) {
		// troca os separadores por .
		if (FilenameUtils.isExtension(className, "class"))
			className = FilenameUtils.removeExtension(className);

		// retorna o nome padronizado com a extensao .class
		return className.replaceAll("[\\\\/]", ".") + ".class";
	}

	public void loadAll() throws Exception {
		List<Class<?>> classesList = new ArrayList<>();
		Collection<File> files = FileUtils.listFiles(new File(Utils.getInstance().getUploadDir()),
				new String[] { "class", "jar" }, true);
		for (File file : files) {
			String filePath = file.getAbsolutePath();
			if (FilenameUtils.isExtension(filePath, "class")) {
				ClassLoaderUtils.getInstance().loadClass(file.getAbsolutePath());
			} else {
				ClassLoaderUtils.getInstance().loadJar(file.getAbsolutePath());
			}

		}
	}
}
