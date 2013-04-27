package de.gedoplan.buch.eedemos.provs.firma.validation.constraint;

// CHECKSTYLE:OFF

import de.gedoplan.buch.eedemos.provs.firma.entity.StrassenAdresse;
import de.gedoplan.buch.eedemos.provs.firma.validation.validator.StrassenAdresseValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * BV-Constraint für Strassenadressen.
 * 
 * Anwendbar auf die Klasse {@link StrassenAdresse}. Details siehe {@link StrassenAdresseValidator}.
 * 
 * @author dw
 * 
 */
@Constraint(validatedBy = StrassenAdresseValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStrassenAdresse
{
  String message() default "{de.gedoplan.buch.eedemos.provs.firma.validation.constraint.ValidStrassenAdresse.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
