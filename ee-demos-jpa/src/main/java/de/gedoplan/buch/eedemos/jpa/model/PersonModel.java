package de.gedoplan.buch.eedemos.jpa.model;

import de.gedoplan.baselibs.faces.validation.FacesValidationHelper;
import de.gedoplan.buch.eedemos.jpa.entity.Person;
import de.gedoplan.buch.eedemos.jpa.entity.Person_;
import de.gedoplan.buch.eedemos.jpa.repository.PersonRepository;
import de.gedoplan.buch.eedemos.jpa.service.ConversationService;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;

/**
 * Präsentationsmodel für Person-bezogene Views.
 * 
 * @author dw
 */
@Model
@ConversationScoped
public class PersonModel implements Serializable
{
  @Inject
  PersonRepository      personRepository;

  @Inject
  ConversationService   conversationService;

  @Inject
  FacesValidationHelper facesValidationHelper;

  @Inject
  Log                   logger;

  List<Person>          personen;

  Person                person;

  /**
   * Alle Personen aus der DB liefern.
   * 
   * @return Personen
   */
  public List<Person> getPersonen()
  {
    if (this.personen == null)
    {
      this.logger.debug("Loading all persons from db");

      this.personen = this.personRepository.findAll();

      for (Person person : this.personen)
      {
        logPerson("  ", person);
      }
    }

    return this.personen;
  }

  /**
   * Aktuell bearbeitete Person liefern.
   * 
   * @return Person
   */
  public Person getPerson()
  {
    return this.person;
  }

  /**
   * Aktionsmethode zum Editieren einer Person.
   * 
   * @param person Person
   * @return "edit"
   */
  public String edit(Person person)
  {
    this.conversationService.beginConversation();

    this.person = this.personRepository.findByIdEager(person.getId());

    logPerson("Edit", this.person);

    return "edit";
  }

  /**
   * Aktionsmethode zum Erzeugen und Editieren einer neuen Person.
   * 
   * @return "edit"
   */
  public String create()
  {
    this.conversationService.beginConversation();

    this.person = new Person(null, null);
    this.logger.debug("Edit new person");

    return "edit";
  }

  /**
   * Aktionsmethode zum Löschen einer Person.
   * 
   * @param person Person
   */
  public void delete(Person person)
  {
    logPerson("Delete", person);

    this.personRepository.remove(person);

    this.personen = null;
  }

  /**
   * Aktionsmethode zum Speichern der aktuell bearbeiteten Person.
   * 
   * @return "ok", falls gespeichert, <code>null</code> im Fehlerfall
   */
  public String save()
  {
    if (this.person != null)
    {
      if (!this.facesValidationHelper.validate(this.person))
      {
        return null;
      }
    }

    logPerson("Save", this.person);

    try
    {
      this.personRepository.merge(this.person);

      this.conversationService.endConversation();
      this.personen = null;

      return "ok";
    }
    catch (Exception e) // CHECKSTYLE:IGNORE
    {
      if (this.facesValidationHelper.convertToFacesMessages(e) == 0)
      {
        throw e;
      }
    }

    return null;
  }

  /**
   * Aktionsmethode zum Abbrechen der Bearbeitung der aktuellen Person.
   * 
   * @return "ok"
   */
  public String cancel()
  {
    this.conversationService.endConversation();
    this.personen = null;

    return "ok";
  }

  private void logPerson(String action, Person person)
  {
    Object attachedEM = "???"; //LogEntityManagerInterceptor.getAttachedEntityManager(person);
    boolean phonesLoaded = Persistence.getPersistenceUtil().isLoaded(person, Person_.phones.getName());
    boolean hobbiesLoaded = Persistence.getPersistenceUtil().isLoaded(person, Person_.hobbies.getName());
    this.logger.debug(action + " " + person.toDebugString() + " (attachedEM=" + attachedEM + ", phonesLoaded=" + phonesLoaded + ", hobbiesLoaded=" + hobbiesLoaded + ")");
  }
}
