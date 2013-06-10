package de.gedoplan.buch.eedemos.cdi.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationInfoBean
{
  private static int nextNumber = 1;

  private int        applicationNumber;

  public int getApplicationNumber()
  {
    return this.applicationNumber;
  }

  @PostConstruct
  public void init()
  {
    this.applicationNumber = nextNumber;
    ++nextNumber;
  }
}
