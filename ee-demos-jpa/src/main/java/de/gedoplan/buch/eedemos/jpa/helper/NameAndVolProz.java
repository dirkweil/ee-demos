package de.gedoplan.buch.eedemos.jpa.helper;

public class NameAndVolProz
{
  private String name;
  private double volProz;

  public NameAndVolProz(String name, double volProz)
  {
    this.name = name;
    this.volProz = volProz;
  }

  public NameAndVolProz(String name, Double volProz)
  {
    this(name, volProz.doubleValue());
  }

  public String getName()
  {
    return this.name;
  }

  public double getVolProz()
  {
    return this.volProz;
  }

  @Override
  public String toString()
  {
    return "NameAndVolProz [name=" + this.name + ", volProz=" + this.volProz + "]";
  }
}
