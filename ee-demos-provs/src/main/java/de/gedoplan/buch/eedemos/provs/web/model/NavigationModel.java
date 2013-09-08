package de.gedoplan.buch.eedemos.provs.web.model;

import de.gedoplan.buch.eedemos.provs.common.service.SessionService;
import de.gedoplan.buch.eedemos.provs.firma.entity.Person;
import de.gedoplan.buch.eedemos.provs.firma.service.PersonService;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 * Controller für Navigation.
 * 
 * @author dw
 */
@ConversationScoped
@Model
public class NavigationModel implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Inject
  private PersonService     personService;

  @Inject
  private SessionService    sessionService;

  /**
   * Aktuell angemeldeten User liefern.
   * 
   * @return angemeldete Person oder <code>null</code>
   */
  public Person getCurrentUser()
  {
    return this.personService.getCurrentUser();
  }

  /**
   * Ist ein aktueller User angemeldet?
   * 
   * @return <code>true</code>, wenn Anmeldung vorhanden
   */
  public boolean isLoggedIn()
  {
    return getCurrentUser() != null;
  }

  /**
   * User abmelden.
   * 
   * @return Outcome "loggedOut"
   */
  public String logOut()
  {
    this.sessionService.logOut();

    return "loggedOut";
  }

  /**
   * Zur angegebenen Haupt-Navigations-Ziel navigieren.
   * 
   * @param outcome Haupt-Navigations-Ziel
   * 
   * @return outcome
   */
  public String navigate(String outcome)
  {
    this.sessionService.endConversation();
    return outcome;
  }

}
