package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.TestBase;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.BeforeClass;
import org.junit.Test;

//CHECKSTYLE:OFF

/**
 * Test der Persistence-Fuktionalität bzgl. der Entity User.
 * 
 * @author dw
 */
public class UserTest extends TestBase
{
  public static User          testUser1        = new User("ww", "Willi Wacker");
  public static User          testUser2        = new User("hh", "Harry Hirsch");
  public static User[]        testUsers        = { testUser1, testUser2 };

  public static Application   testApplication1 = new Application("Order Entry");
  public static Application   testApplication2 = new Application("Purchasing");
  public static Application[] testApplications = { testApplication1, testApplication2 };
  static
  {
    testUser1.getUsableApplications().add(testApplication1);
    testUser1.getUsableApplications().add(testApplication2);
    testUser2.getUsableApplications().add(testApplication1);
  }

  /**
   * Testdaten aufsetzen.
   */
  @BeforeClass
  public static void setup()
  {
    deleteAll(User.USABLEAPPLICATIONS_TABLE_NAME, User.TABLE_NAME, Application.TABLE_NAME);
    insertAll(testApplications, testUsers);
  }

  /**
   * Alle User mit den ihnen zugeordneten Applikationen ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er kann probeweise für eine Ausgabe der Testdaten genutzt werden.
   */
  @Test
  //  @Ignore
  public void showUsers()
  {
    System.out.println("----- showUsers -----");

    TypedQuery<User> query = this.entityManager.createQuery("select x from User x", User.class);
    List<User> users = query.getResultList();
    for (User user : users)
    {
      System.out.println(user.toDebugString());
      for (Application application : user.getUsableApplications())
      {
        System.out.println("  " + application.toDebugString());
      }
    }
  }

  /**
   * Alle Applikationen mit den ihnen zugeordneten Usern ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er kann probeweise für eine Ausgabe der Testdaten genutzt werden.
   */
  @Test
  //  @Ignore
  public void showApplications()
  {
    System.out.println("----- showApplications -----");

    TypedQuery<Application> query = this.entityManager.createQuery("select x from Application x", Application.class);
    List<Application> applications = query.getResultList();
    for (Application application : applications)
    {
      System.out.println(application.toDebugString());
      for (User user : application.getAuthorizedUsers())
      {
        System.out.println("  " + user.toDebugString());
      }
    }
  }
}
