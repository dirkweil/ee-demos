package de.gedoplan.buch.eedemos.bv.validation.validator;

import de.gedoplan.buch.eedemos.bv.validation.constraint.Luhn;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * BV-Validator zum Constraint {@link Luhn}.
 * 
 * Ein String ist gültig, wenn seine Ziffern nach dem Luhn-Algorithmus aufaddiert eine Summe ergeben, die durch 10 teilbar ist.
 * 
 * @author dw
 */
public class LuhnStringValidator implements ConstraintValidator<Luhn, String>
{

  @Override
  public void initialize(Luhn constraintAnnotation)
  {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context)
  {
    if (value == null)
    {
      return true;
    }

    List<Integer> digits = new ArrayList<>();
    for (int i = 0; i < value.length(); ++i)
    {
      char c = value.charAt(i);
      if (Character.isDigit(c))
      {
        digits.add(c - '0');
      }
    }
    return LuhnValidatorHelper.isLuhnCorrect(digits);
  }
}
