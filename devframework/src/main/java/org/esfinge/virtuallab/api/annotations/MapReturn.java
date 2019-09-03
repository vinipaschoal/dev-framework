package org.esfinge.virtuallab.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.esfinge.virtuallab.metadata.processors.MapReturnProcessor;
import org.esfinge.virtuallab.metadata.validator.MethodReturn;

/**
 * Renderiza o retorno de um metodo como um mapa.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@MethodReturn(processor = MapReturnProcessor.class)
public @interface MapReturn
{
	// latitude do centro do mapa
	// por padrao, utiliza a latitude no centro do Brasil
	double mapCenterLat() default -15.77972;	
	
	// longitude do centro do mapa
	// por padrao, utiliza a longitude no centro do Brasil
	double mapCenterLong() default -52.92972;
	
	// zoom inicial do mapa
	int mapZoom() default 5;
	
	// titulo do texto do(s) marcador(es)
	// pode ser utilizada uma expressao EL para acessar um campo do objeto retornado 
	String markerTitle() default "";
	
	// texto do(s) marcador(es)
	// pode ser utilizada uma expressao EL para acessar um campo do objeto retornado 
	String markerText() default "";
	
	// latitude do(s) marcador(es)
	String markerLatField() default "";
	
	// longitude do(s) marcador(es)
	String markerLongField() default "";
}
