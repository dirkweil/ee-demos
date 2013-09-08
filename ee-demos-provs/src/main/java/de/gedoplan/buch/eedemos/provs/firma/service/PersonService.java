package de.gedoplan.buch.eedemos.provs.firma.service;

import de.gedoplan.baselibs.enterprise.stereotype.DomainService;
import de.gedoplan.buch.eedemos.provs.common.qualifier.Angemeldet;
import de.gedoplan.buch.eedemos.provs.common.qualifier.Current;
import de.gedoplan.buch.eedemos.provs.common.service.SessionService;
import de.gedoplan.buch.eedemos.provs.firma.entity.Person;
import de.gedoplan.buch.eedemos.provs.firma.repository.PersonRepository;

import java.io.Serializable;
import java.security.Principal;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * Personenservice.
 * 
 * @author dw
 * 
 */
@DomainService
@SessionScoped
public class PersonService implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Inject
  private SessionService    sessionService;

  @Inject
  private PersonRepository  personRepository;

  private Person            currentPerson;

  /**
   * Aktuell angemeldeten User liefern.
   * 
   * @return angemeldete Person oder <code>null</code>
   */
  @Produces
  @Current
  @Angemeldet
  public Person getCurrentUser()
  {
    if (this.currentPerson == null)
    {
      Principal currentPrincipal = this.sessionService.getCurrentUser();
      if (currentPrincipal != null)
      {
        this.currentPerson = this.personRepository.findByUserId(currentPrincipal.getName());
      }
    }
    return this.currentPerson;
  }
}
