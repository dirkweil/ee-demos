package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "2345")
@Access(AccessType.FIELD)
public class Car extends Vehicle
{
  private int noOfDoors;

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

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{" + super.toStringHelper() + ",noOfDoors=" + this.noOfDoors + "}";
  }

}
