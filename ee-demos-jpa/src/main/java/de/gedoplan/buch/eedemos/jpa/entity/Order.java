package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = Order.TABLE_NAME)
public class Order extends GeneratedIntegerIdEntity
{
  private static final long  serialVersionUID = 1L;

  public static final String TABLE_NAME       = "JPA_ORDER";

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "ORDER_ID")
  // @OrderBy("name DESC")
  @OrderColumn(name = "ORDERLINES_ORDER")
  private List<OrderLine>    orderLines       = new ArrayList<OrderLine>();

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
}
