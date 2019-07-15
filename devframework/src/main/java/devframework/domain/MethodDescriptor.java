package devframework.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Descritor de Médotos.
 */
public class MethodDescriptor {
	
	private String name;
	
	private List<ParameterDescriptor> parameters;
	
	public MethodDescriptor() {
        this.parameters = new ArrayList<ParameterDescriptor>();
    }
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
