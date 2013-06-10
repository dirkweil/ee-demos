package de.gedoplan.buch.eedemos.ejb.service;

import de.gedoplan.buch.eedemos.ejb.util.LookupHelper;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.BeforeClass;
import org.junit.Test;

public class SchedulerServiceTest
{
  private static Context jndiCtx;
  private static String  lookupName;

  @BeforeClass
  public static void beforeClass() throws Exception
  {
    // Anmeldung am JNDI
    jndiCtx = new InitialContext();

    // Lookup-Namen der EJB ermitteln
    lookupName = LookupHelper.getEjbLookupName(SchedulerService.class, false);
  }

  @Test
  public void testStartStop() throws Exception
  {
    // Lookup der Bean
    SchedulerService schedulerService = (SchedulerService) jndiCtx.lookup(lookupName);

    schedulerService.startTimers();

    Thread.sleep(10000);

    schedulerService.stopTimers();
  }

}
