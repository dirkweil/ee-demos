package de.gedoplan.buch.eedemos.cdi.beans;

import javax.enterprise.inject.Vetoed;

@Vetoed
public class InactiveBean extends GreetingBean
{
  @Override
  public String getGreeting()
  {
    return "Hi!";
  }

}
