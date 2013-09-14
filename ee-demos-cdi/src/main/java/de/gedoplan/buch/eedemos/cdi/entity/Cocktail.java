package de.gedoplan.buch.eedemos.cdi.entity;

import de.gedoplan.baselibs.persistence.entity.StringIdEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = Cocktail.TABLE_NAME)
public class Cocktail extends StringIdEntity implements Comparable<Cocktail>
{
  private static final long  serialVersionUID      = 1L;

  public static final String TABLE_NAME            = "CDI_COCKTAIL";
  public static final String ZUTATEN_TABLE_NAME    = "CDI_COCKTAIL_COCKTAILZUTAT";
  public static final String BASISZUTAT_TABLE_NAME = "CDI_COCKTAIL_BASISZUTAT";

  private String             name;

  @ManyToMany
  @JoinTable(name = ZUTATEN_TABLE_NAME, joinColumns = @JoinColumn(name = "COCKTAIL_ID"), inverseJoinColumns = @JoinColumn(name = "ZUTAT_ID"))
  private Set<CocktailZutat> zutaten               = new HashSet<CocktailZutat>();

  @ManyToOne
  @JoinTable(name = BASISZUTAT_TABLE_NAME, joinColumns = @JoinColumn(name = "COCKTAIL_ID"), inverseJoinColumns = @JoinColumn(name = "ZUTAT_ID"))
  private CocktailZutat      basisZutat;

  public Cocktail(String id, String name)
  {
    super(id);
    this.name = name;
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

  protected Cocktail()
  {
  }
}
