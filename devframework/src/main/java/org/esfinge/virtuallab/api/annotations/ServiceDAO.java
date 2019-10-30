package org.esfinge.virtuallab.api.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.esfinge.virtuallab.metadata.validator.ValidServiceDAO;

/**
 * Deve ser utilizada nas interfaces que irao disponibilizar metodos de consulta ao Banco de Dados como servicos utilizando o Esfinge QueryBuilder. 
 */
@Retention(RUNTIME)
@Target(ElementType.TYPE)
@ValidServiceDAO
@Documentation(
	value="Deve ser utilizada nas interfaces que disponibilizarão métodos de consulta ao Banco de Dados como serviços",
	usage="@ServiceDAO<br/>" + 
		  "public interface MyServiceDAO {<br/>" + 
		  "// métodos de serviços (@ServiceMethod)..",
	notes={"A interface DAO deve ter pelo menos 1 método de serviço",
		   "Os métodos da interface DAO devem ser compatíveis com o padrão de nomenclatura do Esfinge QueryBuilder",
		   "Somente os métodos anotados com @ServiceMethod serão disponibilizados como serviços"})
public @interface ServiceDAO
{
	@Documentation(
		value="Nome do módulo de serviços",
		defaultValue="O nome da interface")
	String label() default "";
	
	@Documentation("Texto informativo sobre os tipos de serviços que a interface disponibiliza")
	String description() default "";
	
	@Documentation(
		value="URL de conexão com o Banco de Dados",
		required=true)
	String url();
	
	@Documentation(
		value="Usuário para a conexão com o Banco de Dados",
		required=true)
	String user();
	
	@Documentation(
		value="Senha para a conexão com o Banco de Dados",
		required=true)
	String password();
	
	@Documentation(
		value="Dialeto Hibernate/JPA do Banco de Dados a ser acessado",
		required=true,
		notes="Lista de dialetos disponíveis em " +
			  "https://docs.jboss.org/hibernate/orm/4.1/javadocs/org/hibernate/dialect/package-summary.html")
	String dialect();
}
