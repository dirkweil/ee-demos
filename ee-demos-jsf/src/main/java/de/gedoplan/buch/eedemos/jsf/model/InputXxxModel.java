package de.gedoplan.buch.eedemos.jsf.model;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class InputXxxModel implements Serializable
{
  private static final long serialVersionUID = 1L;

  private String            someString       = "some string";

  public String getSomeString()
  {
    return this.someString;
  }

  public void setSomeString(String someString)
  {
    this.someString = someString;
  }

}
