package de.gedoplan.buch.eedemos.bv.validation.validator;

import java.util.List;

/**
 * Helferklasse zu LuhnXxxValidator.
 * 
 * @author dw
 */
final class LuhnValidatorHelper
{
  static boolean isLuhnCorrect(List<Integer> digits)
  {
    int digitCount = digits.size();
    int sum = 0;
    boolean even = false;
    for (int i = digitCount - 1; i >= 0; --i)
    {
      int digit = digits.get(i);
      if (even)
      {
        digit *= 2;
      }
      if (digit > 9)
      {
        digit = digit - 9;
      }
      sum += digit;
      even = !even;
    }
    return sum % 10 == 0;
  }
}
