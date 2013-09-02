package de.gedoplan.buch.eedemos.cdi.model;

import de.gedoplan.buch.eedemos.cdi.beans.GreetingBean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;

@Named
@RequestScoped
public class GreetingModel implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Inject
  private Log               logger;

  @Inject
  //  @Formal
  //  @Greeting(type = GreetingType.NORMAL, additionalInfo = "...")
  private GreetingBean      greetingBean;

  /*
   * Alternativen zur Field Injection: @Inject für GreetingBean oben deaktivieren und Kommentar um Konstruktor *oder* Setter
   * entfernen.
   */

  // @Inject
  // public DemoModel(GreetingBean greetingBean)
  // {
  // this.greetingBean = greetingBean;
  // }

  // @Inject
  // public void setGreetingBean(GreetingBean greetingBean)
  // {
  // this.greetingBean = greetingBean;
  // }

  public String getHelloWorld()
  {
    this.logger.debug("getHelloWorld()");
    return this.greetingBean.getGreeting() + ", dies ist \"Hello, world\" auf CDI-Basis.";
  }
}
