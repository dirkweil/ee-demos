package de.gedoplan.baselibs.utils.validation.validator;

import de.gedoplan.baselibs.utils.validation.constraint.ValidTelNumber;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * BV-Validatorklasse zum Constraint {@link ValidTelNumber}.
 * 
 * Telefonnummern werden als gültig angesehen, wenn sie der DIN 5008 entsprechen: Funktionsbezogene Trennung durch Leerzeichen,
 * Durchwahl mit Bindestrich abgetrennt. Bsp.: +49 30 12345-67
 * 
 * @author dw
 */
public class TelNumberValidator implements ConstraintValidator<ValidTelNumber, String>
{
  private static final Pattern PATTERN = Pattern.compile("\\+\\d{1,3} \\d+ \\d+(-\\d+)?");

  @Override
  public void initialize(ValidTelNumber constraint)
  {
  }

  @Override
  public boolean isValid(String telNo, ConstraintValidatorContext validationContext)
  {
    return isValid(telNo);
  }

  /**
   * Test der Telefonnummer auf Validität.
   * 
   * @param telNo Telefonnummer
   * @return <code>true</code>, wenn Nummer valide
   */
  public static boolean isValid(String telNo)
  {
    if (telNo == null)// null ist ok
    {
      return true;
    }

    return PATTERN.matcher(telNo).matches();
  }
}
