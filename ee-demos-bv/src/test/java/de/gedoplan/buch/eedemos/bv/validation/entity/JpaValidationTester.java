package de.gedoplan.buch.eedemos.bv.validation.entity;

import de.gedoplan.buch.eedemos.bv.validation.util.FragebogenSamples;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class JpaValidationTester
{
  public static void main(String[] args)
  {
    EntityManager entityManager = Persistence.createEntityManagerFactory("unittest").createEntityManager();
    entityManager.getTransaction().begin();

    System.out.println(FragebogenSamples.FRAGEBOGEN_OK);
    entityManager.persist(FragebogenSamples.FRAGEBOGEN_OK);
    System.out.println("  OK");

    try
    {
      System.out.println(FragebogenSamples.FRAGEBOGEN_NULL);
      entityManager.persist(FragebogenSamples.FRAGEBOGEN_NULL);
      System.out.println("  OK");
    }
    catch (ConstraintViolationException e)
    {
      Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
      for (ConstraintViolation<?> constraintViolation : constraintViolations)
      {
        System.out.println("  " + constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage());
      }
    }

    entityManager.getTransaction().rollback();
    entityManager.close();
  }

}
