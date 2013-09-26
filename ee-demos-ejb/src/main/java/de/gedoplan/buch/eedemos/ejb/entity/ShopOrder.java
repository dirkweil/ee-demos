package de.gedoplan.buch.eedemos.ejb.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = ShopOrder.TABLE_NAME)
@Access(AccessType.FIELD)
public class ShopOrder implements Serializable
{
  private static final long  serialVersionUID     = 1L;

  public static final String TABLE_NAME           = "EJB_SHOPORDER";
  public static final String TABLE_NAME_SHOPITEMS = "EJB_SHOPORDER_SHOPITEM";

  @Id
  @GeneratedValue
  private Integer            id;

  @ManyToMany
  @JoinTable(name = ShopOrder.TABLE_NAME_SHOPITEMS, joinColumns = @JoinColumn(name = "SHOPORDER_ID"), inverseJoinColumns = @JoinColumn(name = "SHOPITEM_ID"))
  private List<ShopItem>     shopItems            = new ArrayList<>();

  public Integer getId()
  {
    return this.id;
  }

  public List<ShopItem> getShopItems()
  {
    return this.shopItems;
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
    ShopOrder other = (ShopOrder) obj;
    return this.id != null && this.id.equals(other.id);
  }
}
