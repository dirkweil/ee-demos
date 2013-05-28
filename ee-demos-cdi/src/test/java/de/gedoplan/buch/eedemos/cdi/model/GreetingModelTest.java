package de.gedoplan.buch.eedemos.cdi.model;

import de.gedoplan.buch.eedemos.cdi.TestBase;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.Test;

public class GreetingModelTest extends TestBase
{
  @Test
  public void testGetHelloWorld()
  {
    GreetingModel greetingModel = BeanProvider.getContextualReference(GreetingModel.class);
    System.out.println(greetingModel.getHelloWorld());
  }
}
