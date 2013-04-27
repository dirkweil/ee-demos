package de.gedoplan.baselibs.utils.validation.constraint;

//CHECKSTYLE:OFF

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * BV-Constraint für "nicht leer".
 * 
 * Dies stellt eine Kombination von @{@link NotNull} und @{@link Size}(min=1) dar. Anwendbar auf die Klasse {@link String}.
 * 
 * @author dw
 */
@Constraint(validatedBy = {})
@NotNull
@Size(min = 1)
@ReportAsSingleViolation
@Target({ FIELD, METHOD })
@Retention(RUNTIME)
public @interface NotEmpty
{
  String message() default "{de.gedoplan.baselibs.utils.validation.constraint.NotEmpty.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
