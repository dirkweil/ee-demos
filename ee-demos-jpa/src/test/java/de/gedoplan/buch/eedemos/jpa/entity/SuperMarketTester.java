package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class SuperMarketTester
{
  public static void main(String[] args)
  {
    insertSuperMarket();
    findSuperMarket();
  }

  public static void insertSuperMarket()
  {
    EntityManager em = JpaUtil.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    tx.begin();

    em.persist(new SuperMarket_EmbeddedId(1, 555, "Tante Emmas Superstore"));
    em.persist(new SuperMarket_CompositeId(1, 555, "Tante Emmas Superstore"));

    tx.commit();

    em.close();

  }

  public static void findSuperMarket()
  {
    EntityManager em = JpaUtil.createEntityManager();

    System.out.println(em.find(SuperMarket_EmbeddedId.class, new BranchId(1, 555)));
    System.out.println(em.find(SuperMarket_CompositeId.class, new BranchId(1, 555)));
  }

}
