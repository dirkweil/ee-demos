package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.buch.eedemos.jpa.TestBase;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test der Persistence-Fuktionalität bzgl. der Entity SuperMarket_xxx.
 * 
 * @author dw
 */
public class VehicleTest extends TestBase
{
  protected static Car       testCar1     = new Car("Smart", 3);
  protected static Car       testCar2     = new Car("Golf", 5);
  protected static Lorry     testLorry1   = new Lorry("Actros", 2, 35);
  protected static Ship      testShip1    = new Ship("Norwegian Breakaway", 146600);
  protected static Ship      testShip2    = new Ship("Disney Fantasy", 130000);
  // Achtung: testVehicles müssen aufsteigend bzgl. der ID angeordnet sein
  protected static Car[]     testCars     = { testCar1, testCar2, testLorry1 };
  protected static Lorry[]   testLorries  = { testLorry1 };
  protected static Ship[]    testShips    = { testShip1, testShip2 };
  protected static Vehicle[] testVehicles = { testCar1, testCar2, testLorry1, testShip1, testShip2 };

  /**
   * Testdaten aufsetzen.
   */
  @BeforeClass
  public static void setup()
  {
    deleteAll(Lorry.TABLE_NAME, Car.TABLE_NAME, Ship.TABLE_NAME, Vehicle.TABLE_NAME);
    insertAll(testVehicles);
  }

  @Test
  // @Ignore
  public void testfindVehicles()
  {
    testFindEntries(Vehicle.class, testVehicles);
  }

  @Test
  // @Ignore
  public void testFindCars()
  {
    testFindEntries(Car.class, testCars);
  }

  @Test
  // @Ignore
  public void testFindLorries()
  {
    testFindEntries(Lorry.class, testLorries);
  }

  @Test
  // @Ignore
  public void testFindShips()
  {
    testFindEntries(Ship.class, testShips);
  }

  public <E extends Vehicle> void testFindEntries(Class<E> entityClass, E[] testEntities)
  {
    List<E> entities = this.entityManager.createQuery("select v from " + entityClass.getSimpleName() + " v order by v.id", entityClass).getResultList();
    Assert.assertEquals("Anzahl Einträge des Typs " + entityClass.getSimpleName(), testEntities.length, entities.size());

    for (int i = 0; i < testEntities.length; ++i)
    {
      Assert.assertEquals(entityClass.getSimpleName() + "[" + i + "]", testEntities[i], entities.get(i));
    }
  }

  @Test
  //  @Ignore
  public void testFindCarsExactly()
  {
    List<Car> entities = this.entityManager.createQuery("select v from Car v where type(v)=Car order by v.id", Car.class).getResultList();
    for (Car car : entities)
    {
      Assert.assertEquals("Type", Car.class, car.getClass());
    }
  }

  @Test
  //  @Ignore
  public void testFindLargeVehicles()
  {
    List<Vehicle> entities = this.entityManager.createQuery("select v from Vehicle v where treat(v as Lorry).payLoad>30 or treat(v as Ship).tonnage>130000 order by v.id", Vehicle.class).getResultList();
    for (Vehicle vehicle : entities)
    {
      System.out.println(vehicle.toDebugString());
    }
  }
}
