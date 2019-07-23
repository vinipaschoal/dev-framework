package org.esfinge.virtuallab.annotations.container;

import java.lang.reflect.Method;

import org.esfinge.virtuallab.annotations.HtmlTableReturn;
import org.esfinge.virtuallab.annotations.JsonReturn;
import org.esfinge.virtuallab.annotations.ServiceMethod;

import net.sf.esfinge.metadata.annotation.container.AnnotationProperty;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ContainsAnnotation;
import net.sf.esfinge.metadata.annotation.container.ElementName;
import net.sf.esfinge.metadata.annotation.container.ReflectionReference;
import net.sf.esfinge.metadata.container.ContainerTarget;

@ContainerFor(ContainerTarget.METHODS)
public class MethodContainer {

	@ContainsAnnotation(ServiceMethod.class)
	private boolean temAnotacaoServiceMethod;
	
	@ContainsAnnotation(JsonReturn.class)
	private boolean temAnotacaoJsonReturn;

	@ContainsAnnotation(HtmlTableReturn.class)
	private boolean temAnotacaoHtmlTableReturn;
	
	@ElementName
	private String nomeMethod;
	
	@ReflectionReference
	private Method method;

	@AnnotationProperty(annotation = ServiceMethod.class, property = "alias")
	private String aliasMethod;

	public boolean isTemAnotacaoServiceMethod() {
		return temAnotacaoServiceMethod;
	}

	public void setTemAnotacaoServiceMethod(boolean temAnotacaoServiceMethod) {
		this.temAnotacaoServiceMethod = temAnotacaoServiceMethod;
	}
	
	public String getNomeMethod() {
		return nomeMethod;
	}

	public void setNomeMethod(String nomeMethod) {
		this.nomeMethod = nomeMethod;
	}

	public String getAliasMethod() {
		return aliasMethod;
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

}
