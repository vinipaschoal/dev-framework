package org.esfinge.virtuallab.annotations.container;

import java.lang.annotation.Annotation;
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
	private boolean temAnotacaoServiceMethod;

	@ContainsAnnotation(JsonReturn.class)
	private boolean temAnotacaoJsonReturn;

	@ContainsAnnotation(HtmlTableReturn.class)
	private boolean temAnotacaoHtmlTableReturn;

	@ContainsAnnotation(Label.class)
	private boolean temAnotacaoLabel;

	@ElementName
	private String nomeMethod;

	@ReflectionReference
	private Method method;

	@AnnotationProperty(annotation = ServiceMethod.class, property = "alias")
	private String aliasMethod;

	@AnnotationProperty(annotation = Label.class, property = "name")
	private String labelClass;

	public boolean isTemAnotacaoServiceMethod() {
		return temAnotacaoServiceMethod;
	}

	public void setTemAnotacaoServiceMethod(boolean temAnotacaoServiceMethod) {
		this.temAnotacaoServiceMethod = temAnotacaoServiceMethod;
	}

	public String getNomeMethod() {
		if (aliasMethod != null && !"".equals(aliasMethod)) {
			return aliasMethod;
		} else {
			return nomeMethod;
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

	public boolean isTemAnotacaoJsonReturn() {
		return temAnotacaoJsonReturn;
	}

	public void setTemAnotacaoJsonReturn(boolean temAnotacaoJsonReturn) {
		this.temAnotacaoJsonReturn = temAnotacaoJsonReturn;
	}

	public boolean isTemAnotacaoHtmlTableReturn() {
		return temAnotacaoHtmlTableReturn;
	}

	public void setTemAnotacaoHtmlTableReturn(boolean temAnotacaoHtmlTableReturn) {
		this.temAnotacaoHtmlTableReturn = temAnotacaoHtmlTableReturn;
	}

	public void setNomeMethod(String nomeMethod) {
		this.nomeMethod = nomeMethod;
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

	public void setTemAnotacaoLabel(boolean temAnotacaoLabel) {
		this.temAnotacaoLabel = temAnotacaoLabel;
	}

	public void setLabelClass(String labelClass) {
		this.labelClass = labelClass;
	}

	public String getLabeledMethodName() {
		if (temAnotacaoLabel) {
			return labelClass;
		} else {
			return nomeMethod;
		}
	}

}
