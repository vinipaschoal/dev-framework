package org.esfinge.virtuallab.demo.proxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esfinge.virtuallab.api.InvokerProxy;
import org.esfinge.virtuallab.api.annotations.BarChartReturn;
import org.esfinge.virtuallab.api.annotations.Inject;
import org.esfinge.virtuallab.api.annotations.Invoker;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;
import org.esfinge.virtuallab.demo.dao.DaoDemo;
import org.esfinge.virtuallab.demo.dao.Temperatura;


/*--------------------------------------------------------------------------
 * Demonstracao das anotacoes @Invoker e @Inject.
 *-------------------------------------------------------------------------*/
@ServiceClass(
	label = "PROXY",
	description = "Demonstração das anotações @Invoker e @Inject.")
@SuppressWarnings("unchecked")
public class ProxyDemo
{
	@Invoker
	private InvokerProxy invoker;
	
	@Inject
	private DaoDemo temperaturaDAO;
	
	
	/*--------------------------------------------------------------------------
	 * Demonstra a anotacao @Invoker.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Listar todas temperaturas", 
		description = "Utiliza um serviço disponível através da anotação @Invoker.")
	@TableReturn(
		fields = {"mes", "local", "maxima", "minima"},
		headerLabels = {"Mês", "Local", "Temp. Máx", "Temp. Mín"})
	public List<Temperatura> listTemperaturasByInvoker()
	{
		return this.invoker.invoke("org.esfinge.virtuallab.demo.dao.DaoDemo", "getTemperatura", List.class);		
	}

	
	/*--------------------------------------------------------------------------
	 * Demonstra a anotacao @Inject.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Gráfico de temperatura média por mês", 
		description = "Utiliza um serviço disponível através da anotação @Inject.")
	@BarChartReturn(
		legend = "Temp. Média",
		title = "Temperatura Média do Mês",
		titleFontSize = 40,
		xAxisLabel = "Local",
		yAxisLabel = "Temperatura",
		axisFontSize = 30) 
	public Map<String, Number> listTemperaturaMediaByInject(String mes)
	{
		List<Temperatura> result = this.temperaturaDAO.getTemperaturaByMes(mes);
		
		Map<String,Number> tempMap = new HashMap<>();
		result.forEach(t -> tempMap.put(t.getLocal(), (t.getMaxima() + t.getMinima()) / 2));
		
		return tempMap;
	}
}
