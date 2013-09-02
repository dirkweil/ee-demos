package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.TestBase;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.BeforeClass;
import org.junit.Test;

//CHECKSTYLE:OFF

/**
 * Test der Persistence-Fuktionalität bzgl. der Entity SuperMarket_xxx.
 * 
 * @author dw
 */
public class SuperMarketTest extends TestBase
{
  public static SuperMarketCompositeId testSuperMarket1 = new SuperMarketCompositeId(1, 555, "Tante Emmas Superstore");
  public static SuperMarketEmbeddedId  testSuperMarket2 = new SuperMarketEmbeddedId(1, 556, "Tante Emmas Kiosk");
  public static Object[]               testSuperMarkets = { testSuperMarket1, testSuperMarket2 };

  /**
   * Testdaten aufsetzen.
   */
  @BeforeClass
  public static void setup()
  {
    deleteAll(SuperMarketCompositeId.TABLE_NAME);
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

    TypedQuery<SuperMarketCompositeId> query = this.entityManager.createQuery("select x from SuperMarket_CompositeId x", SuperMarketCompositeId.class);
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

    TypedQuery<SuperMarketEmbeddedId> query = this.entityManager.createQuery("select x from SuperMarket_EmbeddedId x", SuperMarketEmbeddedId.class);
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
