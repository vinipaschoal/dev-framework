package org.esfinge.virtuallab.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import org.esfinge.virtuallab.metadata.processors.BarChartReturnProcessor;
import org.esfinge.virtuallab.metadata.validator.MethodReturn;
import org.hibernate.mapping.Map;

import net.sf.esfinge.metadata.annotation.validator.method.ValidMethodReturn;

/**
 * Indica que o retorno de um metodo sera apresentado como um grafico de barras simples.
 * Pode ser utilizado com Collection<Number>, Collection<Object> ou Map<String,Number>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ValidMethodReturn(validTypesToReturn = { Collection.class, Map.class })
@MethodReturn(processor = BarChartReturnProcessor.class)
public @interface BarChartReturn
{
	// titulo do grafico
	String title() default "";
	
	// tamanho da fonte do titulo
	int titleFontSize() default 20;
	
	// legenda dos valores do grafico
	String legend() default "";
	
	// nome do eixo X
	String xAxisLabel() default "";

	// nome do eixo Y
	String yAxisLabel() default "";
	
	// mostra as linhas de grade do eixo X
	boolean xAxisGridLines() default true;
	
	// mostra as linhas de grade do eixo Y
	boolean yAxisGridLines() default true;
	
	// tamanho da fonte dos eixos
	int axisFontSize() default 16;
	
	// barras horizontais ou verticais (default)
	boolean horizontal() default false;

	// nomes das categorias (barras) do grafico
	String[] labels() default {};
	
	// o campo do item da colecao que sera utilizado 
	// como nome das categorias (barras) do grafico
	String labelsField() default "";
	
	// cores das categorias (barras) do grafico
	String[] colors() default {};
	
	// o campo do item da colecao que sera utilizado
	// para obter a cor das categorias (barras) do grafico
	String colorsField() default "";
}
