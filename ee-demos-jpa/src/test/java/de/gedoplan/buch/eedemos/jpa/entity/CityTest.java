package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.TestBase;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

/**
 * Test der Persistence-Fuktionalität bzgl. der Entity City.
 * 
 * @author dw
 */
public class CityTest extends TestBase
{
  public static City   testCity1  = new City("Berlin", 3500000, 892);
  public static City   testCity2  = new City("Bielefeld", 325000, 258);
  public static City   testCity3  = new City("Dortmund", 580000, 280);
  public static City   testCity4  = new City("Mannheim", 315000, 145);
  public static City   testCity5  = new City("Stuttgart", 600000, 207);
  public static City[] testCities = { testCity1, testCity2, testCity3, testCity4, testCity5 };

  /**
   * Testdaten aufsetzen.
   */
  @BeforeClass
  public static void setup()
  {
    deleteAll(City.TABLE_NAME);
    insertAll(testCities);
  }

  /**
   * Alle Testdaten ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er kann probeweise für eine Ausgabe der Testdaten genutzt werden.
   */
  @Test
  //  @Ignore
  public void showAll()
  {
    System.out.println("----- showAll -----");

    TypedQuery<City> query = this.entityManager.createQuery("select x from City x order by x.id", City.class);
    List<City> cities = query.getResultList();
    for (City city : cities)
    {
      System.out.println(city.toDebugString());
    }
  }

  /**
   * Test: Sind die Testdaten korrekt in der DB?
   */
  @Test
  // @Ignore
  public void testFindAll()
  {
    System.out.println("----- testFindAll -----");

    TypedQuery<City> query = this.entityManager.createQuery("select x from City x order by x.name", City.class);
    List<City> cities = query.getResultList();

    Assert.assertEquals("City count", testCities.length, cities.size());
    for (int i = 0; i < testCities.length; ++i)
    {
      City testCity = testCities[i];
      City city = cities.get(i);
      ReflectionAssert.assertReflectionEquals("City", testCity, city);
    }
  }

  /**
   * Demo: Select von Einzelwerten.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er kann probeweise für eine Ausgabe der Daten genutzt werden.
   */
  @Test
  //  @Ignore
  @SuppressWarnings("unchecked")
  public void showPopulationDensity()
  {
    System.out.println("----- showPopulationDensity -----");

    System.out.println("Variante 1 (dynamisch, nur Bielefeld):");
    Query query = this.entityManager.createNativeQuery("SELECT NAME, POPULATION/AREA FROM " + City.TABLE_NAME + " WHERE NAME=?");
    query.setParameter(1, "Bielefeld");
    List<Object[]> resultList = query.getResultList();
    for (Object[] entry : resultList)
    {
      String name = (String) entry[0];
      double populationDensity = ((Number) entry[1]).doubleValue();
      System.out.printf("    %s: %.0f Einw./km²\n", name, populationDensity);
    }

    System.out.println("Variante 2 (NamedNativeQuery):");
    System.out.println(" ");
    query = this.entityManager.createNamedQuery("City_populationDensity");
    resultList = query.getResultList();
    for (Object[] entry : resultList)
    {
      String name = (String) entry[0];
      double populationDensity = ((Number) entry[1]).doubleValue();
      System.out.printf("    %s: %.0f Einw./km²\n", name, populationDensity);
    }
  }

  /**
   * Demo: Select von Einzelwerten.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er kann probeweise für eine Ausgabe der Daten genutzt werden.
   */
  @Test
  //  @Ignore
  @SuppressWarnings("unchecked")
  public void showPopulationDensityCtorResult()
  {
    System.out.println("----- showPopulationDensityCtorResult -----");

    Query query = this.entityManager.createNamedQuery("City_populationDensityCtorResult");
    List<PopulationDensity> resultList = query.getResultList();
    for (PopulationDensity entry : resultList)
    {
      String name = entry.getName();
      double populationDensity = entry.getDensity().doubleValue();
      System.out.printf("    %s: %.0f Einw./km²\n", name, populationDensity);
    }
  }

  /**
   * Demo: Select eine ad-hoc aus Einzelwerten zusammengestellten City.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er kann probeweise für eine Ausgabe der Daten genutzt werden.
   */
  @Test
  //  @Ignore
  @SuppressWarnings("unchecked")
  public void showAdHocCity()
  {
    System.out.println("----- showAdHocCity -----");

    Query query = this.entityManager.createNativeQuery("SELECT 4711 AS ID, 'Essen' AS NAME, 570000 AS POPULATION, 210 AS AREA", City.class);
    List<City> resultList = query.getResultList();
    for (City entry : resultList)
    {
      System.out.println(entry);
    }
  }
}
