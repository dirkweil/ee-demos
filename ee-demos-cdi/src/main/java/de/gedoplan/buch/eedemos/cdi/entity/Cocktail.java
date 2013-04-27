package de.gedoplan.buch.eedemos.cdi.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

//@Entity
//@Access(AccessType.FIELD)
public class Cocktail implements Comparable<Cocktail>
{
  // @Id
  private String             id;

  private String             name;

  // @ManyToMany
  private Set<CocktailZutat> zutaten = new HashSet<CocktailZutat>();

  // @ManyToOne
  private CocktailZutat      basisZutat;

  public Cocktail(String id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public String getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Set<CocktailZutat> getZutaten()
  {
    return Collections.unmodifiableSet(this.zutaten);
  }

  public void addZutat(CocktailZutat zutat)
  {
    this.zutaten.add(zutat);
    if (this.basisZutat == null)
    {
      this.basisZutat = zutat;
    }
  }

  public CocktailZutat getBasisZutat()
  {
    return this.basisZutat;
  }

  @Override
  public int hashCode()
  {
    return this.id != null ? this.id.hashCode() : 0;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    final Cocktail other = (Cocktail) obj;

    return this.id != null && this.id.equals(other.id);
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{id=" + this.id + ",name=" + this.name + "}";
  }

  protected Cocktail()
  {
  }

  @Override
  public int compareTo(Cocktail other)
  {
    return this.name.compareTo(other.name);
  }

  public boolean isAlcoholic()
  {
    for (CocktailZutat zutat : this.zutaten)
    {
      if (zutat.getVolProz() != 0)
      {
        return true;
      }
    }
    return false;
  }

  public CharSequence getZutatenliste()
  {
    StringBuilder zutatenliste = new StringBuilder();
    String separator = "";
    for (CocktailZutat zutat : this.zutaten)
    {
      zutatenliste.append(separator);
      zutatenliste.append(zutat.getName());
      separator = ", ";
    }
    return zutatenliste;
  }
}
