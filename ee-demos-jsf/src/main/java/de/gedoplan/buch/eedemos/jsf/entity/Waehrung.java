package de.gedoplan.buch.eedemos.jsf.entity;

public class Waehrung
{
  private String id;
  private double euroValue;

  public Waehrung(String id, double euroValue)
  {
    this.id = id;
    this.euroValue = euroValue;
  }

  public String getId()
  {
    return this.id;
  }

  public double getEuroValue()
  {
    return this.euroValue;
  }

}
