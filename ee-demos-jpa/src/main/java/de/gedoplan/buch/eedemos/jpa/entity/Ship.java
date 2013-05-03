package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
//@DiscriminatorValue(value = "34567")
@Access(AccessType.FIELD)
@Table(name = Ship.TABLE_NAME)
public class Ship extends Vehicle
{
  public static final String TABLE_NAME = "EEDEMOS_SHIP";

  private double             tonnage;

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
}
