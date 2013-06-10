package de.gedoplan.buch.eedemos.ejb.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Access(AccessType.FIELD)
public class ShopItem implements Serializable
{
  @Id
  @GeneratedValue
  private Integer id;

  private String  name;

  private double  price;

  public ShopItem(String name, double price)
  {
    this.name = name;
    this.price = price;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public double getPrice()
  {
    return this.price;
  }

  public void setPrice(double price)
  {
    this.price = price;
  }

  public Integer getId()
  {
    return this.id;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    return result;
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
    ShopItem other = (ShopItem) obj;
    return this.id != null && this.id.equals(other.id);
  }

  @Override
  public String toString()
  {
    return "ShopItem [id=" + this.id + ", name=" + this.name + ", price=" + this.price + "]";
  }

  protected ShopItem()
  {
  }
}
