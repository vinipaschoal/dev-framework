package org.esfinge.virtuallab.demo.chart.lines;

import java.util.List;

import org.esfinge.virtuallab.api.annotations.ServiceDAO;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;

import net.sf.esfinge.querybuilder.Repository;
import net.sf.esfinge.querybuilder.annotation.GreaterOrEquals;

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
public interface DaoXrayLow extends Repository<XrayDataLow>
{
	    @ServiceMethod(
			label = "Listar os valores de goes.xray_data_low",
			description = "Retorna todos os elementos da tabela goes.xray_data_low")
		@TableReturn
		public List<XrayDataLow> getXrayDataLow();
	    
	    @ServiceMethod(
				label = "Listar os valores de goes.xray_data_low",
				description = "Retorna todos os elementos da tabela goes.xray_data_low")
		@TableReturn
		public List<XrayDataLow> getXrayDataLowById(@GreaterOrEquals Long id);
	    
	    @ServiceMethod(
				label = "Listar os valores de goes.xray_data_low filtrando por header",
				description = "Retorna todos os elementos da tabela goes.xray_data_low")
		@TableReturn
		public List<XrayDataLow> getXrayDataLowByIdAndHeader(@GreaterOrEquals Long id,Long header);
	    
	    
	    @ServiceMethod(
				label = "Listar os valores de goes.xray_data_low filtrando por data",
				description = "Retorna todos os elementos da tabela goes.xray_data_low")
		@TableReturn
	    public List<XrayDataLow> getXrayDataLowByHeaderOrderById(Long header);


}