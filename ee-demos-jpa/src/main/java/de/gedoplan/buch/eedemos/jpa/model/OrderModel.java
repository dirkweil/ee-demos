package de.gedoplan.buch.eedemos.jpa.model;

import de.gedoplan.buch.eedemos.jpa.entity.Order;
import de.gedoplan.buch.eedemos.jpa.entity.OrderLine;
import de.gedoplan.buch.eedemos.jpa.repository.OrderRepository;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class OrderModel
{
  @Inject
  private OrderRepository orderRepository;
  private Order           currentOrder;

  public void insertTestData()
  {
    Order order = new Order();
    order.addOrderLine("Apfel", 10);
    order.addOrderLine("Pflaume", 50);
    order.addOrderLine("Limette", 5);
    order.addOrderLine("Birne", 30);
    this.orderRepository.insert(order);
  }

  public Order getCurrentOrder()
  {
    fillCurrentOrder();
    return this.currentOrder;
  }

  public List<OrderLine> getCurrentOrderLines()
  {
    fillCurrentOrder();
    if (this.currentOrder == null)
    {
      return null;
    }
    return this.currentOrder.getOrderLines();
  }

  public void reduceOrder()
  {
    fillCurrentOrder();
    if (this.currentOrder != null)
    {
      int count = this.currentOrder.getOrderLines().size();
      if (count > 0)
      {
        this.currentOrder.removeOrderLine(this.currentOrder.getOrderLines().get(count - 1));
        this.orderRepository.update(this.currentOrder);
      }
    }
  }

  public void deleteOrder()
  {
    fillCurrentOrder();
    this.orderRepository.delete(this.currentOrder.getId());
    this.currentOrder = null;
  }

  private void fillCurrentOrder()
  {
    if (this.currentOrder == null)
    {
      List<Order> orders = this.orderRepository.findAll();
      if (!orders.isEmpty())
      {
        this.currentOrder = orders.get(0);
      }
    }
  }

}
