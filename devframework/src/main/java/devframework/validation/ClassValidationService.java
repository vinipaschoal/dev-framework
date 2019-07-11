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
		return isClassValid(filePath + File.separator + className);
	}

	public Class<?> isClassValid(String filePath) throws Exception {
		Class<?> clazz = ClassLoaderUtils.getInstance().loadClass(filePath);
		if (clazz.isAnnotationPresent(ServiceClass.class)) {
			for (Method m : clazz.getMethods()) {
				if (m.isAnnotationPresent(ServiceMethod.class))
					return clazz;
			}
		}
		return null;
	}

	public boolean isAnnotationPresent(Method method, Class annotation) throws Exception {
		return method.isAnnotationPresent(annotation);
	}

	public String getAliasFromServiceMethod(Method method) throws Exception {
		ServiceMethod annotation = method.getAnnotation(ServiceMethod.class);
		return annotation.alias();
	}
}
