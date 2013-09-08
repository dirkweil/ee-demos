package de.gedoplan.buch.eedemos.provs.firma.model;

import de.gedoplan.baselibs.faces.validation.FacesValidationHelper;
import de.gedoplan.buch.eedemos.provs.common.repository.RepositoryMaster;
import de.gedoplan.buch.eedemos.provs.common.service.SessionService;
import de.gedoplan.buch.eedemos.provs.firma.bean.PersonLazyDataModel;
import de.gedoplan.buch.eedemos.provs.firma.entity.Person;
import de.gedoplan.buch.eedemos.provs.firma.repository.PersonRepository;

import java.io.Serializable;

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
@Model
public class PersonenVerwaltungModel implements Serializable
{
  private static final long     serialVersionUID = 1L;

  @Inject
  private SessionService        sessionService;

  @Inject
  private PersonRepository      personRepository;

  @Inject
  private RepositoryMaster      repositoryMaster;

  @Inject
  private PersonLazyDataModel   personDataModel;

  @Inject
  private FacesValidationHelper facesValidationHelper;

  /**
   * Aktuelle Person.
   */
  private Person                currentPerson;

  /**
   * Wert liefern: {@link #personDataModel}.
   * 
   * @return Wert
   */
  public PersonLazyDataModel getPersonDataModel()
  {
    return this.personDataModel;
  }

  /**
   * Wert liefern: {@link #currentPerson}.
   * 
   * @return Wert
   */
  public Person getCurrentPerson()
  {
    return this.currentPerson;
  }

  /**
   * Werte setzen: {@link #currentPerson}.
   * 
   * @param currentPerson Wert
   */
  private void setCurrentPerson(Person currentPerson)
  {
    this.currentPerson = currentPerson;
  }

  /**
   * Aktionsmethode zum Anlegen einer Person.
   * 
   * @return Outcome
   */
  public String createPerson()
  {
    this.sessionService.beginConversation();
    setCurrentPerson(Person.createValidInstance());
    this.personRepository.persist(this.currentPerson);
    this.currentPerson.clear();
    return "editPerson";
  }

  /**
   * Aktionsmethode zum Bearbeiten einer Person.
   * 
   * @param person Person
   * @return Outcome
   */
  public String editPerson(Person person)
  {
    this.sessionService.beginConversation();
    setCurrentPerson(this.personRepository.findById(person.getId()));
    return "editPerson";
  }

  /**
   * Aktionsmethode zum Löschen einer Person.
   * 
   * @param person Person
   * @return Outcome
   */
  public String deletePerson(Person person)
  {
    this.sessionService.beginConversation();
    setCurrentPerson(this.personRepository.findById(person.getId()));
    this.personRepository.remove(this.currentPerson);
    return "deletePerson";
  }

  /**
   * Alle Änderungen speichern.
   * 
   * @return Outcome
   */
  public String save()
  {
    if (this.currentPerson != null)
    {
      if (!this.facesValidationHelper.validate(this.currentPerson))
      {
        FacesContext.getCurrentInstance().validationFailed();
        return null;
      }
    }

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
}
