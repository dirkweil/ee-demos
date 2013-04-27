package de.gedoplan.buch.eedemos.provs.firma.model;

import de.gedoplan.baselibs.enterprise.faces.validation.FacesValidationHelper;
import de.gedoplan.buch.eedemos.provs.common.repository.RepositoryMaster;
import de.gedoplan.buch.eedemos.provs.common.service.SessionService;
import de.gedoplan.buch.eedemos.provs.firma.bean.FirmaLazyDataModel;
import de.gedoplan.buch.eedemos.provs.firma.entity.Firma;
import de.gedoplan.buch.eedemos.provs.firma.entity.Land;
import de.gedoplan.buch.eedemos.provs.firma.entity.Mitarbeiter;
import de.gedoplan.buch.eedemos.provs.firma.entity.Person;
import de.gedoplan.buch.eedemos.provs.firma.entity.Standort;
import de.gedoplan.buch.eedemos.provs.firma.repository.FirmaRepository;
import de.gedoplan.buch.eedemos.provs.firma.repository.LandRepository;
import de.gedoplan.buch.eedemos.provs.firma.repository.PersonRepository;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * Model für die Personenverwaltung.
 * 
 * @author dw
 */
@ConversationScoped
// @SessionScoped
@Model
public class FirmenVerwaltungModel implements Serializable
{
  @Inject
  private SessionService        sessionService;

  @Inject
  private FirmaRepository       firmaRepository;

  @Inject
  private LandRepository        landRepository;

  @Inject
  private PersonRepository      personRepository;

  @Inject
  private RepositoryMaster      repositoryMaster;

  @Inject
  private FirmaLazyDataModel    firmaDataModel;

  @Inject
  private FacesValidationHelper facesValidationHelper;

  /**
   * Aktuelle Firma.
   */
  private Firma                 currentFirma;

  /**
   * Aktueller Standort.
   */
  private Standort              currentStandort;

  /**
   * Aktueller Mitarbeiter.
   */
  private Mitarbeiter           currentMitarbeiter;

  /**
   * Wert liefern: {@link #firmaDataModel}.
   * 
   * @return Wert
   */
  public FirmaLazyDataModel getFirmaDataModel()
  {
    return this.firmaDataModel;
  }

  /**
   * Alle Länder liefern.
   * 
   * @return alle Länder.
   */
  public List<Land> getLaender()
  {
    return this.landRepository.findAll();
  }

  /**
   * Wert liefern: {@link #currentFirma}.
   * 
   * @return Wert
   */
  public Firma getCurrentFirma()
  {
    return this.currentFirma;
  }

  /**
   * Wert setzen: {@link #currentFirma}.
   * 
   * @param currentFirma Wert
   */
  public void setCurrentFirma(Firma currentFirma)
  {
    this.currentFirma = currentFirma;
  }

  /**
   * Wert liefern: {@link #currentStandort}.
   * 
   * @return Wert
   */
  public Standort getCurrentStandort()
  {
    return this.currentStandort;
  }

  /**
   * Wert setzen: {@link #currentStandort}.
   * 
   * @param currentStandort Wert
   */
  public void setCurrentStandort(Standort currentStandort)
  {
    this.currentStandort = currentStandort;
  }

  /**
   * Wert liefern: {@link #currentMitarbeiter}.
   * 
   * @return Wert
   */
  public Mitarbeiter getCurrentMitarbeiter()
  {
    return this.currentMitarbeiter;
  }

  /**
   * Wert setzen: {@link #currentMitarbeiter}.
   * 
   * @param currentMitarbeiter Wert
   */
  public void setCurrentMitarbeiter(Mitarbeiter currentMitarbeiter)
  {
    this.currentMitarbeiter = currentMitarbeiter;
  }

  /**
   * Aktionsmethode zum Anlegen einer Firma.
   * 
   * @return Outcome
   */
  public String createFirma()
  {
    this.sessionService.beginConversation();
    setCurrentFirma(Firma.createValidInstance());
    this.firmaRepository.persist(this.currentFirma);
    this.currentFirma.clear();
    return "editFirma";
  }

  /**
   * Aktionsmethode zum Bearbeiten einer Firma.
   * 
   * @param firma Firma
   * @return Outcome
   */
  public String editFirma(Firma firma)
  {
    this.sessionService.beginConversation();
    setCurrentFirma(this.firmaRepository.findById(firma.getId()));
    return "editFirma";
  }

  /**
   * Aktionsmethode zum Löschen einer Firma.
   * 
   * @param firma Firma
   * @return Outcome
   */
  public String deleteFirma(Firma firma)
  {
    this.sessionService.beginConversation();
    setCurrentFirma(this.firmaRepository.findById(firma.getId()));
    this.firmaRepository.remove(this.currentFirma);
    return "deleteFirma";
  }

  /**
   * Aktionsmethode zum Anlegen eines Standortes der aktuellen Firma.
   */
  public void createStandort()
  {
    setCurrentStandort(this.currentFirma.addStandort());
  }

  /**
   * Aktionsmethode zum Bearbeiten eines Standortes der aktuellen Firma.
   * 
   * @param standort Standort
   */
  public void editStandort(Standort standort)
  {
    setCurrentStandort(standort);
  }

  /**
   * Aktionsmethode zum Löschen eines Standortes der aktuellen Firma.
   * 
   * @param standort Standort
   */
  public void deleteStandort(Standort standort)
  {
    this.currentFirma.removeStandort(standort);
    setCurrentStandort(null);
  }

  /**
   * Aktionsmethode zum Anlegen eines Mitarbeiters der aktuellen Firma.
   */
  public void createMitarbeiter()
  {
    Mitarbeiter mitarbeiter = this.currentFirma.addMitarbeiter();
    this.setCurrentMitarbeiter(mitarbeiter);
  }

  /**
   * Aktionsmethode zum Bearbeiten eines Mitarbeiters der aktuellen Firma.
   * 
   * @param mitarbeiter Mitarbeiter
   */
  public void editMitarbeiter(Mitarbeiter mitarbeiter)
  {
    setCurrentMitarbeiter(mitarbeiter);
  }

  /**
   * Aktionsmethode zum Löschen eines Mitarbeiters der aktuellen Firma.
   * 
   * @param mitarbeiter Mitarbeiter
   */
  public void deleteMitarbeiter(Mitarbeiter mitarbeiter)
  {
    this.currentFirma.removeMitarbeiter(mitarbeiter);
    setCurrentMitarbeiter(null);
  }

  /**
   * Alle Änderungen speichern.
   * 
   * @return Outcome
   */
  public String save()
  {
    if (this.facesValidationHelper.validate(this.currentFirma))
    {
      try
      {
        this.repositoryMaster.saveAll();
        this.sessionService.endConversation();
        return "saved";
      }
      catch (Throwable e) // CHECKSTYLE:IGNORE
      {
        if (this.facesValidationHelper.convertToFacesMessages(e) == 0)
        {
          throw e;
        }
      }
    }

    FacesContext.getCurrentInstance().validationFailed();
    return null;
  }

  /**
   * Alle Änderungen verwerfen.
   * 
   * @return Outcome
   */
  public String cancel()
  {
    this.sessionService.endConversation();
    return "cancelled";
  }

  /**
   * Personen liefern, deren String-Repräsentation mit dem angegebenen Präfix beginnt.
   * 
   * @param prefix Präfix
   * @return gefundene Personen
   */
  public List<Person> completePerson(String prefix)
  {
    return this.personRepository.findByToStringPrefix(prefix);
  }

}
