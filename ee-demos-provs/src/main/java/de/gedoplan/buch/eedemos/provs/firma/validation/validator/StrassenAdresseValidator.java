package de.gedoplan.buch.eedemos.provs.firma.validation.validator;

import de.gedoplan.buch.eedemos.provs.firma.entity.StrassenAdresse;
import de.gedoplan.buch.eedemos.provs.firma.validation.constraint.ValidStrassenAdresse;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * BV-Validator zum Constraint {@link ValidStrassenAdresse}.
 *
 * Strassenadressen sind gültig, wenn ihre Komponenten entweder komplett <code>null</code> oder nicht <code>null</code> sind.
 *
 * @author dw
 */
public class StrassenAdresseValidator implements ConstraintValidator<ValidStrassenAdresse, StrassenAdresse>
{
  @Override
  public void initialize(ValidStrassenAdresse constraint)
  {
  }

  @Override
  public boolean isValid(StrassenAdresse adresse, ConstraintValidatorContext validationContext)
  {
    if (adresse == null // null ist ok
        || (adresse.getLand() == null && adresse.getOrt() == null && adresse.getPlz() == null && adresse.getStrasse() == null) // alles null ist ok
        || (adresse.getLand() != null && adresse.getOrt() != null && adresse.getPlz() != null && adresse.getStrasse() != null))
    {
      return true;
    }

    return false;
  }

}
