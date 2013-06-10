package de.gedoplan.baselibs.enterprise.stereotype;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Stereotype;

/**
 * Stereotyp für Domain Services.
 * 
 * @author dw
 */
@RequestScoped
@Stereotype
@Target({ TYPE, METHOD, FIELD })
@Retention(RUNTIME)
public @interface DomainService
{
}
