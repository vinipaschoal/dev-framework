package org.esfinge.virtuallab.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esfinge.virtuallab.api.InvokerProxy;
import org.esfinge.virtuallab.api.annotations.BarChartReturn;
import org.esfinge.virtuallab.api.annotations.Inject;
import org.esfinge.virtuallab.api.annotations.Invoker;
import org.esfinge.virtuallab.api.annotations.MapReturn;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;

@ServiceClass(label = "TemperaturaServiceProxy @Invoker / @Inject", description = "Demonstra a utilizacao de proxy para invocar um servico DAO disponivel (@Invoker e @Inject)")
@SuppressWarnings("unchecked")
public class TemperaturaServiceProxy
{
	@Invoker
	private InvokerProxy invoker;
	
	@Inject
	private TemperaturaService service;
	
	
	
	@ServiceMethod(label = "Tabela de temperaturas @Invoker", description = "Invoca o metodo 'getTemperatura' do DAO TemperaturaService e retorna em formato de tabela")
	@TableReturn(fields = {"mes", "local", "latitude", "longitude", "maxima", "minima"})
	public List<Temperatura> listTemperaturasAsTable()
	{
		return this.invoker.invoke("org.esfinge.virtuallab.domain.TemperaturaService", "getTemperatura", List.class);		
	}

	@ServiceMethod(label = "Gráfico de temperaturas médias @Inject", description = "Invoca o metodo 'getTemperaturaByMes' do DAO TemperaturaService e retorna em formato de tabela")
	@BarChartReturn(legend = "Temperatura Média",
	title = "Temperatura Média do Mês",
	titleFontSize = 40,
	xAxisLabel = "Local",
	yAxisLabel = "Temperatura",
	xAxisShowGridlines = true,
	yAxisShowGridlines = true,
	axisFontSize = 30,
	horizontal = false) 
	public Map<String, Number> listTemperaturaMediaAsGraph(String mes)
	{
		List<Temperatura> result = this.service.getTemperaturaByMes(mes);
		
		Map<String,Number> tempMap = new HashMap<>();
		result.forEach(t -> tempMap.put(t.getLocal(), (t.getMaxima() + t.getMinima()) / 2));
		
		return tempMap;
	}
	
	@ServiceMethod(label = "Mapa de temperaturas @Inject", description = "Invoca o metodo 'getTemperaturaByMes' do DAO TemperaturaService e retorna em formato de mapa")
	@MapReturn(markerTitle = "${obj.local}",
	markerText = "Temperaturas:<br/>Min: ${obj.minima}°C / Max: ${obj.maxima}°C")
	public List<Temperatura> listTemperaturaAsMap(String mes)
	{
		return this.service.getTemperaturaByMes(mes);
	}
}
