package de.gedoplan.buch.eedemos.cdi.stereotype;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Stereotype;

@Alternative
@Stereotype
@Retention(RUNTIME)
@Target({ METHOD, FIELD, TYPE })
public @interface Mock
{
}
