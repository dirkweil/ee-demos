package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.baselibs.utils.util.ExceptionUtil;
import de.gedoplan.buch.eedemos.jpa.TestBase;
import de.gedoplan.buch.eedemos.jpa.helper.ContinentDescription;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

/**
 * Test der Persistence-Fuktionalität bzgl. der Entity Country.
 * 
 * @author dw
 */
public class CountryTest extends TestBase
{
  public static Country      testCountryCA   = new Country("CA", "Canada", "1", null, 34_482_779, Continent.NORTH_AMERICA);
  public static Country      testCountryCN   = new Country("CN", "China", "86", null, 1_331_460_000, Continent.ASIA);
  public static Country      testCountryDE   = new Country("DE", "Germany", "49", "D", 81_879_976, Continent.EUROPE);
  public static Country      testCountryIT   = new Country("IT", "Italy", "39", "I", 60_221_210, Continent.EUROPE);
  public static Country      testCountryUS   = new Country("US", "United States of America", "1", null, 305_548_183, Continent.NORTH_AMERICA);
  public static Country      testCountryVN   = new Country("VN", "Vietnam", "84", null, 87_840_000, Continent.ASIA);
  public static Country[]    testCountries   = { testCountryCA, testCountryCN, testCountryDE, testCountryIT, testCountryUS, testCountryVN };

  private static Set<String> removedIsoCodes = new HashSet<>();

  /**
   * Testdaten aufsetzen.
   */
  @BeforeClass
  public static void setup()
  {
    deleteAll(Country.TABLE_2_NAME, Country.TABLE_3_NAME, Country.TABLE_NAME);
    insertAll(testCountries);
  }

