package devframework.validation;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

@SuppressWarnings("rawtypes")
public final class ClassValidationService {

	private static final String SERVICE_METHOD_ANNOTATION_NAME = "ServiceMethod";
	private static final String SERVICE_CLASS_ANNOTATION_NAME = "ServiceClass";
	private ClassLoader classLoader;
	private static ClassValidationService instance;

	public synchronized static ClassValidationService getInstance() {
		if (instance == null) {
			instance = new ClassValidationService();
		}
		return instance;
	}

	public <T> Class isClassValid(String filePath, String className)
			throws ClassNotFoundException, MalformedURLException {
		configureClassLoader(filePath);

		Class<T> clazz = (Class<T>) classLoader.loadClass(className);

		if (isClassContainsAnnotation(SERVICE_CLASS_ANNOTATION_NAME, clazz)
				&& isClassContainsAnnotationInSomeMethod(SERVICE_METHOD_ANNOTATION_NAME, clazz)) {
			return clazz;
		} else {
			return null;
		}
	}

	private void configureClassLoader(String filename) throws MalformedURLException {
		File file = new File(filename);
		URL url = file.toURI().toURL();
		URL[] urls = new URL[] { url };

		classLoader = new URLClassLoader(urls, ClassValidationService.class.getClassLoader());
	}

	private boolean isClassContainsAnnotation(String annotationName, Class clazz) {
		Annotation[] annotations = clazz.getDeclaredAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().getName().contains(annotationName)) {
				return true;
			}
		}

		return false;
	}

	private boolean isClassContainsAnnotationInSomeMethod(String annotationName, Class clazz) {
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			Annotation[] annotations = method.getDeclaredAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation.annotationType().getName().contains(annotationName)) {
					return true;
				}
			}
		}

		return false;
	}

}
