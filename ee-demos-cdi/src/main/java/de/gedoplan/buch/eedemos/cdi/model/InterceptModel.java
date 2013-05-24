package de.gedoplan.buch.eedemos.cdi.model;

import de.gedoplan.baselibs.enterprise.interceptor.TraceCall;
import de.gedoplan.buch.eedemos.cdi.sub.beans.SomeService;
import de.gedoplan.buch.eedemos.cdi.sub.interceptor.One;
import de.gedoplan.buch.eedemos.cdi.sub.interceptor.Three;
import de.gedoplan.buch.eedemos.cdi.sub.interceptor.Two;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class InterceptModel
{
  @Inject
  SomeService someService;

  @One
  @Two
  @Three
  @TraceCall
  public void doSomething()
  {
    this.someService.doSomething();
  }

}
