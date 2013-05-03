package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.TestBase;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

/**
 * Test der Persistence-Fuktionalität bzgl. der Entity Order.
 * 
 * @author dw
 */
public class OrderTest extends TestBase
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
    insertAll(testOrders);
  }

  /**
   * Alle Orders mit den ihnen zugeordneten Orderlines ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er kann probeweise für eine Ausgabe der Testdaten genutzt werden.
   */
  @Test
  //  @Ignore
  public void showOrders()
  {
    System.out.println("----- showOrders -----");

    TypedQuery<Order> query = this.entityManager.createQuery("select x from Order x", Order.class);
    List<Order> orders = query.getResultList();
    for (Order order : orders)
    {
      System.out.println(order.toDebugString());
      for (OrderLine orderLine : order.getOrderLines())
      {
        System.out.println("  " + orderLine.toDebugString());
      }
    }
  }

  /**
   * Test: Sind die Testdaten korrekt in der DB?
   */
  @Test
  //  @Ignore
  public void testFindAll()
  {
    System.out.println("----- testFindAll -----");

    TypedQuery<Order> query = this.entityManager.createQuery("select x from Order x order by x.id", Order.class);
    List<Order> orders = query.getResultList();

    Assert.assertEquals("Order count", testOrders.length, orders.size());
    for (int i = 0; i < testOrders.length; ++i)
    {
      Order testOrder = testOrders[i];
      Order order = orders.get(i);
      ReflectionAssert.assertReflectionEquals("Order", testOrder, order);
    }
  }

  /**
   * Test: Sind nach dem Löschen der Order alle Daten aus der DB weg?
   */
  @Test
  //  @Ignore
  public void testRemove()
  {
    System.out.println("----- testRemove -----");

    Order order = this.entityManager.find(Order.class, testOrder1.getId());
    this.entityManager.remove(order);

    Number orderCount = this.entityManager.createQuery("select count(x) from Order x", Number.class).getSingleResult();
    Assert.assertEquals("Order count", 0, orderCount.intValue());

    Number orderLineCount = this.entityManager.createQuery("select count(x) from OrderLine x", Number.class).getSingleResult();
    Assert.assertEquals("OrderLine count", 0, orderLineCount.intValue());

    this.entityManager.getTransaction().rollback();
  }

  /**
   * Test: Ist nach dem Entfernen einer OrderLine aus der Order der zugehörige DB-Eintrag weg?
   */
  @Test
  //  @Ignore
  public void testReduce()
  {
    System.out.println("----- testReduce -----");

    Order order = this.entityManager.find(Order.class, testOrder1.getId());
    OrderLine orderLine = order.getOrderLines().get(0);
    order.removeOrderLine(orderLine);

    Number orderLineCount = this.entityManager.createQuery("select count(x) from OrderLine x where x.id=:id", Number.class).setParameter("id", orderLine.getId()).getSingleResult();
    Assert.assertEquals("OrderLine count", 0, orderLineCount.intValue());

    this.entityManager.getTransaction().rollback();
  }

}
