package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "34567")
@Access(AccessType.FIELD)
public class Ship extends Vehicle
{
  private double tonnage;

  protected Ship()
  {
  }

  public Ship(String name, int tonnage)
  {
    super(name);
    this.tonnage = tonnage;
  }

  public double getTonnage()
  {
    return this.tonnage;
  }

  public void setTonnage(double tonnage)
  {
    this.tonnage = tonnage;
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{" + super.toStringHelper() + ",tonnage=" + this.tonnage + "}";
  }

}
