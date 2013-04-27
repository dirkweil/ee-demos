package de.gedoplan.buch.eedemos.cdi.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

@Dependent
public class DependentInfoBean implements Serializable
{
  private static int nextNumber = 1;

  private int        dependentNumber;

  public int getDependentNumber()
  {
    return this.dependentNumber;
  }

  @PostConstruct
  public void init()
  {
    this.dependentNumber = nextNumber;
    ++nextNumber;
  }

}
