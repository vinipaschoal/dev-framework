package org.esfinge.virtuallab.annotations.container;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.esfinge.virtuallab.annotations.HtmlTableReturn;
import org.esfinge.virtuallab.annotations.JsonReturn;
import org.esfinge.virtuallab.annotations.Label;
import org.esfinge.virtuallab.annotations.ServiceMethod;
import org.springframework.core.DefaultParameterNameDiscoverer;

import net.sf.esfinge.metadata.annotation.container.AnnotationProperty;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ContainsAnnotation;
import net.sf.esfinge.metadata.annotation.container.ElementName;
import net.sf.esfinge.metadata.annotation.container.ReflectionReference;
import net.sf.esfinge.metadata.container.ContainerTarget;

@ContainerFor(ContainerTarget.METHODS)
public class MethodContainer implements IContainer {

	@ContainsAnnotation(ServiceMethod.class)
	private boolean annotatedWithServiceMethod;

	@ContainsAnnotation(JsonReturn.class)
	private boolean annotatedWithJsonReturn;

	@ContainsAnnotation(HtmlTableReturn.class)
	private boolean annotatedWithHtmlTableReturn;

	@ContainsAnnotation(Label.class)
	private boolean annotatedWithLabel;

	@ElementName
	private String methodName;

	@ReflectionReference
	private Method method;

	@AnnotationProperty(annotation = ServiceMethod.class, property = "alias")
	private String aliasMethod;

	@AnnotationProperty(annotation = Label.class, property = "name")
	private String labelMethod;

	public boolean isAnnotatedWithServiceMethod() {
		return annotatedWithServiceMethod;
	}

	public void setAnnotatedWithServiceMethod(boolean annotatedWithServiceMethod) {
		this.annotatedWithServiceMethod = annotatedWithServiceMethod;
	}

	public String getMethodName() {
		if (aliasMethod != null && !"".equals(aliasMethod)) {
			return aliasMethod;
		} else {
			return methodName;
		}
	}

	public void setAliasMethod(String aliasMethod) {
		this.aliasMethod = aliasMethod;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public boolean isAnnotatedWithJsonReturn() {
		return annotatedWithJsonReturn;
	}

	public void setAnnotatedWithJsonReturn(boolean annotatedWithJsonReturn) {
		this.annotatedWithJsonReturn = annotatedWithJsonReturn;
	}

	public boolean isAnnotatedWithHtmlTableReturn() {
		return annotatedWithHtmlTableReturn;
	}

	public void setAnnotatedWithHtmlTableReturn(boolean annotatedWithHtmlTableReturn) {
		this.annotatedWithHtmlTableReturn = annotatedWithHtmlTableReturn;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<String> getLabeledParameterNames() {
		DefaultParameterNameDiscoverer defaultParameterNameDiscoverer = new DefaultParameterNameDiscoverer();
		String[] parameterNames = defaultParameterNameDiscoverer.getParameterNames(method);		
		List<String> labeledAndNamed = new ArrayList<String>();
		for (int i = 0; i < parameterNames.length; i++) {
			Parameter parameter = method.getParameters()[i];
			if(parameter.isAnnotationPresent(Label.class)) {
				Label annotation = parameter.getAnnotation(Label.class);
				labeledAndNamed.add(annotation.name());
			}else {
				labeledAndNamed.add(parameterNames[i]);
			}
		}
		
		return labeledAndNamed;
	}

	public Class<?> getReturnType() {
		return method.getReturnType();
	}

	public int getNumberOfParameters() {
		return method.getParameterCount();
	}

	public void setAnnotatedWithLabel(boolean annotatedWithLabel) {
		this.annotatedWithLabel = annotatedWithLabel;
	}

	public void setLabelMethod(String labelMethod) {
		this.labelMethod = labelMethod;
	}

	public String getLabeledMethodName() {
		if (annotatedWithLabel) {
			return labelMethod;
		} else {
			return methodName;
		}
	}

}
