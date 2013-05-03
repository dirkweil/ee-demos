package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.TestBase;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test der Persistence-Fuktionalität bzgl. der Entity SuperMarket_xxx.
 * 
 * @author dw
 */
public class SuperMarketTest extends TestBase
{
  public static SuperMarket_CompositeId testSuperMarket1 = new SuperMarket_CompositeId(1, 555, "Tante Emmas Superstore");
  public static SuperMarket_EmbeddedId  testSuperMarket2 = new SuperMarket_EmbeddedId(1, 556, "Tante Emmas Kiosk");
  public static Object[]                testSuperMarkets = { testSuperMarket1, testSuperMarket2 };

  /**
   * Testdaten aufsetzen.
   */
  @BeforeClass
  public static void setup()
  {
    deleteAll(SuperMarket_CompositeId.TABLE_NAME);
    insertAll(testSuperMarkets);
  }

  /**
   * Alle Testdaten ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er kann probeweise für eine Ausgabe der Testdaten genutzt werden.
   */
  @Test
  //  @Ignore
  public void showAllCompositeId()
  {
    System.out.println("----- showAllCompositeId -----");

    TypedQuery<SuperMarket_CompositeId> query = this.entityManager.createQuery("select x from SuperMarket_CompositeId x", SuperMarket_CompositeId.class);
    showList(query.getResultList());
  }

  /**
   * Alle Testdaten ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er kann probeweise für eine Ausgabe der Testdaten genutzt werden.
   */
  @Test
  //  @Ignore
  public void showAllEmbeddedId()
  {
    System.out.println("----- showAllEmbeddedId -----");

    TypedQuery<SuperMarket_EmbeddedId> query = this.entityManager.createQuery("select x from SuperMarket_EmbeddedId x", SuperMarket_EmbeddedId.class);
    showList(query.getResultList());
  }

  private static void showList(List<?> list)
  {
    for (Object entry : list)
    {
      System.out.println(entry);
    }
  }
}
