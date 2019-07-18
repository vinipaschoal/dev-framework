package devframework.domain;

public class AttributeDescriptor {

	private String type;
	
	private String title;
	
	private Boolean required;
	
	private int maxLength;
	
	public AttributeDescriptor(String type) {
		this.setRequired(true);
		this.setType(type);
		this.setMaxLength();
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = FieldType(type);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	public void setMaxLength() {
		this.maxLength = 1000;
		if (this.type == "char") this.maxLength = 1;
	}	
	
	private String FieldType(String parameterType) {
		
		String fieldType = "";
		switch(parameterType.toLowerCase()) {
			case "byte": case "short": case "int": case "long": case "float": case "double":
				fieldType = "number";
				break;
			case "boolean":
				fieldType = "boolean";
				break;
			case "char": case "string":
				fieldType = "string";
				break;
			default:
				fieldType = "string";
		}
		
		return fieldType;

	}
	
}
