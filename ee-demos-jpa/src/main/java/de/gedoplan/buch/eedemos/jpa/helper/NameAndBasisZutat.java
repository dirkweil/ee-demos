package de.gedoplan.buch.eedemos.jpa.helper;

public class NameAndBasisZutat
{
  private String name;
  private String basisZutat;

  public NameAndBasisZutat(String name, String basisZutat)
  {
    this.name = name;
    this.basisZutat = basisZutat;
  }

  public String getName()
  {
    return this.name;
  }

  public String getBasisZutat()
  {
    return this.basisZutat;
  }

  @Override
  public String toString()
  {
    return "NameAndBasisZutat [name=" + this.name + ", basisZutat=" + this.basisZutat + "]";
  }

}
