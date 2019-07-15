package devframework.domain;

import java.util.List;

/**
 * Descritor de classes.
 */
public class ClassDescriptor {
	// nome da classe
	private String name;

	// nome qualificado da classe (pacote + nomeclasse)
	private String qualifiedName;

	// objeto Class
	private transient Class<?> classClass;

	private List<MethodDescriptor> methods;
	
	/**
	 * Construtor padrao (para reflexao / demais frameworks)
	 */
	public ClassDescriptor() {
	}

	public ClassDescriptor(Class<?> clazz) {
		this.name = clazz.getSimpleName();
		this.qualifiedName = clazz.getCanonicalName();
		this.classClass = clazz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

	public Class<?> getClassClass() {
		return classClass;
	}

	public void setClassClass(Class<?> clazz) {
		this.classClass = clazz;
	}

	public List<MethodDescriptor> getMethods() {
		return methods;
	}
	
	public void addMethods(MethodDescriptor method) {
		this.methods.add(method);
	}
	
	public void setMethods(List<MethodDescriptor> methods) {
		this.methods = methods;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((qualifiedName == null) ? 0 : qualifiedName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassDescriptor other = (ClassDescriptor) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (qualifiedName == null) {
			if (other.qualifiedName != null)
				return false;
		} else if (!qualifiedName.equals(other.qualifiedName))
			return false;
		return true;
	}
}
