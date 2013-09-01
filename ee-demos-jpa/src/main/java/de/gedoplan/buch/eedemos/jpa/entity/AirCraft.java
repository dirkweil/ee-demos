package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = AirCraft.TABLE_NAME)
public class AirCraft extends GeneratedIntegerIdEntity
{
  private static final long  serialVersionUID = 1L;

  public static final String TABLE_NAME       = "EEDEMOS_AIRCRAFT";

  private String             maker;
  private String             type;
  private String             serialNo;

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
}
