package de.gedoplan.buch.eedemos.cdi.model;

import de.gedoplan.baselibs.enterprise.interceptor.TraceCall;
import de.gedoplan.buch.eedemos.cdi.beans.GreetingBean;
import de.gedoplan.buch.eedemos.cdi.qualifier.Formal;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;

@Named
@RequestScoped
public class DemoModel implements Serializable
{
  @Inject
  private Log          logger;

  @Inject
  @Formal
  // @Greeting(type = GreetingType.NORMAL, additionalInfo = "...")
  private GreetingBean greetingBean;

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

  @Inject
  // @TempDb
  private Connection dbConnection;

  public String getDbMake()
  {
    try
    {
      DatabaseMetaData metaData = this.dbConnection.getMetaData();
      return metaData.getDatabaseProductName() + " " + metaData.getDatabaseProductVersion();
    }
    catch (SQLException e)
    {
      return e.toString();
    }
  }

  @TraceCall
  public String doSomething()
  {
    return "OK";
  }
}
