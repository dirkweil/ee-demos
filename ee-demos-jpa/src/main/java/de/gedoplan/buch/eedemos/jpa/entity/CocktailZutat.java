package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.baselibs.persistence.entity.StringIdEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = CocktailZutat.TABLE_NAME)
public class CocktailZutat extends StringIdEntity
{
  private static final long  serialVersionUID = 1L;

  public static final String TABLE_NAME       = "EEDEMOS_COCKTAILZUTAT";

  private String             name;

  private double             volProz;

  public CocktailZutat(String id, String name, double volProz)
  {
    super(id);
    this.name = name;
    this.volProz = volProz;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public double getVolProz()
  {
    return this.volProz;
  }

  public void setVolProz(double volProz)
  {
    this.volProz = volProz;
  }

  protected CocktailZutat()
  {
  }
}
