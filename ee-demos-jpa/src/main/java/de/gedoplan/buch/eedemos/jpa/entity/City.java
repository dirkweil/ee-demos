package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@NamedNativeQuery(name = "City_populationDensity", query = "SELECT NAME, POPULATION/AREA AS DENSITY FROM EEDEMOS_CITY", resultSetMapping = "City_populationDensity")
@SqlResultSetMapping(name = "City_populationDensity", columns = { @ColumnResult(name = "NAME"), @ColumnResult(name = "DENSITY") })
@Table(name = City.TABLE_NAME)
public class City extends GeneratedIntegerIdEntity
{
  public static final String TABLE_NAME = "EEDEMOS_CITY";

  // Durch die Ableitung von GeneratedIntegerIdEntity lässt sich dieser Code einsparen
  //  @Id
  //  @GeneratedValue(strategy = GenerationType.TABLE, generator = "cityGenerator")
  //  @TableGenerator(name = "cityGenerator", table = "EEDEMOS_CITY_GEN", pkColumnName = "GENERATOR", valueColumnName = "NEXT_ID", initialValue = 200000)
  //  private Integer            id;
  private String             name;

  private int                population;

  private int                area;

  public City()
  {
  }

  public City(String title, int population, int area)
  {
    this.name = title;
    this.population = population;
    this.area = area;
  }

  @Override
  public Integer getId()
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

  public int getPopulation()
  {
    return this.population;
  }

  public void setPopulation(int population)
  {
    this.population = population;
  }

  public int getArea()
  {
    return this.area;
  }

  public void setArea(int area)
  {
    this.area = area;
  }

  // Durch die Ableitung von GeneratedIntegerIdEntity lässt sich dieser Code einsparen
  //  @Override
  //  public int hashCode()
  //  {
  //    return this.id != null ? this.id.hashCode() : 0;
  //  }
  //
  //  @Override
  //  public boolean equals(Object obj)
  //  {
  //    if (this == obj)
  //    {
  //      return true;
  //    }
  //
  //    if (obj == null)
  //    {
  //      return false;
  //    }
  //
  //    if (getClass() != obj.getClass())
  //    {
  //      return false;
  //    }
  //
  //    final City other = (City) obj;
  //
  //    return this.id != null && this.id.equals(other.id);
  //  }
  //
  //  @Override
  //  public String toString()
  //  {
  //    return "City [id=" + this.id + ", name=" + this.name + ", population=" + this.population + ", area=" + this.area + "]";
  //  }
}
