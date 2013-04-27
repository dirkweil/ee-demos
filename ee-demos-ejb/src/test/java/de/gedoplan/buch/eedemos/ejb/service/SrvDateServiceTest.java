package de.gedoplan.buch.eedemos.ejb.service;

import de.gedoplan.buch.eedemos.ejb.util.LookupHelper;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.BeforeClass;
import org.junit.Test;

public class SrvDateServiceTest
{
  private static Context jndiCtx;
  private static String  lookupName;

  @BeforeClass
  public static void beforeClass() throws Exception
  {
    // Anmeldung am JNDI
    jndiCtx = new InitialContext();

    // Lookup-Namen der EJB ermitteln
    lookupName = LookupHelper.getEjbLookupName(SrvDateService.class, false);
  }

  @Test
  public void testGetSrvDate() throws Exception
  {
    // Lookup der Bean
    SrvDateService srvDateService = (SrvDateService) jndiCtx.lookup(lookupName);

    // Aufruf von Bean-Methoden
    Date srvDate = srvDateService.getSrvDate();

    System.out.println("Date on server: " + srvDate);
  }
}
