package org.esfinge.virtuallab.inpe;
import java.util.List;

import org.esfinge.virtuallab.api.annotations.ServiceDAO;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;

import net.sf.esfinge.querybuilder.Repository;


/*--------------------------------------------------------------------------
 * Demonstracao da anotacao @ServiceDAO.
 *-------------------------------------------------------------------------*/
@ServiceDAO(
	label = "DAO",
	description = "Demonstração da anotação @ServiceDAO.",
	url = "jdbc:postgresql://localhost:5432/postgres",
	user = "postgres", 
	password = "postgres", 
	dialect = "org.hibernate.dialect.PostgreSQLDialect")
public interface DaoDemoM extends Repository<StationM>
{
	@ServiceMethod(
			label = "Listar todas temperaturas",
			description = "Retorna todos as temperaturas cadastradas no Banco de Dados.")
		@TableReturn
		public List<StationM> getStationM();

}
