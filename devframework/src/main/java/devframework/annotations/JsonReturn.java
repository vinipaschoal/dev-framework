package devframework.annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.esfinge.metadata.annotation.validator.Prohibits;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@NoVoidReturn
@Prohibits(HtmlTableReturn.class)
public @interface JsonReturn {

	 
}	