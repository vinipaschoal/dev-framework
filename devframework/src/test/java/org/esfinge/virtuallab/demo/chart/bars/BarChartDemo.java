package org.esfinge.virtuallab.demo.chart.bars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.esfinge.virtuallab.api.annotations.BarChartReturn;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;


/*--------------------------------------------------------------------------
 * Demonstracao da anotacao @BarChartReturn.
 *-------------------------------------------------------------------------*/
@ServiceClass(
	label = "GRÁFICOS - BARRAS",
	description = "Demonstração da anotação @BarChartReturn.")
public class BarChartDemo
{
	private static final List<Votes> votes = new ArrayList<>();
	static {
		for ( int i = 1; i <= 5; i++ )
			votes.add(new Votes(RandomUtils.nextInt(5, 20), String.format("Região %02d", i), 
					String.format("rgba(%d, %d, %d, 0.7)", RandomUtils.nextInt(0, 256), RandomUtils.nextInt(0, 256), RandomUtils.nextInt(0, 256)))); // cor aleatoria
	}	
	
	/*--------------------------------------------------------------------------
	 * Utiliza a anotacao sem parametros.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Criar gráfico - Lista",
		description = "@BarChartReturn sem parâmetros.")
	@BarChartReturn
	public List<Number> createChartByList()
	{
		List<Number> list = new ArrayList<>();
		list.add(12);
		list.add(19);
		list.add(3);
		list.add(7);
		list.add(10);
		
		return list;
	}

	@ServiceMethod(
		label = "Criar gráfico - Mapa",
		description = "@BarChartReturn sem parâmetros.")
	@BarChartReturn
	public Map<String,Number> createChartByMap()
	{
		Map<String, Number> map = new HashMap<>();
		
		map.put("Sul", 12);
		map.put("Sudeste", 19);
		map.put("Nordeste", 3);
		map.put("Norte", 7);
		map.put("Centro-Oeste", 10);
		
		return map;
	}
	
	
	/*--------------------------------------------------------------------------
	 * Especifica os nomes (categorias) das barras dos dados retornados.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Criar gráfico - Nomes / Categorias (Fixo)",
		description = "@BarChartReturn especificando os nomes (categorias) das barras.")
	@BarChartReturn(
		dataLabels = {"CENTRO-OESTE", "NORDESTE", "SUL", "SUDESTE", "NORTE"})
	public List<Number> createChartByNamedCategories()
	{
		return this.createChartByList();
	}
	
	@ServiceMethod(
		label = "Criar gráfico - Nomes / Categorias (Propriedade)",
		description = "@BarChartReturn especificando o campo dos nomes das barras.")
	@BarChartReturn(
		dataValuesField = "total",
		dataLabelsField = "region")
	public List<Votes> createChartByCategoriesField()
	{
		return votes;
	}

	
	/*--------------------------------------------------------------------------
	 * Especifica as cores das barras.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Criar gráfico - Cores (Fixo)",
		description = "@BarChartReturn especificando as cores das barras.")
	@BarChartReturn(
		dataColors = {"blue", "green", "red", "yellow", "gray"})
	public Map<String,Number> createChartByNamedColors()
	{
		return this.createChartByMap();
	}
	
	@ServiceMethod(
		label = "Criar gráfico - Cores (Propriedade)",
		description = "@BarChartReturn especificando as cores das barras.")
	@BarChartReturn(
		dataValuesField = "total",
		dataLabelsField = "region",
		dataColorsField = "color")
	public List<Votes> createChartByColorsField()
	{
		return votes;
	}

	
	/*--------------------------------------------------------------------------
	 * Especifica as grades dos valores dos eixos X e Y.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Criar gráfico - Grid",
		description = "@BarChartReturn especificando as grades dos valores dos eixos X e Y.")
	@BarChartReturn(
		dataValuesField = "total",
		dataLabelsField = "region",
		dataColorsField = "color",
		xAxisShowGridlines = false,
		yAxisShowGridlines = false)
	public List<Votes> createChartByGrid()
	{
		return votes;
	}

	
	/*--------------------------------------------------------------------------
	 * Especifica o titulo do grafico e os labels dos eixos.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Criar gráfico - Título / Eixos",
		description = "@BarChartReturn especificando o título do gráfico e label dos eixos X e Y.")
	@BarChartReturn(
		legend = "Número de Votos",
		title = "Eleições - Votos",
		titleFontSize = 40,
		xAxisLabel = "Região",
		yAxisLabel = "Votos",
		axisFontSize = 30)
	public Map<String,Number> createChartByTitleAndAxis()
	{
		return this.createChartByMap();
	}


	/*--------------------------------------------------------------------------
	 * Especifica o grafico com barras HORIZONTAIS.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Criar gráfico - Horizontal",
		description = "@BarChartReturn especificando gráfico horizontal.")
	@BarChartReturn(
		dataValuesField = "total",
		dataLabelsField = "region",
		dataColorsField = "color",
		legend = "Número de Votos",
		title = "Eleições - Votos",
		titleFontSize = 40,
		xAxisLabel = "Votos",
		yAxisLabel = "Região",
		axisFontSize = 30,
		xAxisShowGridlines = true,
		yAxisShowGridlines = false,
		horizontal = true)
	public List<Votes> createChartHorizontalBars()
	{
		return votes;
	}
}
