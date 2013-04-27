package de.gedoplan.buch.eedemos.jpa.helper;

import de.gedoplan.buch.eedemos.jpa.entity.Continent;

public class ContinentDescription
{
  private Continent continent;
  private Number    averagePopulation;

  public ContinentDescription(Continent continent, Number averagePopulation)
  {
    this.continent = continent;
    this.averagePopulation = averagePopulation;
  }

  public Continent getContinent()
  {
    return this.continent;
  }

  public Number getAveragePopulation()
  {
    return this.averagePopulation;
  }

  @Override
  public String toString()
  {
    return "ContinentDescription [continent=" + this.continent + ", averagePopulation=" + this.averagePopulation + "]";
  }
}
