package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.TestBase;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

//CHECKSTYLE:OFF

/**
 * Test der Persistence-Fuktionalität bzgl. der Entity Employee.
 * 
 * @author dw
 */
public class EmployeeTest extends TestBase
{
  public static Employee   testEmployee1 = new Employee(1, "Harry Hirsch");
  static
  {
    testEmployee1.setAddress(new Address("33605", "Bielefeld", "Stieghorster Str. 60"));
    testEmployee1.addSkill("Java SE");
    testEmployee1.addSkill("Java EE");
    testEmployee1.addSkill("JBoss");
    testEmployee1.setPhone("WORK", "+49 521 20889-88");
    testEmployee1.setPhone("MOBILE", "+49 170 22334455");
  }

  public static Employee   testEmployee2 = new Employee(2, "Willi Wacker");
  static
  {
    testEmployee2.setAddress(new Address("33605", "Bielefeld", "Stieghorster Str. 60"));
    testEmployee2.addSkill("Scrum");
    testEmployee2.setPhone("WORK", "+49 521 20889-99");
    testEmployee2.setPhone("MOBILE", "+49 170 22334455");
  }

  public static Employee[] testEmployees = { testEmployee1, testEmployee2 };

  /**
   * Testdaten aufsetzen.
   */
  @BeforeClass
  public static void setup()
  {
    deleteAll(Employee.SKILLS_TABLE_NAME, Employee.PHONES_TABLE_NAME, Employee.TABLE_NAME);
    insertAll(testEmployees);
  }

  /**
   * Alle Testdaten ausgeben.
   * 
   * Dies ist kein Unit-Test im eigentlichen Sinne. Er kann probeweise für eine Ausgabe der Testdaten genutzt werden.
   */
  @Test
  //  @Ignore
  public void showAll()
  {
    System.out.println("----- showAll -----");

    TypedQuery<Employee> query = this.entityManager.createQuery("select x from Employee x order by x.id", Employee.class);
    List<Employee> employees = query.getResultList();
    for (Employee employee : employees)
    {
      System.out.println(employee);
    }
  }

  /**
   * Test: Sind die Testdaten korrekt in der DB?
   */
  @Test
  // @Ignore
  public void testFindAll()
  {
    System.out.println("----- testFindAll -----");

    TypedQuery<Employee> query = this.entityManager.createQuery("select x from Employee x order by x.id", Employee.class);
    List<Employee> employees = query.getResultList();

    Assert.assertEquals("Employee count", testEmployees.length, employees.size());
    for (int i = 0; i < testEmployees.length; ++i)
    {
      Employee testEmployee = testEmployees[i];
      Employee employee = employees.get(i);
      ReflectionAssert.assertReflectionEquals("Employee", testEmployee, employee);
    }
  }

  /**
   * Test: Query nach einer PLZ.
   */
  @Test
  // @Ignore
  public void testFindByZipCode()
  {
    System.out.println("----- testFindByZipCode -----");

    String zipCode = "33605";
    TypedQuery<Employee> query = this.entityManager.createQuery("select e from Employee e where e.address.zipCode=:zipCode order by e.id", Employee.class);
    query.setParameter("zipCode", zipCode);
    List<Employee> employees = query.getResultList();

    int j = 0;
    for (int i = 0; i < testEmployees.length; ++i)
    {
      Employee testEmployee = testEmployees[i];
      if (zipCode.equals(testEmployee.getAddress().getZipCode()))
      {
        Employee employee = employees.get(j);
        ReflectionAssert.assertReflectionEquals("Employee", testEmployee, employee);

        ++j;
      }
    }
    Assert.assertEquals("Employee count", j, employees.size());
  }
}
