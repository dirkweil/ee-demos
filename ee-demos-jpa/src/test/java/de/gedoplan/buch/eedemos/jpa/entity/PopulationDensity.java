package de.gedoplan.buch.eedemos.jpa.entity;

public class PopulationDensity
{
  private String name;
  private Number density;

  public PopulationDensity(String name, Number density)
  {
    this.name = name;
    this.density = density;
  }

  public String getName()
  {
    return this.name;
  }

  public Number getDensity()
  {
    return this.density;
  }

  @Override
  public String toString()
  {
    return "PopulationDensity [name=" + this.name + ", density=" + this.density + "]";
  }

}
