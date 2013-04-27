package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.util.JpaUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ProjectTester
{
  public static void main(String[] args)
  {
    insertTestData();
    showAll();
  }

  public static void insertTestData()
  {
    EntityManager em = JpaUtil.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    tx.begin();

    Department department = new Department("Enterprise Java Team");
    em.persist(department);

    em.persist(new Project_CompositeId(department, "ee_demos", "Java-EE-Demos", 9000));
    em.persist(new Project_EmbeddedId(department, "ee_demos", "Java-EE-Demos", 9000));

    tx.commit();

    em.close();

  }

  public static void showAll()
  {
    EntityManager em = JpaUtil.createEntityManager();

    showList(em.createQuery("select p from Project_CompositeId p", Project_CompositeId.class).getResultList());
    showList(em.createQuery("select p from Project_EmbeddedId p", Project_EmbeddedId.class).getResultList());

    em.close();
  }

  private static void showList(List<?> list)
  {
    for (Object entry : list)
    {
      System.out.println(entry);
    }
  }
}
