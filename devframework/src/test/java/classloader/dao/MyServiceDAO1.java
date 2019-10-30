package classloader.dao;

import java.util.List;

import org.esfinge.virtuallab.api.annotations.ServiceDAO;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;

import net.sf.esfinge.querybuilder.Repository;

@ServiceDAO(
		label = "DAO",
		description = "Demonstração da anotação @ServiceDAO.",
		url = "jdbc:postgresql://localhost:5432/postgres", 
		user = "postgres", 
		password = "postgres", 
		dialect = "org.hibernate.dialect.PostgreSQLDialect")
public interface MyServiceDAO1 extends Repository<MyEntity1>
{
	@ServiceMethod
	@TableReturn
	/*
	@CustomReturn(
			fields = {"local", "mes", "minima", "maxima"},
			labels = {"CIDADE", "MÊS", "MIN", "MAX"})
	*/
	public List<MyEntity1> getMyEntity();
}
