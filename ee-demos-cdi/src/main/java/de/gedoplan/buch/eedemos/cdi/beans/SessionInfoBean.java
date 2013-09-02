package de.gedoplan.buch.eedemos.cdi.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class SessionInfoBean implements Serializable
{
  private static final long serialVersionUID = 1L;

  private static int        nextNumber       = 1;

  private int               sessionNumber;

  public int getSessionNumber()
  {
    return this.sessionNumber;
  }

  @PostConstruct
  public void init()
  {
    this.sessionNumber = nextNumber;
    ++nextNumber;
  }

}
