package devframework.validation;

import java.io.File;
import java.lang.reflect.Method;

import devframework.annotations.ServiceClass;
import devframework.annotations.ServiceMethod;
import devframework.utils.ClassLoaderUtils;

public final class ClassValidationService {
	private static ClassValidationService instance;

	private ClassValidationService() {
	}

	public synchronized static ClassValidationService getInstance() {
		if (instance == null) {
			instance = new ClassValidationService();
		}
		return instance;
	}

	public Class<?> isClassValid(String filePath, String className) throws Exception {

		Class<?> clazz = ClassLoaderUtils.getInstance().loadClass(filePath + File.separator + className);
//		System.out.println("CLASSE: " + clazz.getName());
//		System.out.println("ANNOT: " + Arrays.toString(clazz.getAnnotations()));
//		System.out.println("METODOS: " + Arrays.toString(clazz.getMethods()));

		if (clazz.isAnnotationPresent(ServiceClass.class))
			for (Method m : clazz.getMethods())
				if (m.isAnnotationPresent(ServiceMethod.class))
					return clazz;

		throw new Exception("Classe invalida: " + className);
	}

	public Class<?> isClassValid(String filePath) throws Exception {
		Class<?> clazz = ClassLoaderUtils.getInstance().loadClass(filePath);
		if (clazz.isAnnotationPresent(ServiceClass.class))
			for (Method m : clazz.getMethods())
				if (m.isAnnotationPresent(ServiceMethod.class))
					return clazz;

		throw new Exception("Classe invalida: " + filePath);
	}

	public boolean isAnnotationPresent(Method method, Class annotation) throws Exception {
		return method.isAnnotationPresent(annotation);
	}
}
