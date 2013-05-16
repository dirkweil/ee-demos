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
    this.zipCode = interpretEmptyAsNull(zipCode);
  }

  public String getTown()
  {
    return this.town;
  }

  public void setTown(String town)
  {
    this.town = interpretEmptyAsNull(town);
  }

  public String getStreet()
  {
    return this.street;
  }

  public void setStreet(String street)
  {
    this.street = interpretEmptyAsNull(street);
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{zipCode=" + this.zipCode + ",town=" + this.town + ",street=" + this.street + "}";
  }

  public boolean isNull()
  {
    return this.zipCode == null && this.town == null && this.street == null;
  }

  /*
   * Workaround um Bug im GLF 4:javax.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL funktioniert nicht
   * TODO: Raus, wenn Bug gefixt
   */
  private static String interpretEmptyAsNull(String s)
  {
    return s == null || s.isEmpty() ? null : s;
  }
}
