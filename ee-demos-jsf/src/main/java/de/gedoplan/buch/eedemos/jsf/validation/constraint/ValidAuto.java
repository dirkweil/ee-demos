package de.gedoplan.buch.eedemos.jsf.validation.constraint;

import de.gedoplan.buch.eedemos.jsf.validation.validator.AutoValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = AutoValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAuto
{
  String message() default "Auto falsch";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
