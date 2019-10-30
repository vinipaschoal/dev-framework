package org.esfinge.virtuallab.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;

import org.esfinge.virtuallab.metadata.processors.LineChartReturnProcessor;
import org.esfinge.virtuallab.metadata.validator.MethodReturn;

import net.sf.esfinge.metadata.annotation.validator.method.ValidMethodReturn;

/**
 * Renderiza o retorno de um metodo como um grafico de linhas.
 * Pode ser utilizado com List<Number>, List<Object>, Map<String,Number> ou Map<String,Object>.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ValidMethodReturn(validTypesToReturn = { List.class, Map.class })
@MethodReturn(processor = LineChartReturnProcessor.class)
public @interface LineChartReturn 
{
	// titulo do grafico
	String title() default "";
	
	//MultipleDataSet
	boolean multipleDataset() default false; 
	
	// tamanho da fonte do titulo
	int titleFontSize() default 20;
	
	// legenda dos valores do grafico
	String legend() default "";
	
	// nome do eixo X
	String xAxisLabel() default "";

	// nome do eixo Y
	String yAxisLabel() default "";
	
	// mostra as linhas de grade do eixo X
	boolean xAxisShowGridlines() default true;
	
	// mostra as linhas de grade do eixo Y
	boolean yAxisShowGridlines() default true;
	
	// tamanho da fonte dos eixos
	int axisFontSize() default 16;
	
	// barras horizontais ou verticais (default)
	boolean horizontal() default false;

	// nomes das categorias (barras) do grafico
	String dataLabels() default "";
	
	// cores das categorias (barras) do grafico
	String[] dataColors() default {};
	
	// o campo dos objetos da colecao que sera utilizado 
	// para obter o nome das categorias (barras) do grafico
	String dataLabelsField() default "";
	
	// o campo dos objetos da colecao que sera utilizado
	// para obter a cor das categorias (barras) do grafico
	String dataColorsField() default "";
	
	// o campo dos objetos da colecao que sera utilizado 
	// para obter o valor das categorias (barras) do grafico
	String dataValuesField() default "";

	String typeOfChart() default "line";

	String[] xAxis() default "";

	String[] yAxis() default "";



	boolean temporalSeries() default false; 
}