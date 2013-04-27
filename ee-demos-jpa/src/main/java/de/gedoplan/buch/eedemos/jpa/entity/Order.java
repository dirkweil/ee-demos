package de.gedoplan.buch.eedemos.jpa.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Entity
// Achtung: ORDER ist ein reserviertes Wort in SQL
@Table(name = "ORDERS")
@Access(AccessType.FIELD)
public class Order
{
  @Id
  @GeneratedValue
  private Integer         id;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "ORDER_ID")
  // @OrderBy("name DESC")
  @OrderColumn(name = "ORDERLINES_ORDER")
  private List<OrderLine> orderLines = new ArrayList<OrderLine>();

  public Order()
  {
  }

  public Integer getId()
  {
    return this.id;
  }

  public List<OrderLine> getOrderLines()
  {
    return Collections.unmodifiableList(this.orderLines);
  }

  public void addOrderLine(String name, int count)
  {
    this.orderLines.add(new OrderLine(name, count));
  }

  public void removeOrderLine(OrderLine orderLine)
  {
    this.orderLines.remove(orderLine);
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
    final Order other = (Order) obj;

    return this.id != null && this.id.equals(other.id);
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{id=" + this.id + "}";
  }

}
