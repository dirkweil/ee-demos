package de.gedoplan.buch.eedemos.bv.validation.constraint;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

public class LuhnTester
{
  private static Validator validator;

  static
  {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  public static void main(String[] args)
  {
    validate("8763");
    validate("8764");

    validate(8763);
    validate(8764);
  }

  private static void validate(String stringValue)
  {
    validate("stringValue", stringValue);
  }

  private static void validate(int intValue)
  {
    validate("intValue", intValue);
  }

  private static void validate(String fieldName, Object value)
  {
    Set<ConstraintViolation<ConstraintHolder>> violations = validator.validateValue(ConstraintHolder.class, fieldName, value);
    boolean ok = violations.isEmpty();
    System.out.println("\"" + value + "\": " + ok);
    if (!ok)
    {
      for (ConstraintViolation<ConstraintHolder> violation : violations)
      {
        System.out.println("  " + violation.getMessage());
      }
    }
  }

  private static class ConstraintHolder
  {
    @Luhn
    private String stringValue;

    @Luhn
    private int    intValue;
  }

}
