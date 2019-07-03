package devframework.webservice;
/*
 * import java.lang.reflect.Method;
 * 
 * import devframework.validation.ClassValidationService;
 * 
 * public class Invoker extends ClassLoader {
 * 
 * public Object call(String className, String methodName) { try { className =
 * className.replace(".", "\\"); String filePath =
 * System.getenv("TOMCAT_FILES_DIR")+"\\upload\\"; className =
 * className.replaceAll("\\\\", "."); Class clazz =
 * ClassValidationService.getInstance().isClassValid(filePath, className);
 * if(clazz!=null){ Object instanceOfClass = clazz.newInstance(); Method method
 * = clazz.getMethod(methodName); Object response =
 * method.invoke(instanceOfClass); return response; } return null; } catch
 * (Exception e) { e.printStackTrace(); } return null; }
 * 
 * }
 */