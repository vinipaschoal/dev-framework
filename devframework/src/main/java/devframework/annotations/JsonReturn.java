package devframework.annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import devframework.annotations.logical.JsonReturnLogical;
import net.sf.esfinge.metadata.annotation.validator.ToValidate;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ToValidate(JsonReturnLogical.class)
public @interface JsonReturn {

	 
}	