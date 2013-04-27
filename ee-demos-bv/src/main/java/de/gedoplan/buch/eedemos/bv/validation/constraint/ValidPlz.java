package de.gedoplan.buch.eedemos.bv.validation.constraint;

// CHECKSTYLE:OFF

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

/**
 * BV-Constraint für Postleitzahlen.
 * 
 * Anwendbar auf die Klasse {@link String}.
 * 
 * @author dw
 * 
 */
@Constraint(validatedBy = {})
@Pattern(regexp = "\\d{5}")
@ReportAsSingleViolation
@Target({ FIELD, METHOD })
@Retention(RUNTIME)
public @interface ValidPlz
{
  String message() default "{de.gedoplan.buch.eedemos.bv.validation.constraint.ValidPlz.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
