package de.gedoplan.buch.eedemos.bv.validation.validator;

import de.gedoplan.buch.eedemos.bv.validation.constraint.ValidAdresse;
import de.gedoplan.buch.eedemos.bv.validation.entity.Adresse;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * BV-Validator zum Constraint {@link ValidAdresse}.
 * 
 * Adressen sind gültig, wenn ihre Komponenten entweder komplett <code>null</code> oder nicht <code>null</code> sind.
 * 
 * @author dw
 */
public class AdresseValidator implements ConstraintValidator<ValidAdresse, Adresse>
{
  @Override
  public void initialize(ValidAdresse constraint)
  {
  }

  @Override
  public boolean isValid(Adresse adresse, ConstraintValidatorContext validationContext)
  {
    // null ist ok
    if (adresse == null)
    {
      return true;
    }

    // alles null ist ok
    if (adresse.getOrt() == null && adresse.getPlz() == null && adresse.getStrasse() == null)
    {
      return true;
    }

    // alles gefüllt ist ok
    if (adresse.getOrt() != null && adresse.getPlz() != null && adresse.getStrasse() != null)
    {
      return true;
    }

    return false;
  }
}
