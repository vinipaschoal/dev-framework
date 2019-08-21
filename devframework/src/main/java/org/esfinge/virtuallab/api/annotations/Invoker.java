package org.esfinge.virtuallab.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.esfinge.virtuallab.metadata.validator.InvokerProxyValidator;

import net.sf.esfinge.metadata.annotation.validator.ToValidate;

/**
 * Permite injetar um proxy para invocar metodos de outras classes de servico.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ToValidate(InvokerProxyValidator.class)
public @interface Invoker
{

}
