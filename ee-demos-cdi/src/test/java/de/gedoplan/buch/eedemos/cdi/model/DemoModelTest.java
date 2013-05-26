package de.gedoplan.buch.eedemos.cdi.model;

import de.gedoplan.buch.eedemos.cdi.TestBase;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.Test;

public class DemoModelTest extends TestBase
{
  @Test
  public void testGetHelloWorld()
  {
    DemoModel demoModel = BeanProvider.getContextualReference(DemoModel.class);
    System.out.println(demoModel.getHelloWorld());
  }
}
