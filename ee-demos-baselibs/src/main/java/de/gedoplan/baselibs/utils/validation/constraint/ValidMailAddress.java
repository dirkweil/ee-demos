package de.gedoplan.baselibs.utils.validation.constraint;

import de.gedoplan.baselibs.utils.validation.validator.MailAddressValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

//CHECKSTYLE:OFF

/**
 * Bean-Validation-Constraint für gültige Mail-Adressen.
 * 
 * Anwendbar auf Felder oder Methoden vom Typ <code>String</code>.
 * 
 * Mail-Adressen werden als gültig angesehen, wenn die Form Name@Domain haben. Name und Domäne dürfen aus Buchstaben, Zahlen, '_',
 * '.' und '-' bestehen. Die Domäne muss min. zweiteilg sein.
 * 
 * @author dw
 */
@Constraint(validatedBy = { MailAddressValidator.class })
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMailAddress
{
  String message() default "{de.gedoplan.baselibs.utils.validation.constraint.ValidMailAddress.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /**
   * Ist der Name optional? Ist dieser Wert <code>true</code>, muss eine gültige Adresse keinen Namen enthalten
   * 
   * @return <code>true</code>, wenn der Name optional ist
   */
  boolean nameOptional() default false;
}
