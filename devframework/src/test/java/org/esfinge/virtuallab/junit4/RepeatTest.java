package org.esfinge.virtuallab.junit4;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Permite a repeticao de testes no JUnit4.
 * Baseado em: https://github.com/cbismuth/junit-repeat-rule
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface RepeatTest
{
	int times();
	boolean verbose() default false;
}
