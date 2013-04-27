package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.util.JpaUtil;

import java.util.List;

import javax.persistence.EntityManager;

public class QueryTester
{
  public static void main(String[] args)
  {
    EntityManager em = JpaUtil.createEntityManager();

    StringBuilder jpql = new StringBuilder();
    for (String arg : args)
    {
      jpql.append(arg).append(' ');
    }

    List resultList = em.createQuery(jpql.toString()).getResultList();
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

    em.close();

  }
}
