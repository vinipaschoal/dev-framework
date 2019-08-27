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
public @interface ServiceDAO
{
	// rotulo para a classe
	// por padrao usa o nome da classe
	String label() default "";
	
	// texto informativo sobre os tipos de servicos que a classe disponibiliza
	String description() default "";
	
	// URL de conexao com o BD
	String url();
	
	// usuario para conxeao com o BD
	String user();
	
	// senha para conexao com o BD
	String password();
	
	// dialeto Hibernate do BD a ser acessado
	String dialect();
}
