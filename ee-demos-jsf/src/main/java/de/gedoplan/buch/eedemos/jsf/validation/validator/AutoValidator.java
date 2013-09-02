package de.gedoplan.buch.eedemos.jsf.validation.validator;

import de.gedoplan.buch.eedemos.jsf.entity.Auto;
import de.gedoplan.buch.eedemos.jsf.validation.constraint.ValidAuto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AutoValidator implements ConstraintValidator<ValidAuto, Auto>
{
  @Override
  public void initialize(ValidAuto constraint)
  {
  }

  @Override
  public boolean isValid(Auto auto, ConstraintValidatorContext validationContext)
  {
    // null ist ok
    if (auto == null)
    {
      return true;
    }

    boolean anzahlTuerenUngerade = (auto.getAnzahlTueren() % 2) != 0;

    String errorMessage = null;
    if (auto.isKombi())
    {
      if (!anzahlTuerenUngerade)
      {
        errorMessage = "Ein Kombi muss eine ungerade Anzahl Türen haben";
      }
    }
    else
    {
      if (anzahlTuerenUngerade)
      {
        errorMessage = "Ein Nicht-Kombi muss eine gerade Anzahl Türen haben";
      }
    }

    if (errorMessage == null)
    {
      return true;
    }

    validationContext.disableDefaultConstraintViolation();
    validationContext.buildConstraintViolationWithTemplate(errorMessage).addPropertyNode("anzahlTueren").addConstraintViolation();
    return false;
  }
}
