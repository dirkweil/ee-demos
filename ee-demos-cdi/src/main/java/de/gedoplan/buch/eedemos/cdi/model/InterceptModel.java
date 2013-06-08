package de.gedoplan.buch.eedemos.cdi.model;

import de.gedoplan.baselibs.enterprise.interceptor.TraceCall;
import de.gedoplan.buch.eedemos.cdi.sub1.beans.SomeService;
import de.gedoplan.buch.eedemos.cdi.sub1.interceptor.One;
import de.gedoplan.buch.eedemos.cdi.sub1.interceptor.Three;
import de.gedoplan.buch.eedemos.cdi.sub1.interceptor.Two;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@TraceCall
public class InterceptModel
{
  @Inject
  SomeService someService;

  @One
  @Two
  @Three
  public void doSomething()
  {
    this.someService.doSomething();
  }

}
