package org.esfinge.virtuallab.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esfinge.virtuallab.api.InvokerProxy;
import org.esfinge.virtuallab.api.annotations.BarChartReturn;
import org.esfinge.virtuallab.api.annotations.Invoker;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;

@ServiceClass(label = "TemperaturaService @Invoker", description = "Demonstra a utilizacao de proxy para invocar um servico DAO disponivel (@Invoker e @InvokerProxy)")
@SuppressWarnings("unchecked")
public class TemperaturaInvokerProxy
{
	@Invoker
	private InvokerProxy invoker;
	
	@ServiceMethod(label = "Tabela de temperaturas @InvokerProxy", description = "Invoca o metodo 'getTemperatura' do DAO TemperaturaService e retorna em formato de tabela")
	@TableReturn(fields = {"mes", "latitude", "longitude", "maxima", "minima"})
	public List<Temperatura> listTemperaturasAsTable()
	{
		return this.invoker.invoke("org.esfinge.virtuallab.domain.TemperaturaService", "getTemperatura", List.class);		
	}

	@ServiceMethod(label = "Grafico de temperaturas @InvokerProxy", description = "Invoca o metodo 'getTemperaturaByMes' do DAO TemperaturaService e retorna em formato de tabela")
	@BarChartReturn(legend = "Temperatura Média",
	title = "Temperatura Média do Mês",
	titleFontSize = 40,
	xAxisLabel = "Local (lat,long)",
	yAxisLabel = "Temperatura",
	xAxisShowGridlines = true,
	yAxisShowGridlines = true,			
	axisFontSize = 30,
	horizontal = false) 
	public Map<String, Number> listTemperaturaMediaAsGraph(String mes)
	{
		List<Temperatura> result = this.invoker.invoke("org.esfinge.virtuallab.domain.TemperaturaService", "getTemperaturaByMes", List.class, mes);
		
		Map<String,Number> tempMap = new HashMap<>();
		result.forEach(t -> tempMap.put(String.format("%.4f, %.4f", Float.valueOf(t.getLatitude()), Float.valueOf(t.getLongitude())), (t.getMaxima() + t.getMinima()) / 2));
		
		return tempMap;
	}
}
