package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.util.JpaUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class EmployeeTester
{
  public static void main(String[] args)
  {
    insertEmployee();
    searchEmployee();
  }

  public static void insertEmployee()
  {
    EntityManager em = JpaUtil.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    tx.begin();

    Employee employee = new Employee(1, "Harry Hirsch");
    employee.setAddress(new Address("33605", "Bielefeld", "Stieghorster Str. 60"));
    employee.addSkill("Java SE");
    employee.addSkill("Java EE");
    employee.addSkill("JBoss");
    employee.setPhone("WORK", "+49 521 20889-88");
    employee.setPhone("MOBILE", "+49 170 22334455");

    em.persist(employee);

    employee = new Employee(2, "Willi Wacker");
    employee.setAddress(new Address("33605", "Bielefeld", "Stieghorster Str. 60"));
    employee.addSkill("Scrum");
    employee.setPhone("WORK", "+49 521 20889-99");
    employee.setPhone("MOBILE", "+49 170 22334455");

    em.persist(employee);

    tx.commit();

    em.close();

  }

  public static void searchEmployee()
  {
    EntityManager em = JpaUtil.createEntityManager();
    TypedQuery<Employee> query = em.createQuery("select e from Employee e where e.address.zipCode='33605'", Employee.class);

    List<Employee> list = query.getResultList();
    for (Employee e : list)
    {
      System.out.println(e);
    }
  }

}
