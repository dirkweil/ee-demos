package de.gedoplan.buch.eedemos.ejb.service;

import de.gedoplan.buch.eedemos.ejb.util.LookupHelper;

import junit.framework.Assert;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.BeforeClass;
import org.junit.Test;

public class DeepThougthServiceTest
{
  private static Context jndiCtx;
  private static String  lookupName;

  @BeforeClass
  public static void beforeClass() throws Exception
  {
    // Anmeldung am JNDI
    jndiCtx = new InitialContext();

    // Lookup-Namen der EJB ermitteln
    lookupName = LookupHelper.getEjbLookupName(DeepThoughtService.class, false);
  }

  @Test(timeout = 20000)
  public void testGetAnswerToQuestionAboutLifeUniverseAndEverything() throws Exception
  {
    // Lookup der Bean
    DeepThoughtService deepThoughtService = (DeepThoughtService) jndiCtx.lookup(lookupName);

    // Aufruf von Bean-Methoden
    Future<String> futureAnswer = deepThoughtService.getAnswerToQuestionAboutLifeUniverseAndEverything();

    while (true)
    {
      try
      {
        String answer = futureAnswer.get(1000, TimeUnit.MILLISECONDS);
        System.out.println(answer);
        Assert.assertEquals("Answer", "Zweiundvierzig", answer);
        break;
      }
      catch (TimeoutException e)
      {
        System.out.println("Thinking ...");
      }
    }
  }

}
