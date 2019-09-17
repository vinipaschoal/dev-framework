package org.esfinge.virtuallab.demo.map;

import java.util.Arrays;
import java.util.List;

import org.esfinge.virtuallab.api.annotations.Inject;
import org.esfinge.virtuallab.api.annotations.MapReturn;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.demo.dao.DaoDemo;
import org.esfinge.virtuallab.demo.dao.Temperatura;


/*--------------------------------------------------------------------------
 * Demonstracao da anotacao @MapReturn.
 *-------------------------------------------------------------------------*/
@ServiceClass(
	label = "MAP",
	description = "Demonstração da anotação @MapReturn.")
public class MapDemo
{
	private static final Cidade[] capitais = {
			new Cidade("Rio Branco", "AC", -9.97472, -67.81),
			new Cidade("Maceió", "AL", -9.66583, -35.73528),
			new Cidade("Manaus", "AM", -3.10194, -60.025),
			new Cidade("Macapá", "AP", 0.03889, -51.06639),
			new Cidade("Salvador", "BA", -12.97111, -38.51083),
			new Cidade("Fortaleza", "CE", -3.71722, -38.54306),
			new Cidade("Brasília", "DF", -15.77972, -47.92972),
			new Cidade("Vitória", "ES", -20.31944, -40.33778),
			new Cidade("Goiania", "GO", -16.67861, -49.25389),
			new Cidade("São Luis", "MA", -2.52972, -44.30278),
			new Cidade("Cuiabá", "MT", -15.59611, -56.09667),
			new Cidade("Campo Grande", "MS", -20.44278, -54.64639),
			new Cidade("Belo Horizonte", "MG", -19.92083, -43.93778),
			new Cidade("Belém", "PA", -1.45583, -48.50444),
			new Cidade("João Pessoa", "PB", -7.115, -34.86306),
			new Cidade("Curitiba", "PR", -25.42778, -49.27306),
			new Cidade("Recife", "PE", -8.05389, -34.88111),
			new Cidade("Teresina", "PI", -5.08917, -42.80194),
			new Cidade("Rio de Janeiro", "RJ", -22.90278, -43.2075),
			new Cidade("Natal", "RN", -5.795, -35.20944),
			new Cidade("Porto Velho", "RO", -8.76194, -63.90389),
			new Cidade("Porto Alegre", "RS", -30.03306, -51.23),
			new Cidade("Boa Vista", "RR", 2.81972, -60.67333),
			new Cidade("Florianópolis", "SC", -27.59667, -48.54917),
			new Cidade("Aracajú", "SE", -10.91111, -37.07167),
			new Cidade("São Paulo", "SP", -23.5475, -46.63611),
			new Cidade("Palmas", "TO", -10.16745, -48.32766)			
		};
	
	private static final Localidade[] locais_SJC = {
			new Localidade("INPE", "Instituto Nacional de Pesquisas Espaciais", -23.209632, -45.859959),
			new Localidade("DCTA", "Departamento de Ciência e Tecnologia Aeroespacial", -23.208436, -45.871526)
	};
	
	@Inject
	private DaoDemo temperaturaDAO;

	
	/*--------------------------------------------------------------------------
	 * Utiliza a anotacao sem parametros.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
			label = "Listar capitais do Brasil", 
			description = "@MapReturn sem parâmetros.")
	@MapReturn
	public List<Cidade> listCapitaisBrasil()
	{
		return Arrays.asList(capitais);
	}
	
	
	/*--------------------------------------------------------------------------
	 * Especifica o titulo e texto do marcador utilizando EL.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Listar temperatura por mês", 
		description = "@MapReturn utilizando EL.")
	@MapReturn(
		markerTitle = "${obj.local}",
		markerText = "Min: ${obj.minima}°C / Max: ${obj.maxima}°C")
	public List<Temperatura> listTemperaturaDoMes(String mes)
	{
		return this.temperaturaDAO.getTemperaturaByMes(mes);
	}
	
	@ServiceMethod(
		label = "Listar capitais do Brasil - Detalhes", 
		description = "@MapReturn utilizando EL.")
	@MapReturn(
		markerTitle = "${obj.nome}",
		markerText = "Estado: ${obj.estado}")
	public List<Cidade> listCapitaisBrasilDetalhes()
	{
		return this.listCapitaisBrasil();
	}
	
	
	/*--------------------------------------------------------------------------
	 * Especifica campos de lat/long e configuracoes do mapa.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Listar locais de SJC", 
		description = "@MapReturn especificando configurações do mapa.")
	@MapReturn(
		markerTitle = "${obj.nome}",
		markerText = "${obj.observacoes}",
		markerLatField = "posLat",
		markerLongField = "posLong",
		mapCenterLat = -23.209221,
		mapCenterLong = -45.865059,
		mapZoom = 15)
	public List<Localidade> listLocaisSJC()
	{
		return Arrays.asList(locais_SJC);
	}
}
