package de.gedoplan.buch.eedemos.bv.validation.validator;

import de.gedoplan.buch.eedemos.bv.validation.constraint.Luhn;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * BV-Validator zum Constraint {@link Luhn}.
 * 
 * Eine Zahl ist gültig, wenn ihre Ziffern nach dem Luhn-Algorithmus aufaddiert eine Summe ergeben, die durch 10 teilbar ist.
 * 
 * @author dw
 */
public class LuhnIntValidator implements ConstraintValidator<Luhn, Integer>
{

  @Override
  public void initialize(Luhn constraintAnnotation)
  {
  }

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context)
  {
    if (value == null)
    {
      return true;
    }

    List<Integer> digits = new ArrayList<>();
    int val = value;
    while (val != 0)
    {
      digits.add(0, val % 10);
      val /= 10;
    }

    return LuhnValidatorHelper.isLuhnCorrect(digits);
  }
}
