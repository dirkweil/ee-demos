package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Cacheable(true)
@Access(AccessType.FIELD)
public class OrderLine
{
  @Id
  @GeneratedValue
  private Integer id;
  private String  name;
  private int     count;

  protected OrderLine()
  {
  }

  protected OrderLine(String name, int count)
  {
    this.name = name;
    this.count = count;
  }

  public Integer getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public int getCount()
  {
    return this.count;
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
    final OrderLine other = (OrderLine) obj;

    return this.id != null && this.id.equals(other.id);
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{id=" + this.id + ",name=" + this.name + ",isbn=" + this.count + "}";
  }

}
