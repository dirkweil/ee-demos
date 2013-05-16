package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;
import de.gedoplan.baselibs.utils.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Access(AccessType.FIELD)
@Table(name = Person.TABLE_NAME)
public class Person extends GeneratedIntegerIdEntity
{
  public static final String TABLE_NAME         = "EEDEMOS_PERSON";
  public static final String TABLE_NAME_PHONES  = "EEDEMOS_PERSON_PHONE";
  public static final String TABLE_NAME_HOBBIES = "EEDEMOS_PERSON_HOBBY";

  /**
   * Name.
   */
  private String             name;

  /**
   * Vorname.
   */
  private String             firstname;

  /**
   * (Privat-)Adresse.
   */
  private String             zipCode;
  private String             town;
  private String             street;

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = TABLE_NAME_PHONES)
  @Column(name = "TEL")
  @OrderColumn
  private List<String>       phones;

  /**
   * Hobbies.
   */
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = TABLE_NAME_HOBBIES)
  @Column(name = "HOBBY")
  @OrderColumn
  private List<String>       hobbies;

  /**
   * E-Mail-Adressen.
   */
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "PERSON_ID")
  private List<MailAddress>  mailAddresses;

  public Person(String name, String firstname, MailAddress... mailAddress)
  {
    this.name = name;
    this.firstname = firstname;

    this.phones = new ArrayList<>();
    this.hobbies = new ArrayList<>();

    this.mailAddresses = new ArrayList<MailAddress>();
    for (MailAddress ma : mailAddress)
    {
      this.mailAddresses.add(ma);
    }
  }

  protected Person()
  {
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = interpretEmptyAsNull(name);
  }

  public String getFirstname()
  {
    return this.firstname;
  }

  public void setFirstname(String firstname)
  {
    this.firstname = interpretEmptyAsNull(firstname);
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

  /**
   * Wert liefern: {@link #telefonNummern}.
   * 
   * @return Wert
   */
  public List<String> getPhones()
  {
    return this.phones;
  }

  /**
   * Telefonnummern zeilenweise in einem String liefern.
   * 
   * @return Telefonnummern, getrennt durch '\n'
   */
  @Transient
  public String getPhonesAsString()
  {
    return CollectionUtil.toLines(this.phones);
  }

  /**
   * Telefonnummern setzen anhand eines Strings, in dem die Nummern zeilenweise enthalten sind.
   * 
   * @param s Telefonnummern, getrennt durch '\n'
   */
  public void setPhonesAsString(String s)
  {
    this.phones = CollectionUtil.fromLines(s);
  }

  /**
   * Wert liefern: {@link #hobbies}.
   * 
   * @return Wert
   */
  public List<String> getHobbies()
  {
    return this.hobbies;
  }

  /**
   * Hobbies zeilenweise in einem String liefern.
   * 
   * @return Hobbies, getrennt durch '\n'
   */
  @Transient
  public String getHobbiesAsString()
  {
    return CollectionUtil.toLines(this.hobbies);
  }

  /**
   * Hobbies setzen anhand eines Strings, in dem die Hobbies zeilenweise enthalten sind.
   * 
   * @param s Hobbies, getrennt durch '\n'
   */
  public void setHobbiesAsString(String s)
  {
    this.hobbies = CollectionUtil.fromLines(s);
  }

  /**
   * Mailadressen liefern.
   * 
   * @return Mailadressen
   */
  public List<MailAddress> getMailAddresses()
  {
    return this.mailAddresses;
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
