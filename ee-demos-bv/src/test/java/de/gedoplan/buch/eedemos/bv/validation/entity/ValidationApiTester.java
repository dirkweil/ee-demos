package de.gedoplan.buch.eedemos.bv.validation.entity;

import de.gedoplan.buch.eedemos.bv.validation.group.InitialInput;
import de.gedoplan.buch.eedemos.bv.validation.util.FragebogenSamples;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

public class ValidationApiTester
{
  private static Validator validator;

  static
  {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  public static void main(String[] args)
  {
    validate(InitialInput.class);
    validate();
    validate(Default.class, InitialInput.class);
  }

  private static void validate(Class<?>... groups)
  {
    System.out.println("Validierung (Validierungsgruppen:" + groupDescription(groups) + ")");
    for (Fragebogen fragebogen : FragebogenSamples.FRAGEBOEGEN)
    {
      System.out.println("  " + fragebogen);

      Set<ConstraintViolation<Fragebogen>> constraintViolations = validator.validate(fragebogen, groups);
      for (ConstraintViolation<Fragebogen> constraintViolation : constraintViolations)
      {
        System.out.println("    " + constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage());
      }
    }
    System.out.println();
  }

  private static CharSequence groupDescription(Class<?>[] groups)
  {
    if (groups.length == 0)
    {
      return " keine";
    }

    StringBuffer description = new StringBuffer();
    for (Class<?> group : groups)
    {
      description.append(' ').append(group.getSimpleName());
    }
    return description;
  }

}
