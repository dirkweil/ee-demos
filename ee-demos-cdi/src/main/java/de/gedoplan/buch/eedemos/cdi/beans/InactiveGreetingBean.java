package de.gedoplan.buch.eedemos.cdi.beans;

import javax.enterprise.inject.Vetoed;

@Vetoed
public class InactiveGreetingBean extends GreetingBean
{
  @Override
  public String getGreeting()
  {
    return "Hi!";
  }

}
