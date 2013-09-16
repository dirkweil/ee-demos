package de.gedoplan.buch.eedemos.jsf.model;

import javax.enterprise.inject.Model;

@Model
public class ExceptionDemoModel
{
  public void fail()
  {
    throw new NullPointerException();
  }

}
