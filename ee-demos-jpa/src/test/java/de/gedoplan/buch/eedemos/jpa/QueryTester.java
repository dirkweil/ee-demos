package de.gedoplan.buch.eedemos.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Testprogramm zum Ausprobieren von JPQL-Queries.
 * 
 * Die Klasse kann auf der KOmmandozeile mit einem JPQL-Text als Parameter aufgerufen werden. Die Query wird durchgeführt und das
 * Ergebnis angezeigt.
 * 
 * @author dw
 */
public class QueryTester
{
  public static void main(String[] args)
  {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    StringBuilder jpql = new StringBuilder();
    for (String arg : args)
    {
      jpql.append(arg).append(' ');
    }

    Query query = entityManager.createQuery(jpql.toString());
    printResultList(query);
  }

  public static void printResultList(Query query)
  {
    printResultList(query.getResultList());
  }

  public static void printResultList(List<?> resultList)
  {
    for (Object result : resultList)
    {
      if (result instanceof Object[])
      {
        char delim = '[';
        for (Object entry : (Object[]) result)
        {
          System.out.print(delim);
          System.out.print(entry);
          delim = ',';
        }
        System.out.println("]");
      }
      else
      {
        System.out.println(result);
      }
    }
  }
}
