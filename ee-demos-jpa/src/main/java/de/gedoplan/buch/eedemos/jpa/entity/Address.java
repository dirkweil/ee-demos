package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Address
{
  private String zipCode;
  private String town;
  private String street;

  protected Address()
  {
  }

  public Address(String zipCode, String town, String street)
  {
    this.zipCode = zipCode;
    this.town = town;
    this.street = street;
  }

  public String getZipCode()
  {
    return this.zipCode;
  }

  public void setZipCode(String zipCode)
  {
    this.zipCode = zipCode;
  }

  public String getTown()
  {
    return this.town;
  }

  public void setTown(String town)
  {
    this.town = town;
  }

  public String getStreet()
  {
    return this.street;
  }

  public void setStreet(String street)
  {
    this.street = street;
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{zipCode=" + this.zipCode + ",town=" + this.town + ",street=" + this.street + "}";
  }

}
