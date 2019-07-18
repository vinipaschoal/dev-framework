package devframework.annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import devframework.annotations.logical.NeedReturnListLogical;
import net.sf.esfinge.metadata.annotation.validator.Prohibits;
import net.sf.esfinge.metadata.annotation.validator.ToValidate;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@ToValidate(NeedReturnListLogical.class)
public @interface NeedReturnList {

	 
}	