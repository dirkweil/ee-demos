package de.gedoplan.buch.eedemos.cdi.sub1.beans;

import de.gedoplan.buch.eedemos.cdi.sub1.interceptor.One;
import de.gedoplan.buch.eedemos.cdi.sub1.interceptor.Three;
import de.gedoplan.buch.eedemos.cdi.sub1.interceptor.Two;

@One
@Two
@Three
public class SomeService
{
  public void doSomething()
  {

  }
}
