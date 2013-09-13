package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
//@DiscriminatorValue(value = "2345")
@Access(AccessType.FIELD)
@Table(name = Car.TABLE_NAME)
public class Car extends Vehicle
{
  private static final long  serialVersionUID = 1L;

  public static final String TABLE_NAME       = "JPA_CAR";

  private int                noOfDoors;

  protected Car()
  {
  }

  public Car(String name, int noOfDoors)
  {
    super(name);
    this.noOfDoors = noOfDoors;
  }

  public int getNoOfDoors()
  {
    return this.noOfDoors;
  }

  public void setNoOfDoors(int noOfDoors)
  {
    this.noOfDoors = noOfDoors;
  }
}
