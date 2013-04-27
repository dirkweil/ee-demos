package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

@Entity
@Access(AccessType.FIELD)
public class Flight
{
  @Id
  @GeneratedValue
  private Integer  id;
  private String   carrier;
  private int      flightNo;

  @ManyToOne
  // @JoinColumn(name = "PLANE_ID")
  @JoinTable(name = "F_A_LINK", joinColumns = { @JoinColumn(name = "F_ID") }, inverseJoinColumns = { @JoinColumn(name = "A_ID") })
  private AirCraft airCraft;

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

  public Integer getId()
  {
    return this.id;
  }

}
