package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.helper.ContinentDescription;
import de.gedoplan.buch.eedemos.jpa.util.JpaUtil;

import java.util.List;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class CountryTester
{
  public static void main(String[] args)
  {
    insertCountry();
    // deleteCountry();
    // findCountry();
    // showAll();
    // showByCarCode();
    // showByPhonePrefix();
    // showContinentDescription();
    // cacheDemo();
  }

  public static void insertCountry()
  {
    EntityManager em = JpaUtil.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Country country = new Country("DE", "Germany", "49", "D", 81879976, Continent.EUROPE);
    em.persist(country);
    System.out.println("New entry: " + country);

    country = new Country("IT", "Italy", "39", "I", 60221211, Continent.EUROPE);
    em.persist(country);
    System.out.println("New entry: " + country);

    /*
     * Die folgende Operation würde fehlschlagen, da der Name nicht leer sein darf.
     */
    // country = new Country("AT", "", "31", "A", 8376761, Continent.EUROPE);
    // em.persist(country);
    // System.out.println("New entry: " + country);

    country = new Country("US", "United States of America", "1", null, 305548183, Continent.NORTH_AMERICA);
    em.persist(country);
    System.out.println("New entry: " + country);

    country = new Country("CA", "Canada", "1", null, 33739900, Continent.NORTH_AMERICA);
    em.persist(country);
    System.out.println("New entry: " + country);

    country = new Country("CN", "China", "86", null, 1331460000, Continent.ASIA);
    em.persist(country);
    System.out.println("New entry: " + country);

    tx.commit();
    em.close();

  }

  public static void findCountry()
  {
    String isoCode = "IT";

    EntityManager em = JpaUtil.createEntityManager();

    Country country = em.find(Country.class, isoCode);

    System.out.println("Found: " + country);

    em.close();

  }

  public static void deleteCountry()
  {
    String isoCode = "IT";

    EntityManager em = JpaUtil.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Country country = em.find(Country.class, isoCode);
    if (country != null)
    {
      em.remove(country);
    }

    tx.commit();
    em.close();
  }

  public static void showAll()
  {
    EntityManager em = JpaUtil.createEntityManager();
    TypedQuery<Country> query = em.createQuery("select c from Country c order by c.name", Country.class);

    List<Country> countries = query.getResultList();
    for (Country country : countries)
    {
      System.out.println("Found: " + country);
    }

    // Vor JPA 2.0:
    // Query query = em.createQuery("select c from Country c order by c.name");
    //
    // @SuppressWarnings("unchecked")
    // List<Country> l = query.getResultList();
    // ...
  }

  public static void showByCarCode()
  {
    String carCode = "D";

    EntityManager em = JpaUtil.createEntityManager();
    TypedQuery<Country> query = em.createQuery("select c from Country c where c.carCode=?1", Country.class);
    query.setParameter(1, carCode);

    List<Country> countries = query.getResultList();
    for (Country country : countries)
    {
      System.out.println("Found: " + country);
    }
  }

  public static void showByPhonePrefix()
  {
    String phonePrefix = "49";

    EntityManager em = JpaUtil.createEntityManager();
    TypedQuery<Country> query = em.createNamedQuery("Country_findByPhonePrefix", Country.class);
    query.setParameter(1, phonePrefix);

    List<Country> countries = query.getResultList();
    for (Country country : countries)
    {
      System.out.println("Found: " + country);
    }
  }

  public static void showContinentDescription()
  {
    EntityManager em = JpaUtil.createEntityManager();

    System.out.println("Variante 1 (Object[]):");
    TypedQuery<Object[]> query1 = em.createQuery("select c.continent, avg(c.population) from Country c group by c.continent", Object[].class);
    for (Object[] continentDescription : query1.getResultList())
    {
      Continent continent = (Continent) continentDescription[0];
      Number averagePopulation = (Number) continentDescription[1];
      System.out.println("  " + continent + ": " + averagePopulation);
    }

    System.out.println("Variante 2 (ContinentDescription):");
    TypedQuery<ContinentDescription> query2 = em.createQuery(
        "select new de.gedoplan.buch.eedemos.jpa.helper.ContinentDescription(c.continent, avg(c.population)) from Country c group by c.continent", ContinentDescription.class);
    for (ContinentDescription continentDescription : query2.getResultList())
    {
      Continent continent = continentDescription.getContinent();
      Number averagePopulation = continentDescription.getAveragePopulation();
      System.out.println("  " + continent + ": " + averagePopulation);
    }
  }

  public static void cacheDemo()
  {
    String isoCode = "IT";

    // Eintrag mit ersten EM lesen
    EntityManager em = JpaUtil.createEntityManager();

    Country country = em.find(Country.class, isoCode);

    System.out.println("Found: " + country);

    em.close();

    // EntityManagerFactory emf = em.getEntityManagerFactory();
    EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
    Cache secondLevelCache = emf.getCache();
    System.out.println("Entry is in 2nd lvl cache: " + secondLevelCache.contains(Country.class, isoCode));

    // Eintrag mit zweitem EM lesen
    em = JpaUtil.createEntityManager();

    country = em.find(Country.class, isoCode);

    System.out.println("Found: " + country);

    em.close();

  }

}
