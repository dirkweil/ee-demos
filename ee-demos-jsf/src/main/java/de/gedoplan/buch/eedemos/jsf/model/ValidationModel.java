package de.gedoplan.buch.eedemos.jsf.model;

import de.gedoplan.baselibs.enterprise.faces.validation.FacesValidationHelper;
import de.gedoplan.buch.eedemos.jsf.entity.Auto;
import de.gedoplan.buch.eedemos.jsf.repository.AutoRepository;

import java.io.Serializable;

import javax.enterprise.inject.Model;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

import org.apache.commons.logging.Log;

/**
 * Model zur Demonstration verschiedener Verfahren zur feldübergreifenden Validierung.
 * 
 * @author dw
 * 
 */
@Model
public class ValidationModel implements Serializable
{
  @Inject
  private AutoRepository        autoRepository;

  @Inject
  private FacesValidationHelper facesValidationHelper;

  @Inject
  private Log                   log;

  private Auto                  auto = new Auto();

  public Auto getAuto()
  {
    return this.auto;
  }

  /**
   * Beispielhafte Aktionsmethode mit mehreren Validierungsansätzen.
   */
  public void save()
  {
    // Cross Component Validation vor der Verarbeitung
    if (!this.facesValidationHelper.validate(this.auto))
    {
      // Falls nicht valide, ...
      // ... im Kontext eintragen (kann von anderen Methoden wieder abgefragt werden)
      FacesContext facesContext = FacesContext.getCurrentInstance();
      facesContext.validationFailed();

      // ... weitere Verarbeitung abbrechen
      return;
    }

    try
    {
      // Geschäftslogik aufrufen, z. B.:
      this.autoRepository.save(this.auto);

      this.log.debug("Saved: " + this.auto);
    }
    catch (Exception e)
    {
      // Validierungsmeldungen aus Exception holen
      if (this.facesValidationHelper.convertToFacesMessages(e) != 0)
      {
        // Falls Meldungen erzeugt, ...
        // ... im Kontext eintragen (kann von anderen Methoden wieder abgefragt werden)
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.validationFailed();

        // ... weitere Verarbeitung abbrechen
        return;
      }
      else
      {
        // Falls andere Exception, Aufrufer damit betrauen
        throw e;
      }
    }
  }

  /**
   * Feldübergreifende Validierung der Eingabewerte.
   * 
   * Diese Methode muss als Event Listener für den postValidata-Event registriert werden.
   * 
   * @param componentSystemEvent Event
   */
  public void validateCrossComponents(ComponentSystemEvent componentSystemEvent)
  {
    // Falls schon Validierungfehler, nichts mehr prüfen
    FacesContext facesContext = FacesContext.getCurrentInstance();
    if (facesContext.isValidationFailed())
    {
      return;
    }

    // Eingabewerte aus den Komponenten holen
    UIComponent form = componentSystemEvent.getComponent();
    String name = getInputValue(form, "name");
    boolean kombi = getInputValue(form, "kombi");
    int anzahlTueren = getInputValue(form, "anzahlTueren");

    // Komplettobjekt daraus erstellen und prüfen
    Auto auto4validation = new Auto(name, kombi, anzahlTueren);
    if (!this.facesValidationHelper.validate(auto4validation))
    {
      // Falls nicht valide, ...
      // ... im Kontext eintragen (kann von anderen Methoden wieder abgefragt werden)
      facesContext.validationFailed();

      // ... nach Validierungsphase direkt zur Renderphase
      facesContext.renderResponse();
    }
  }

  /**
   * Hilfsmethode zum Auslesen eines Komponentenwertes.
   * 
   * @param <T> Erwarteter Ziel-Typ
   * @param anchorComponent Anker-Komponente, i. d. R. h:form
   * @param componentId Id der Eingabekomponente
   * @return aktueller Wert der Komponente
   */
  @SuppressWarnings("unchecked")
  private <T> T getInputValue(UIComponent anchorComponent, String componentId)
  {
    UIInput component = (UIInput) anchorComponent.findComponent(componentId);
    return (T) component.getValue();
  }

}
