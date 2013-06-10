package de.gedoplan.buch.eedemos.bv.validation.constraint;

// CHECKSTYLE:OFF

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import de.gedoplan.buch.eedemos.bv.validation.entity.Adresse;
import de.gedoplan.buch.eedemos.bv.validation.validator.AdresseValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * BV-Constraint für Strassenadressen.
 * 
 * Anwendbar auf die Klasse {@link Adresse}. Details siehe {@link AdresseValidator}.
 * 
 * @author dw
 * 
 */
@Constraint(validatedBy = AdresseValidator.class)
@Target({ FIELD, METHOD, TYPE })
@Retention(RUNTIME)
public @interface ValidAdresse
{
  String message() default "{de.gedoplan.buch.eedemos.bv.validation.constraint.ValidAdresse.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
