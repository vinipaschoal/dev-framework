package org.esfinge.virtuallab.services;

import org.esfinge.virtuallab.annotations.container.ClassContainer;
import org.esfinge.virtuallab.annotations.container.ContainerFactory;
import org.esfinge.virtuallab.annotations.container.TypeContainer;

import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

public class CustomAnnotationIntrospector extends JacksonAnnotationIntrospector {

	@Override
	public boolean hasIgnoreMarker(AnnotatedMember m) {
		if (m instanceof AnnotatedField) {
			try {
				ClassContainer classContainer = ContainerFactory.create(((AnnotatedField) m).getDeclaringClass(),
						TypeContainer.CLASS_CONTAINER);
				if (classContainer.isAnnotatedWithTableStructure()
						&& !containsNameOnTableStructure(classContainer.getFieldsNameTableStructure(), m.getName())) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private boolean containsNameOnTableStructure(String[] nameFields, String name) {
		for (int i = 0; i < nameFields.length; i++) {
			if (nameFields[i].equals(name)) {
				return true;
			}
		}
		return false;
	}

}