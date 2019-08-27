package org.esfinge.virtuallab.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esfinge.virtuallab.api.annotations.BarChartReturn;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;

@ServiceClass(description = "Demonstra a geracao de graficos (@BarChartReturn)")
public class ChartService
{
	@ServiceMethod(description = "Gera o grafico atraves de uma lista de numeros (List<Number>), configurando a anotacao com informacoes estaticas.")
	@BarChartReturn(dataLabels = {"Sul", "Sudeste", "Nordeste", "Norte", "Centro-Oeste"},
			dataColors = {"rgba(255, 99, 132, 0.2)", "rgba(255, 206, 86, 0.2)", "rgba(75, 192, 192, 0.2)", "rgba(153, 102, 255, 0.2)", "rgba(255, 159, 64, 0.2)"},
			legend = "Numero de Votos",
			title = "Eleições Brasil - Votos",
			titleFontSize = 40,
			xAxisLabel = "Região",
			yAxisLabel = "Votos",
			xAxisShowGridlines = false,
			yAxisShowGridlines = false,			
			axisFontSize = 30,
			horizontal = false) 
	public List<Number> createChart1()
	{
		List<Number> list = new ArrayList<>();
		list.add(12);
		list.add(19);
		list.add(3);
		list.add(7);
		list.add(10);
		
		return list;
	}
	
	@ServiceMethod(description = "Gera o grafico atraves de um mapa de numeros (Map<String,Number>), sem nenhuma configuracao da anotacao!")
	@BarChartReturn
	public Map<String,Number> createChart2()
	{
		Map<String, Number> map = new HashMap<>();
		
		map.put("Sul", 12);
		map.put("Sudeste", 19);
		map.put("Nordeste", 3);
		map.put("Norte", 7);
		map.put("Centro-Oeste", 10);
		
		return map;
	}

	@ServiceMethod(description = "Gera o grafico atraves de uma lista de objetos (List<Temperatura>), configurando na anotacao as propriedades"
			+ " do objeto que serao utilizadas para obter os dados do grafico!")
	@BarChartReturn(dataLabelsField = "mes",
			dataValuesField = "maxima",
			legend = "Temperatura Máxima",
			title = "Temperatura Máxima Anual",
			titleFontSize = 40,
			xAxisLabel = "Meses",
			yAxisLabel = "Temperatura",
			xAxisShowGridlines = false,
			yAxisShowGridlines = true,			
			axisFontSize = 30,
			horizontal = false) 
	public List<Temperatura> createChart3()
	{
		List<Temperatura> list = new ArrayList<>();
		list.add(new Temperatura(2l, "-23.5475","-46.63611111", 28.2, 19.3, "janeiro"));
		list.add(new Temperatura(3l, "-23.5475","-46.63611111", 28.8, 19.5, "fevereiro"));
		list.add(new Temperatura(4l, "-23.5475","-46.63611111", 28.0, 18.8, "marco"));
		list.add(new Temperatura(5l, "-23.5475","-46.63611111", 26.2, 17.4, "abril"));
		list.add(new Temperatura(6l, "-23.5475","-46.63611111", 23.3, 14.5, "maio"));
		list.add(new Temperatura(7l, "-23.5475","-46.63611111", 22.6, 13.0, "junho"));
		
		return list;
	}

	@ServiceMethod(description = "Gera o grafico atraves de um mapa de objetos (Map<String, Temperatura>), "
			+ "configurando a anotacao para gerar um grafico HORIZONTAL!")
	@BarChartReturn(dataValuesField = "minima",
			legend = "Temperatura Mínma",
			title = "Temperatura Mínima Anual",
			titleFontSize = 40,
			xAxisLabel = "Temperatura",
			yAxisLabel = "Meses",
			xAxisShowGridlines = true,
			yAxisShowGridlines = false,			
			axisFontSize = 30,
			horizontal = true) 
	public Map<String,Temperatura> createChart4()
	{
		Map<String, Temperatura> map = new HashMap<>();
		map.put("JANEIRO", new Temperatura(2l, "-23.5475","-46.63611111", 28.2, 19.3, "janeiro"));
		map.put("FEVEREIRO", new Temperatura(3l, "-23.5475","-46.63611111", 28.8, 19.5, "fevereiro"));
		map.put("MARCO", new Temperatura(4l, "-23.5475","-46.63611111", 28.0, 18.8, "marco"));
		map.put("ABRIL", new Temperatura(5l, "-23.5475","-46.63611111", 26.2, 17.4, "abril"));
		map.put("MAIO", new Temperatura(6l, "-23.5475","-46.63611111", 23.3, 14.5, "maio"));
		map.put("JUNHO", new Temperatura(7l, "-23.5475","-46.63611111", 22.6, 13.0, "junho"));
		
		return map;
	}
}
