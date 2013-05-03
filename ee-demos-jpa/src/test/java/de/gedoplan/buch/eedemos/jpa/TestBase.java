package de.gedoplan.buch.eedemos.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;

public abstract class TestBase
{
  protected static EntityManagerFactory entityManagerFactory;
  protected EntityManager               entityManager;

  protected Log                         log = LogFactory.getLog(getClass());

  @Before
  public void before()
  {
    this.entityManager = getEntityManagerFactory().createEntityManager();
    this.entityManager.getTransaction().begin();
  }

  @After
  public void after()
  {
    try
    {
      EntityTransaction transaction = this.entityManager.getTransaction();
      if (transaction.isActive())
      {
        if (!transaction.getRollbackOnly())
        {
          transaction.commit();
        }
        else
        {
          transaction.rollback();
        }
      }
    }
    catch (Exception e)
    {
      // ignore
    }

    try
    {
      this.entityManager.close();
    }
    catch (Exception e)
    {
      // ignore
    }
  }

  protected static EntityManagerFactory getEntityManagerFactory()
  {
    if (entityManagerFactory == null)
    {
      entityManagerFactory = Persistence.createEntityManagerFactory("test");
    }
    return entityManagerFactory;
  }

  protected static void deleteAll(String... tableName)
  {
    EntityManager entityManager = getEntityManagerFactory().createEntityManager();
    entityManager.getTransaction().begin();

    try
    {
      for (String t : tableName)
      {
        entityManager.createNativeQuery("delete from " + t).executeUpdate();
      }

      entityManager.getTransaction().commit();
    }
    finally
    {
      try
      {
        entityManager.close();
      }
      catch (Exception e)
      {
        // ignore
      }
    }
  }

  protected static void insertAll(Object[]... entities)
  {
    EntityManager entityManager = getEntityManagerFactory().createEntityManager();
    entityManager.getTransaction().begin();

    try
    {
      for (Object[] e : entities)
      {
        insertTestData(entityManager, e);
      }

      entityManager.getTransaction().commit();
    }
    finally
    {
      try
      {
        entityManager.close();
      }
      catch (Exception e)
      {
        // ignore
      }
    }
  }

  private static void insertTestData(EntityManager entityManager, Object[] entities)
  {
    for (Object entity : entities)
    {
      entityManager.persist(entity);
    }
  }

}
