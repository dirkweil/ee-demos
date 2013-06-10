package de.gedoplan.buch.eedemos.bv.validation.constraint;

// CHECKSTYLE:OFF

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import de.gedoplan.buch.eedemos.bv.validation.entity.Adresse;
import de.gedoplan.buch.eedemos.bv.validation.validator.AdresseValidator;
import de.gedoplan.buch.eedemos.bv.validation.validator.LuhnIntValidator;
import de.gedoplan.buch.eedemos.bv.validation.validator.LuhnStringValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * BV-Constraint für das Modulo-10-Prüfziffern-Verfahren nach Hans Peter Luhn.
 * 
 * Anwendbar auf die Klasse {@link Adresse}. Details siehe {@link AdresseValidator}.
 * 
 * @author dw
 * 
 */
@Constraint(validatedBy = { LuhnStringValidator.class, LuhnIntValidator.class })
@Target({ FIELD, METHOD, TYPE })
@Retention(RUNTIME)
public @interface Luhn
{
  String message() default "{de.gedoplan.buch.eedemos.bv.validation.constraint.Luhn.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
