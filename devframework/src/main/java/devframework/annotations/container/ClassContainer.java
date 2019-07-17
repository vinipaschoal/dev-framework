package devframework.annotations.container;

import java.util.List;

import devframework.annotations.ServiceClass;
import devframework.annotations.ServiceMethod;
import net.sf.esfinge.metadata.annotation.container.AllMethodsWith;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ContainsAnnotation;
import net.sf.esfinge.metadata.annotation.container.ElementName;
import net.sf.esfinge.metadata.annotation.container.ReflectionReference;
import net.sf.esfinge.metadata.container.ContainerTarget;

@ContainerFor(ContainerTarget.TYPE)
public class ClassContainer {

	@AllMethodsWith(ServiceMethod.class)
	private List<MethodContainer> methodsWithServiceMethod;

	@ReflectionReference
	private Class<?> classe;

	@ElementName
	private String nomeClass;

	@ContainsAnnotation(ServiceClass.class)
	private boolean temAnotacaoServiceClass;

	public Class<?> getClasse() {
		return classe;
	}

	public void setClasse(Class<?> classe) {
		this.classe = classe;
	}

	public String getNomeClass() {
		return nomeClass;
	}

	public void setNomeClass(String nomeClass) {
		this.nomeClass = nomeClass;
	}

	public boolean isTemAnotacaoServiceClass() {
		return temAnotacaoServiceClass;
	}

	public void setTemAnotacaoServiceClass(boolean temAnotacaoServiceClass) {
		this.temAnotacaoServiceClass = temAnotacaoServiceClass;
	}

	public List<MethodContainer> getMethodsWithServiceMethod() {
		return methodsWithServiceMethod;
	}

	public void setMethodsWithServiceMethod(List<MethodContainer> methodsWithServiceMethod) {
		this.methodsWithServiceMethod = methodsWithServiceMethod;
	}

}
