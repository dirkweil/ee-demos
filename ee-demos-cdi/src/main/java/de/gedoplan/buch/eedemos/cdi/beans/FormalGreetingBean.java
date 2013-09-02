package de.gedoplan.buch.eedemos.cdi.beans;

import de.gedoplan.buch.eedemos.cdi.qualifier.Formal;

@Formal
// @Greeting(type = GreetingType.FORMAL)
public class FormalGreetingBean extends GreetingBean
{
  private static final long serialVersionUID = 1L;

  @Override
  public String getGreeting()
  {
    return "Sehr geehrte Damen und Herren";
  }

}
