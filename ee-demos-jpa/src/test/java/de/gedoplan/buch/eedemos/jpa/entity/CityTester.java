package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.util.JpaUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class CityTester
{
  public static void main(String[] args)
  {
    insertTestData();
    // showPopulationDensity();
    // importCity();
  }

  public static void insertTestData()
  {
    EntityManager em = JpaUtil.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    City city = new City("Berlin", 3500000, 892);
    em.persist(city);
    System.out.println("Neue City: " + city);

    city = new City("Bielefeld", 325000, 258);
    em.persist(city);
    System.out.println("Neue City: " + city);

    city = new City("Dortmund", 580000, 280);
    em.persist(city);
    System.out.println("Neue City: " + city);

    city = new City("Stuttgart", 600000, 207);
    em.persist(city);
    System.out.println("Neue City: " + city);

    city = new City("Mannheim", 315000, 145);
    em.persist(city);
    System.out.println("Neue City: " + city);

    tx.commit();
    em.close();
  }

  @SuppressWarnings("unchecked")
  public static void showPopulationDensity()
  {
    EntityManager em = JpaUtil.createEntityManager();

    System.out.println("Variante 1 (dynamisch, nur Bielefeld):");
    Query query = em.createNativeQuery("SELECT NAME, POPULATION/AREA FROM CITY WHERE NAME=?");
    query.setParameter(1, "Bielefeld");
    List<Object[]> resultList = query.getResultList();
    for (Object[] entry : resultList)
    {
      String name = (String) entry[0];
      double populationDensity = ((Number) entry[1]).doubleValue();
      System.out.printf("  %s: %.0f Einw./km²\n", name, populationDensity);
    }

    System.out.println("Variante 2 (named, alle):");
    query = em.createNamedQuery("City_populationDensity");
    resultList = query.getResultList();
    for (Object[] entry : resultList)
    {
      String name = (String) entry[0];
      double populationDensity = ((Number) entry[1]).doubleValue();
      System.out.printf("  %s: %.0f Einw./km²\n", name, populationDensity);
    }

    em.close();
  }

  @SuppressWarnings("unchecked")
  public static void importCity()
  {
    EntityManager em = JpaUtil.createEntityManager();

    Query query = em.createNativeQuery("SELECT 4711 AS ID, 'Essen' AS NAME, 570000 AS POPULATION, 210 AS AREA", City.class);
    List<City> resultList = query.getResultList();
    for (City entry : resultList)
    {
      System.out.println(entry);
    }

    em.close();
  }
}
