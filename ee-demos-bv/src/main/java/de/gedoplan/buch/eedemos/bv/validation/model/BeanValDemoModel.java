package de.gedoplan.buch.eedemos.bv.validation.model;

import de.gedoplan.buch.eedemos.bv.validation.entity.Fragebogen;
import de.gedoplan.buch.eedemos.bv.validation.service.UmfrageService;
import de.gedoplan.buch.eedemos.bv.validation.util.FragebogenSamples;

import java.io.Serializable;
import java.util.Set;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Model
public class BeanValDemoModel implements Serializable
{
  @Inject
  private Validator     validator;

  private StringBuilder message;

  private Fragebogen    fragebogen = new Fragebogen();

  public StringBuilder getMessage()
  {
    return this.message;
  }

  public Fragebogen getFragebogen()
  {
    return this.fragebogen;
  }

  public void validateFragebogenNull()
  {
    validate(FragebogenSamples.FRAGEBOGEN_NULL);
  }

  public void validateFragebogenLeerName()
  {
    validate(FragebogenSamples.FRAGEBOGEN_LEERNAME);
  }

  public void validateFragebogenTeiladresseInzukunft()
  {
    validate(FragebogenSamples.FRAGEBOGEN_TEILADRESSE_INZUKUNFT);
  }

  public void validateFragebogenOk()
  {
    validate(FragebogenSamples.FRAGEBOGEN_OK);
  }

  /**
   * Validierung eines Objekts.
   * 
   * Objekt und evtl. Validierungsmeldungen werden in message geschrieben.
   * 
   * @param object zu testendes Objekt
   */
  private <T> void validate(T object)
  {
    this.message = new StringBuilder();
    this.message.append(object).append("\n\n");

    Set<ConstraintViolation<T>> constraintViolations = this.validator.validate(object);
    for (ConstraintViolation<T> constraintViolation : constraintViolations)
    {
      this.message.append(constraintViolation.getPropertyPath()).append(' ').append(constraintViolation.getMessage()).append('\n');
    }
  }

  @Inject
  UmfrageService umfrageService;

  public void createUmfrage(int personenZahl)
  {
    this.umfrageService.createUmfrage(personenZahl);
  }
}
