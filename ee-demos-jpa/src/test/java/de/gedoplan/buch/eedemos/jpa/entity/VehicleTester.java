package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.util.JpaUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class VehicleTester
{
  public static void main(String[] args)
  {
    insertVehicles();
    // showVehicles();
    // showCars();
  }

  public static void insertVehicles()
  {
    EntityManager em = JpaUtil.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Car car = new Car("Peugeot 407SW", 5);
    em.persist(car);

    car = new Car("Fiat Panda", 3);
    em.persist(car);

    Ship ship = new Ship("Queen Mary II", 76000);
    em.persist(ship);

    tx.commit();
    em.close();
  }

  public static void showVehicles()
  {
    EntityManager em = JpaUtil.createEntityManager();
    TypedQuery<Vehicle> q = em.createQuery("select v from Vehicle v", Vehicle.class);

    List<? extends Vehicle> l = q.getResultList();
    for (Vehicle v : l)
    {
      System.out.println(v);
    }
  }

  public static void showCars()
  {
    EntityManager em = JpaUtil.createEntityManager();
    TypedQuery<Car> q = em.createQuery("select c from Car c", Car.class);

    List<Car> l = q.getResultList();
    for (Car c : l)
    {
      System.out.println(c);
    }
  }
}
