package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.baselibs.persistence.entity.SingleIdEntity;
import de.gedoplan.buch.eedemos.jpa.TestBase;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test der Persistence-Fuktionalität bzgl. der Entity Project_xxx.
 * 
 * @author dw
 */
public class ProjectTest extends TestBase
{
  public static Department          testDepartment  = new Department("Enterprise Java Team");
  public static Department[]        testDepartments = { testDepartment };

  public static Project_CompositeId testProject1    = new Project_CompositeId(testDepartment, "ee_demos6", "Java-EE-Demos (v6)", 9000);
  public static Project_EmbeddedId  testProject2    = new Project_EmbeddedId(testDepartment, "ee_demos7", "Java-EE-Demos (v7)", 9000);
  public static Object[]            testProjects    = { testProject1, testProject2 };

  /**
   * Testdaten aufsetzen.
   */
  @BeforeClass
  public static void setup()
  {
    deleteAll(Project_CompositeId.TABLE_NAME, Department.TABLE_NAME);
    insertAll(testDepartments, testProjects);
  }

  /**
   * Alle Testdaten ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er kann probeweise für eine Ausgabe der Testdaten genutzt werden.
   */
  @Test
  //  @Ignore
  public void showAllCompositeId()
  {
    System.out.println("----- showAllCompositeId -----");

    TypedQuery<Project_CompositeId> query = this.entityManager.createQuery("select x from Project_CompositeId x", Project_CompositeId.class);
    showList(query.getResultList());
  }

  /**
   * Alle Testdaten ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er kann probeweise für eine Ausgabe der Testdaten genutzt werden.
   */
  @Test
  //  @Ignore
  public void showAllEmbeddedId()
  {
    System.out.println("----- showAllEmbeddedId -----");

    TypedQuery<Project_EmbeddedId> query = this.entityManager.createQuery("select x from Project_EmbeddedId x", Project_EmbeddedId.class);
    showList(query.getResultList());
  }

  private static void showList(List<? extends SingleIdEntity<?>> list)
  {
    for (SingleIdEntity<?> entry : list)
    {
      System.out.println(entry.toDebugString());
    }
  }
}
