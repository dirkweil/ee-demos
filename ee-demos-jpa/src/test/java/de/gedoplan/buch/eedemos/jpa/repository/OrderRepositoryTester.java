package de.gedoplan.buch.eedemos.jpa.repository;

import de.gedoplan.buch.eedemos.jpa.entity.Order;
import de.gedoplan.buch.eedemos.jpa.entity.OrderLine;
import de.gedoplan.buch.eedemos.jpa.util.JpaUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class OrderRepositoryTester
{
  public static void main(String[] args)
  {
    insertTestData();
    showAll();
  }

  private static void insertTestData()
  {
    EntityManager em = JpaUtil.createEntityManager();

    OrderRepository orderRepository = new OrderRepository();
    orderRepository.entityManager = em;

    EntityTransaction tx = em.getTransaction();

    tx.begin();

    Order order = new Order();
    order.addOrderLine("Apfel", 10);
    order.addOrderLine("Pflaume", 50);
    order.addOrderLine("Limette", 5);
    order.addOrderLine("Birne", 30);
    orderRepository.insert(order);

    tx.commit();
    em.close();
  }

  public static void showAll()
  {
    EntityManager entityManager = JpaUtil.createEntityManager();

    OrderRepository orderRepository = new OrderRepository();
    orderRepository.entityManager = entityManager;

    List<Order> orders = orderRepository.findAll();
    for (Order order : orders)
    {
      System.out.println(order);
      for (OrderLine orderLine : order.getOrderLines())
      {
        System.out.println("  " + orderLine);
      }
    }
  }

}
