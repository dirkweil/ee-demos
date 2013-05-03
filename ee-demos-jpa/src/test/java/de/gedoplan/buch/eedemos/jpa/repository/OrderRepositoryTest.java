package de.gedoplan.buch.eedemos.jpa.repository;

import de.gedoplan.buch.eedemos.jpa.TestBase;
import de.gedoplan.buch.eedemos.jpa.entity.Order;
import de.gedoplan.buch.eedemos.jpa.entity.OrderLine;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test der Persistence-Fuktionalität bzgl. der Entity Order.
 * 
 * @author dw
 */
public class OrderRepositoryTest extends TestBase
{
  public static Order   testOrder1 = new Order();
  public static Order[] testOrders = { testOrder1 };
  static
  {
    testOrder1.addOrderLine("Apfel", 10);
    testOrder1.addOrderLine("Pflaume", 50);
    testOrder1.addOrderLine("Limette", 5);
    testOrder1.addOrderLine("Birne", 30);
  }

  /**
   * Testdaten aufsetzen.
   */
  @BeforeClass
  public static void setup()
  {
    deleteAll(OrderLine.TABLE_NAME, Order.TABLE_NAME);
  }

  /**
   * Order in DB einfügen.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er dient nur der Demo einer DB-Zugriffsklasse.
   */
  @Test
  //  @Ignore
  public void insertTestData()
  {
    OrderRepository orderRepository = new OrderRepository();
    orderRepository.entityManager = this.entityManager;

    orderRepository.insert(testOrder1);
  }

  /**
   * Alle Orders mit den ihnen zugeordneten Orderlines ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er dient nur der Demo einer DB-Zugriffsklasse.
   */
  @Test
  //  @Ignore
  public void showOrders()
  {
    System.out.println("----- showOrders -----");

    OrderRepository orderRepository = new OrderRepository();
    orderRepository.entityManager = this.entityManager;

    List<Order> orders = orderRepository.findAll();
    for (Order order : orders)
    {
      System.out.println(order.toDebugString());
      for (OrderLine orderLine : order.getOrderLines())
      {
        System.out.println("  " + orderLine.toDebugString());
      }
    }
  }
}
