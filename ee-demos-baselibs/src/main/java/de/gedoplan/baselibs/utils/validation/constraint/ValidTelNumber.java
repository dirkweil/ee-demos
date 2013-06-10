package de.gedoplan.baselibs.utils.validation.constraint;

import de.gedoplan.baselibs.utils.validation.validator.TelNumberValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

// CHECKSTYLE:OFF

/**
 * Bean-Validation-Constraint für gültige Telefonnummern.
 * 
 * Anwendbar auf Felder oder Methoden vom Typ <code>String</code>. Details siehe {@link TelNumberValidator}.
 * 
 * @author dw
 */
@Constraint(validatedBy = { TelNumberValidator.class })
@ReportAsSingleViolation
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTelNumber
{
  public static final String MESSAGE = "{de.gedoplan.baselibs.utils.validation.constraint.ValidTelNumber.message}";

  String message() default MESSAGE;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
