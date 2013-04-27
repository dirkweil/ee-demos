package de.gedoplan.baselibs.enterprise.faces.validation;

import java.util.Collection;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

/**
 * Helfer für die (feldübergreifende) Validierung in JSF Beans.
 * 
 * @author dw
 */
@ApplicationScoped
public class FacesValidationHelper
{
  @Inject
  private Validator validator;

  /**
   * Objekt mit BV validieren und Meldungen als FacesMessages im FacesContext eintragen.
   * 
   * @param object zu validierendes Objekt
   * @return <code>true</code>, falls keine Meldungen erzeugt wurden (d. h. das Objekt valide ist)
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public boolean validate(Object object)
  {
    Set<ConstraintViolation<?>> constraintViolations = (Set) this.validator.validate(object);
    return convertToFacesMessages(constraintViolations) == 0;
  }

  /**
   * Ggf. in der Exception enthaltenen BV Constraint Violations als FacesMessages im FacesContext eintragen.
   * 
   * Es wird die gesamte Exception Chain durchsucht, d. h. eine ConstraintViolationException wird auch dann gefunden, wenn sie nur
   * (mittelbare) Cause der übergebenen Exception ist.
   * 
   * @param throwable Exception
   * @return Anzahl eingetragenen Meldungen
   */
  public int convertToFacesMessages(Throwable throwable)
  {
    while (throwable != null)
    {
      if (throwable instanceof ConstraintViolationException)
      {
        return convertToFacesMessages(((ConstraintViolationException) throwable).getConstraintViolations());
      }

      throwable = throwable.getCause();
    }

    return 0;
  }

  /**
   * BV-Validierungsmeldungen als FacesMessages im FacesContext eintragen.
   * 
   * @param constraintViolations Validierungsmeldungen
   * @return Anzahl eingetragenen Meldungen
   */
  private int convertToFacesMessages(Collection<ConstraintViolation<?>> constraintViolations)
  {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    for (ConstraintViolation<?> constraintViolation : constraintViolations)
    {
      FacesMessage msg = new FacesMessage(constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage());
      msg.setSeverity(FacesMessage.SEVERITY_ERROR);
      facesContext.addMessage(null, msg);
    }

    return constraintViolations.size();
  }

}
