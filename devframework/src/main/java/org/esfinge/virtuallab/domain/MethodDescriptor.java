package org.esfinge.virtuallab.domain;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * Descritor de M�dotos.
 */
public class MethodDescriptor {
	
	private String name;
	
	private String returnType;
	
	private List<ParameterDescriptor> parameters;
	
	public MethodDescriptor() {
        this.parameters = new ArrayList<ParameterDescriptor>();
    }
	
	public MethodDescriptor(Parameter[] parameters) {
		this.parameters = new ArrayList<ParameterDescriptor>();
        for (Parameter parameter : parameters) {
			ParameterDescriptor parameterDescriptor = new ParameterDescriptor();
			parameterDescriptor.setName(parameter.getName());
			parameterDescriptor.setDataType(parameter.getType().getSimpleName());
			this.addParameter(parameterDescriptor);
		}
    }
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getReturnType() {
		return returnType;
	}
	
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	public List<ParameterDescriptor> getParameters() {
		return parameters;
	}
	
	public void addParameter(ParameterDescriptor parameter) {
		this.parameters.add(parameter);
	}
	
	public void setParameter(List<ParameterDescriptor> parameters) {
		this.parameters = parameters;
	}
	
}
