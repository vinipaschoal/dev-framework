package devframework.services;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import devframework.annotations.ServiceClass;
import devframework.annotations.ServiceMethod;
import devframework.utils.ClassLoaderUtils;

/**
 * Validator de classes.
 */
public final class ClassValidationService {
	// instancia unica da classe
	private static ClassValidationService _instance;

	/**
	 * Construtor interno.
	 */
	private ClassValidationService() {
	}

	/**
	 * Singleton.
	 */
	public synchronized static ClassValidationService getInstance() {
		if (_instance == null)
			_instance = new ClassValidationService();

		return _instance;
	}

	/**
	 * Retorna a classe se ela for valida.
	 */
	public Class<?> isValidClass(String classFilePath) throws Exception {
		return this.isValidClass(FileUtils.openInputStream(new File(classFilePath)),
				FilenameUtils.getName(classFilePath));
	}

	/**
	 * Retorna a classe se ela for valida.
	 */
	public Class<?> isValidClass(InputStream classFileStream, String fileName) throws Exception {
		List<Class<?>> classList = this.isValidInternal(classFileStream, fileName);

		return (classList != null ? classList.get(0) : null);
	}

	/**
	 * Retorna as classes validas do arquivo jar.
	 */
	public List<Class<?>> isValidJar(String jarFilePath) throws Exception {
		return this.isValidJar(FileUtils.openInputStream(new File(jarFilePath)), FilenameUtils.getName(jarFilePath));
	}

	/**
	 * Retorna as classes validas do arquivo jar.
	 */
	public List<Class<?>> isValidJar(InputStream jarFileStream, String fileName) throws Exception {
		return this.isValidInternal(jarFileStream, fileName);
	}

	/**
	 * Carrega as classes validas.
	 */
	private List<Class<?>> isValidInternal(InputStream fileStream, String fileName) throws Exception {
		List<Class<?>> classList = new ArrayList<Class<?>>();
		List<Class<?>> validClasses = new ArrayList<Class<?>>();

		if (fileName.endsWith(".jar"))
			classList.addAll(ClassLoaderUtils.getInstance().loadJar(fileStream));
		else
			classList.add(ClassLoaderUtils.getInstance().loadClass(fileStream, fileName));

		for (Class<?> clazz : classList)
			if (clazz.isAnnotationPresent(ServiceClass.class))
				for (Method m : clazz.getMethods())
					if (m.isAnnotationPresent(ServiceMethod.class)) {
						validClasses.add(clazz);
						break;
					}

		return (validClasses.size() == 0 ? null : validClasses);
	}
	
	public boolean isAnnotationPresent(Method method, Class annotation) throws Exception {
		return method.isAnnotationPresent(annotation);
	}

	public String getAliasFromServiceMethod(Method method) throws Exception {
		ServiceMethod annotation = method.getAnnotation(ServiceMethod.class);
		return annotation.alias();
	}
}
