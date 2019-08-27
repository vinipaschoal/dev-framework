package org.esfinge.virtuallab.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.esfinge.virtuallab.metadata.validator.InjectValidator;

import net.sf.esfinge.metadata.annotation.validator.ToValidate;

/**
 * Permite injetar uma classe DAO ou de servico para invocar seus metodos.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ToValidate(InjectValidator.class)
public @interface Inject
{

}
