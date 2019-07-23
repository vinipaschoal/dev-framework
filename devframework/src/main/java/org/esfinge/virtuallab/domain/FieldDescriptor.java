package org.esfinge.virtuallab.domain;

public class FieldDescriptor {
	
	private String name;
	
	private AttributeDescriptor attribute;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public AttributeDescriptor getAttribute() {
		return attribute;
	}
	public void setAttribute(AttributeDescriptor attribute) {
		this.attribute = attribute;
	}

}
