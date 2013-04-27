package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.util.JpaUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class User2ApplicationTester
{
  public static void main(String[] args)
  {
    insertTestdata();
    // showUsers();
    // showApplications();
  }

  public static void insertTestdata()
  {
    EntityManager em = JpaUtil.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    User user1 = new User("ww", "Willi Wacker");
    em.persist(user1);

    User user2 = new User("hh", "Harry Hirsch");
    em.persist(user2);

    Application app1 = new Application("Order Entry");
    em.persist(app1);

    Application app2 = new Application("Purchasing");
    em.persist(app2);

    user1.getUsableApplications().add(app1);
    user1.getUsableApplications().add(app2);

    user2.getUsableApplications().add(app1);

    tx.commit();

    em.close();
  }

  public static void showUsers()
  {
    EntityManager em = JpaUtil.createEntityManager();

    List<User> users = em.createQuery("select u from User u", User.class).getResultList();
    for (User user : users)
    {
      System.out.println(user);
      for (Application application : user.getUsableApplications())
      {
        System.out.println("  " + application);
      }
    }
  }

  public static void showApplications()
  {
    EntityManager em = JpaUtil.createEntityManager();

    List<Application> applications = em.createQuery("select a from Application a", Application.class).getResultList();
    for (Application application : applications)
    {
      System.out.println(application);
      for (User user : application.getAuthorizedUsers())
      {
        System.out.println("  " + user);
      }
    }
  }
}