  /**
   * Alle Testdaten ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er kann probeweise für eine Ausgabe der Testdaten genutzt werden.
   */
  @Test
  //  @Ignore
  public void showCountries()
  {
    System.out.println("----- showCountries -----");

    TypedQuery<Country> query = this.entityManager.createQuery("select x from Country x order by x.isoCode", Country.class);
    List<Country> countries = query.getResultList();
    for (Country country : countries)
    {
      System.out.println(country);
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

    TypedQuery<Country> query = this.entityManager.createQuery("select x from Country x order by x.isoCode", Country.class);
    List<Country> countries = query.getResultList();

    Assert.assertEquals("Country count", testCountries.length, countries.size());
    for (int i = 0; i < testCountries.length; ++i)
    {
      Country testCountry = testCountries[i];
      Country country = countries.get(i);
      ReflectionAssert.assertReflectionEquals("Country", testCountry, country);
    }
  }

  /**
   * Test: Finden eines Einzeleintrags anhand seiner ID.
   */
  @Test
  // @Ignore
  public void testFindById()
  {
    System.out.println("----- testFindById -----");

    Country testCountry = testCountryIT;
    String isoCode = testCountry.getIsoCode();

    Country country = this.entityManager.find(Country.class, isoCode);
    Assert.assertEquals("Country", testCountry, country);
  }

  /**
   * Test: Ändern eines Einzeleintrags.
   */
  @Test
  // @Ignore
  public void testUpdate()
  {
    System.out.println("----- testUpdate -----");

    Country testCountry = testCountryIT;
    String isoCode = testCountry.getIsoCode();

    Country country = this.entityManager.find(Country.class, isoCode);
    long population = country.getPopulation() + 2;
    country.setPopulation(population);

    this.entityManager.getTransaction().commit();

    this.entityManager.clear();
    country = this.entityManager.find(Country.class, isoCode);
    Assert.assertEquals("Population", population, country.getPopulation());
  }

  /**
   * Test: Löschen eines Einzeleintrags.
   */
  @Test
  // @Ignore
  public void testRemove()
  {
    System.out.println("----- testRemove -----");

    Country testCountry = testCountryCA;
    String isoCode = testCountry.getIsoCode();

    Country country = this.entityManager.find(Country.class, isoCode);
    this.entityManager.remove(country);

    this.entityManager.getTransaction().commit();

    this.entityManager.clear();
    country = this.entityManager.find(Country.class, isoCode);
    Assert.assertNull("Country", country);

    removedIsoCodes.add(isoCode);
  }

  /**
   * Test: Finden eines Einzeleintrags anhand seines carCode (Normale Query mit Positions-Parameter).
   */
  @Test
  // @Ignore
  public void testFindByCarCode()
  {
    System.out.println("----- testFindByCarCode -----");

    Country testCountry = testCountryDE;
    String carCode = testCountry.getCarCode();

    TypedQuery<Country> query = this.entityManager.createQuery("select c from Country c where c.carCode=?1", Country.class);
    query.setParameter(1, carCode);
    Country country = query.getSingleResult();
    Assert.assertEquals("Country", testCountry, country);
  }

  /**
   * Test: Finden eines Einzeleintrags anhand seines phonePrefix (Named Query mit benanntem Parameter).
   */
  @Test
  // @Ignore
  public void testFindByPhonePrefix()
  {
    System.out.println("----- testFindByPhonePrefix -----");

    Country testCountry = testCountryDE;
    String phonePrefix = testCountry.getPhonePrefix();

    TypedQuery<Country> query = this.entityManager.createNamedQuery("Country_findByPhonePrefix", Country.class);
    query.setParameter("phonePrefix", phonePrefix);
    Country country = query.getSingleResult();
    Assert.assertEquals("Country", testCountry, country);
  }

  /**
   * Test: Selektion von Einzelattributen in ein Object[].
   */
  @Test
  // @Ignore
  public void testSelectContinentDescriptionAsObjectArray()
  {
    System.out.println("----- testSelectContinentDescriptionAsObjectArray -----");

    TypedQuery<Object[]> query = this.entityManager.createQuery("select c.continent, avg(c.population) from Country c group by c.continent", Object[].class);
    for (Object[] continentDescription : query.getResultList())
    {
      Continent continent = (Continent) continentDescription[0];
      long averagePopulation = ((Number) continentDescription[1]).longValue();

      Assert.assertEquals("Average Population of " + continent, getAveragePopulation(continent), averagePopulation);
    }
  }

  /**
   * Test: Selektion von Einzelattributen in ein Value Object.
   */
  @Test
  // @Ignore
  public void testSelectContinentDescriptionAsValueObject()
  {
    System.out.println("----- testSelectContinentDescriptionAsValueObject -----");

    TypedQuery<ContinentDescription> query = this.entityManager.createQuery(
        "select new de.gedoplan.buch.eedemos.jpa.helper.ContinentDescription(c.continent, avg(c.population)) from Country c group by c.continent", ContinentDescription.class);
    for (ContinentDescription continentDescription : query.getResultList())
    {
      Continent continent = continentDescription.getContinent();
      Number averagePopulation = continentDescription.getAveragePopulation().longValue();

      Assert.assertEquals("Average Population of " + continent, getAveragePopulation(continent), averagePopulation);
    }
  }

  private static long getAveragePopulation(Continent continent)
  {
    long countryCount = 0;
    long totalPopulation = 0;
    for (Country testCountry : testCountries)
    {
      if (!removedIsoCodes.contains(testCountry.getIsoCode()))
      {
        if (continent.equals(testCountry.getContinent()))
        {
          ++countryCount;
          totalPopulation += testCountry.getPopulation();
        }
      }
    }

    return totalPopulation == 0 ? 0 : totalPopulation / countryCount;
  }

  /**
   * Test: Cachen von Objektem im Second Level Cache.
   */
  @Test
  // @Ignore
  public void testSecondLevelCache()
  {
    System.out.println("----- testSecondLevelCache -----");

    Country testCountry = testCountryIT;
    String isoCode = testCountry.getIsoCode();

    Cache cache = entityManagerFactory.getCache();
    cache.evict(Country.class, isoCode);

    System.out.println("  Erstes find fuehrt zu DB-Zugriff");
    this.entityManager.find(Country.class, isoCode);

    boolean inCache = cache.contains(Country.class, isoCode);
    Assert.assertTrue("Country in 2nd lvl cache", inCache);

    EntityManager entityManager2 = entityManagerFactory.createEntityManager();
    System.out.println("  Zweites find fuehrt zu keinem DB-Zugriff");
    entityManager2.find(Country.class, isoCode);

  }

  /**
   * Test: Verletzen der Validität eines Eintrags.
   */
  @Test
  // @Ignore
  public void testValidationException()
  {
    System.out.println("----- testValidationException -----");

    Country testCountry = testCountryIT;
    String isoCode = testCountry.getIsoCode();

    Country country = this.entityManager.find(Country.class, isoCode);
    country.setName("");

    try
    {
      this.entityManager.getTransaction().commit();
    }
    catch (Exception e)
    {
      IllegalArgumentException expectedException = ExceptionUtil.getException(e, IllegalArgumentException.class);
      Assert.assertNotNull("Validierungs-Exception", expectedException);
      return;
    }

    Assert.fail("Validierung nicht fehlgeschlagen");
  }
}
