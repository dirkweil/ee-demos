package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = "EEDEMOS_COUNTRY")
@SecondaryTables({ @SecondaryTable(name = "EEDEMOS_COUNTRY_EXT"), @SecondaryTable(name = "EEDEMOS_COUNTRY_EXT2", pkJoinColumns = @PrimaryKeyJoinColumn(name = "IC")) })
@NamedQuery(name = "Country_findByPhonePrefix", query = "select c from Country c where c.phonePrefix=?1")
// @EntityListeners(DebugListener.class)
@Cacheable(true)
public class Country
{
  @Id
  @Column(name = "ISO_CODE", length = 2)
  private String    isoCode;

  @Column(name = "NAME")
  private String    name;

  @Column(table = "EEDEMOS_COUNTRY_EXT", name = "PHONE_PREFIX", length = 5)
  private String    phonePrefix;

  @Column(table = "EEDEMOS_COUNTRY_EXT2", name = "CAR_CODE", length = 3)
  private String    carCode;

  private long      population;

  // @Enumerated(EnumType.STRING)
  private Continent continent;

  protected Country()
  {
  }

  public Country(String isoCode, String name, String phonePrefix, String carCode, long population, Continent continent)
  {
    if (isoCode == null || name == null)
    {
      throw new NullPointerException("isoCode and name must not be null");
    }

    this.isoCode = isoCode;
    this.name = name;
    this.phonePrefix = phonePrefix;
    this.carCode = carCode;
    this.population = population;
    this.continent = continent;
  }

  public String getIsoCode()
  {
    return this.isoCode;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getPhonePrefix()
  {
    return this.phonePrefix;
  }

  public void setPhonePrefix(String phonePrefix)
  {
    this.phonePrefix = phonePrefix;
  }

  public String getCarCode()
  {
    return this.carCode;
  }

  public void setCarCode(String carCode)
  {
    this.carCode = carCode;
  }

  public long getPopulation()
  {
    return this.population;
  }

  public void setPopulation(long population)
  {
    this.population = population;
  }

  public Continent getContinent()
  {
    return this.continent;
  }

  public void setContinent(Continent continent)
  {
    this.continent = continent;
  }

  @Override
  public int hashCode()
  {
    return this.isoCode.hashCode();
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
    final Country other = (Country) obj;
    return this.isoCode.equals(other.isoCode);
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{isoCode=" + this.isoCode + ", name=" + this.name + ", phonePrefix=" + this.phonePrefix + ", carCode=" + this.carCode + ", population=" + this.population
        + ", continent=" + this.continent + "}";
  }

  @PrePersist
  @PreUpdate
  private void validate()
  {
    if (this.name == null || this.name.isEmpty())
    {
      throw new IllegalArgumentException("name darf nicht leer sein");
    }
  }
}
