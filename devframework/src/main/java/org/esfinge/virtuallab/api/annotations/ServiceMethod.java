package org.esfinge.virtuallab.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.esfinge.virtuallab.metadata.validator.ValidServiceMethod;

import net.sf.esfinge.metadata.annotation.validator.method.ForbiddenMethodReturn;
import net.sf.esfinge.metadata.annotation.validator.method.InstanceMethodOnly;
import net.sf.esfinge.metadata.annotation.validator.method.MethodVisibilityRequired;

/**
 * Deve ser utilizada nos metodos que serao disponibilizados como servicos.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ForbiddenMethodReturn(invalidTypesToReturn = { void.class })
@MethodVisibilityRequired(itNeedsToHaveThisVisibility = "public")
@InstanceMethodOnly
@ValidServiceMethod
@Documentation(
	value="Deve ser utilizada nos métodos que serão disponibilizados como serviço",
	usage="@ServiceMethod<br/>" + 
		  "public List myServiceMethod()",
	dependencies= {"@ServiceClass", "@ServiceDAO"},
	notes={"O método de serviço deve estar contido dentro de uma classe anotada "
			+ "com @ServiceClass ou @ServiceDAO",
		   "O método de serviço deve ser público",
		   "O método de serviço deve retornar algum valor (não pode ser void)",
		   "Há restrições aos tipos de retornos e de parâmetros permitidos para métodos de serviço"})
public @interface ServiceMethod
{
	@Documentation(
		value="Nome do serviço",
		defaultValue="O nome do método")
	String label() default "";
	
	@Documentation("Texto informativo sobre o serviço")
	String description() default "";
}