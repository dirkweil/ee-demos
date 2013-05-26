package de.gedoplan.buch.eedemos.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class TestBase
{
  protected static CdiContainer cdiContainer;

  @BeforeClass
  public static void beforeClass()
  {
    // JUL in slf4j umleiten (und damit am Ende in log4j). Achtung: Dies ist für Produktion zu inperformant!
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    // CDI-Container starten
    cdiContainer = CdiContainerLoader.getCdiContainer();
    cdiContainer.boot();

    // Standard-Kontexte starten
    ContextControl contextControl = cdiContainer.getContextControl();
    contextControl.startContext(RequestScoped.class);
    contextControl.startContext(SessionScoped.class);
    contextControl.startContext(ConversationScoped.class);
    contextControl.startContext(ApplicationScoped.class);
  }

  @AfterClass
  public static void afterClass()
  {
    cdiContainer.shutdown();
  }

}
