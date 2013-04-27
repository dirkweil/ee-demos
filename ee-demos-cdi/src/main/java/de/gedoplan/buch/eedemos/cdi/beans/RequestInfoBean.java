package de.gedoplan.buch.eedemos.cdi.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RequestInfoBean
{
  private static int nextNumber = 1;

  private int        requestNumber;

  public int getRequestNumber()
  {
    return this.requestNumber;
  }

  @PostConstruct
  public void init()
  {
    this.requestNumber = nextNumber;
    ++nextNumber;
  }

}
