package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Access(AccessType.FIELD)
public class AirCraft
{
  @Id
  @GeneratedValue
  private Integer id;
  private String  maker;
  private String  type;
  private String  serialNo;

  public String getMaker()
  {
    return this.maker;
  }

  public void setMaker(String maker)
  {
    this.maker = maker;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public String getSerialNo()
  {
    return this.serialNo;
  }

  public void setSerialNo(String serialNo)
  {
    this.serialNo = serialNo;
  }

  public Integer getId()
  {
    return this.id;
  }
}
