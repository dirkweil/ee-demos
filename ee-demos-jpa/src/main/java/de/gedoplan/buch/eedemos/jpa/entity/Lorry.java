package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = Lorry.TABLE_NAME)
public class Lorry extends Car
{
  private static final long  serialVersionUID = 1L;

  public static final String TABLE_NAME       = "EEDEMOS_LORRY";

  /**
   * Nutzlast.
   */
  private double             payLoad;

  /**
   * Konstruktor.
   */
  protected Lorry()
  {
  }

  /**
   * Konstruktor.
   * 
   * @param name
   * @param noOfDoors
   */
  public Lorry(String name, int noOfDoors, double payLoad)
  {
    super(name, noOfDoors);
    this.payLoad = payLoad;
  }

  /**
   * Wert liefern: {@link #payLoad}.
   * 
   * @return Wert
   */
  public double getPayLoad()
  {
    return this.payLoad;
  }

  /**
   * Wert setzen: {@link #payLoad}.
   * 
   * @param payLoad Wert
   */
  public void setPayLoad(double payLoad)
  {
    this.payLoad = payLoad;
  }

}
