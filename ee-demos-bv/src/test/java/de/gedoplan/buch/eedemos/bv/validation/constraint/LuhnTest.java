package de.gedoplan.buch.eedemos.bv.validation.constraint;

import de.gedoplan.buch.eedemos.bv.validation.constraint.Luhn;

import javax.validation.Validation;
import javax.validation.Validator;

public class LuhnTest
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
    boolean ok = validator.validateValue(ConstraintHolder.class, "stringValue", stringValue).isEmpty();
    System.out.println("\"" + stringValue + "\": " + ok);
  }

  private static void validate(int intValue)
  {
    boolean ok = validator.validateValue(ConstraintHolder.class, "intValue", intValue).isEmpty();
    System.out.println(intValue + ": " + ok);
  }

  @SuppressWarnings("unused")
  private static class ConstraintHolder
  {
    @Luhn
    private String stringValue;

    @Luhn
    private int    intValue;
  }

}
