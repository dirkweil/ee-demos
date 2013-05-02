package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.INTEGER)
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("1234")
@Access(AccessType.FIELD)
@Table(name = Vehicle.TABLE_NAME)
public abstract class Vehicle
{
  public static final String TABLE_NAME = "EEDEMOS_VEHICLE";

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Integer            id;
  private String             name;

  protected Vehicle()
  {
  }

  protected Vehicle(String name)
  {
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

  public void setId(Integer id)
  {
    this.id = id;
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

    final Vehicle other = (Vehicle) obj;

    return this.id != null && this.id.equals(other.id);
  }

  protected String toStringHelper()
  {
    return "id=" + this.id + ",name=" + this.name;
  }

}
