package de.gedoplan.buch.eedemos.cdi.sub.beans;

import de.gedoplan.buch.eedemos.cdi.sub.interceptor.One;
import de.gedoplan.buch.eedemos.cdi.sub.interceptor.Three;
import de.gedoplan.buch.eedemos.cdi.sub.interceptor.Two;

@One
@Two
@Three
public class SomeService
{
  public void doSomething()
  {

  }
}
