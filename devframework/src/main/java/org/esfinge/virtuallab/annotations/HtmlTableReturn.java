package org.esfinge.virtuallab.annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import net.sf.esfinge.metadata.annotation.validator.Prohibits;
import net.sf.esfinge.metadata.annotation.validator.method.ValidMethodReturn;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ValidMethodReturn(validTypesToReturn = {List.class})
@Prohibits(JsonReturn.class)
public @interface HtmlTableReturn {

	 
}	