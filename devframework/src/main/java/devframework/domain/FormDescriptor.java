package devframework.domain;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class FormDescriptor {

	private List<FieldDescriptor> fields;
	
	public FormDescriptor(Method method) {
		
		this.fields = new ArrayList<FieldDescriptor>();
		
		for (Parameter parameter : method.getParameters()) {
			
			FieldDescriptor field = new FieldDescriptor();
			
			AttributeDescriptor attribute = new AttributeDescriptor(parameter.getType().getSimpleName());
			attribute.setTitle(parameter.getName());
			
			field.setName(parameter.getName());
			field.setAttribute(attribute);
			
			this.AddField(field);
			
		}
		
	}
	
	public List<FieldDescriptor> getFields(){
		return this.fields;
	}
	
	public void setFields(List<FieldDescriptor> fields){
		this.fields = fields;
	}
	
	public void AddField(FieldDescriptor field){
		this.fields.add(field);
	}
	
}
