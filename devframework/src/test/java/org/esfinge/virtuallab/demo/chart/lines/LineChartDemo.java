package org.esfinge.virtuallab.demo.chart.lines;

import java.util.List;

import org.esfinge.virtuallab.api.annotations.Inject;
import org.esfinge.virtuallab.api.annotations.LineChartReturn;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;

@ServiceClass(
		label = "GRÁFICOS - LINHAS",
		description = "Demonstração da anotação @LineChartReturn.")
public class LineChartDemo {

		@Inject
		private DaoXrayLow xrayLow;
		
		@ServiceMethod(
				label = "Criar gráfico - Lista",
				description = "@ChartReturn com parametros."
				)
		@LineChartReturn(typeOfChart = "line",
					dataLabels = "eventDateTime",
					temporalSeries = true,
					multipleDataset = true,
					xAxisShowGridlines = false, 
					title = "Fluxo Raio-X",
					yAxisLabel = "Watts m²",
					xAxisLabel = "Tempo",
					xAxis = {"eventDateTime"},
					yAxis= {"shortXray","longXray","R1","R2","R3","R4","R5"})
		public List<?> listaXrayDataLowGetXray(Long header)
		{
			
			List<XrayDataLow> resp = xrayLow.getXrayDataLowByHeaderOrderById(header);
			
			
			return resp;	
		}	
}
