package org.esfinge.virtuallab.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.esfinge.virtuallab.metadata.validator.ValidServiceClass;

/**
 * Deve ser utilizada nas classes que irao disponibilizar metodos como servicos.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ValidServiceClass
@Documentation(
	value="Deve ser utilizada nas classes que disponibilizarão métodos como serviços",
	usage="@ServiceClass<br/>" + 
		  "public class MyServiceClass {<br/>" + 
		  "// métodos de serviços (@ServiceMethod)..",
	notes={"A classe de serviço deve ter pelo menos 1 método de serviço",
		   "A classe de serviço deve ter um construtor vazio público (no-arg constructor)",
		   "Somente os métodos anotados com @ServiceMethod serão disponibilizados como serviços"})
public @interface ServiceClass
{
	@Documentation(
		value="Nome do módulo de serviços",
		defaultValue="O nome da classe")
	String label() default "";

	@Documentation("Texto informativo sobre os tipos de serviços que a classe disponibiliza")
	String description() default "";
}