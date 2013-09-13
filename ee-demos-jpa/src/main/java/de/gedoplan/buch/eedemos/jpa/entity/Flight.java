package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = Flight.TABLE_NAME)
public class Flight extends GeneratedIntegerIdEntity
{
  private static final long  serialVersionUID = 1L;

  public static final String TABLE_NAME       = "JPA_FLIGHT";

  private String             carrier;
  private int                flightNo;

  @ManyToOne
  @JoinColumn(name = "PLANE_ID", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
  //  @JoinTable(name = "JPA_F_A", joinColumns = { @JoinColumn(name = "F_ID") }, inverseJoinColumns = { @JoinColumn(name = "A_ID") })
  private AirCraft           airCraft;

  public String getCarrier()
  {
    return this.carrier;
  }

  public void setCarrier(String carrier)
  {
    this.carrier = carrier;
  }

  public int getFlightNo()
  {
    return this.flightNo;
  }

  public void setFlightNo(int flightNo)
  {
    this.flightNo = flightNo;
  }

  public AirCraft getAirCraft()
  {
    return this.airCraft;
  }

  public void setAirCraft(AirCraft airCraft)
  {
    this.airCraft = airCraft;
  }
}
