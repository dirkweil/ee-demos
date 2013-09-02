package de.gedoplan.buch.eedemos.cdi.beans;

import javax.enterprise.inject.Vetoed;

@Vetoed
public class InactiveBean extends GreetingBean
{
  private static final long serialVersionUID = 1L;

  @Override
  public String getGreeting()
  {
    return "Hi!";
  }

}
