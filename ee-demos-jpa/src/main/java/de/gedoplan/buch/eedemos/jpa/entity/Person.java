package de.gedoplan.buch.eedemos.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = Person.TABLE_NAME)
public class Person
{
  public static final String TABLE_NAME    = "EEDEMOS_PERSON";

  @Id
  @GeneratedValue
  private Integer            id;
  private String             name;

  @OneToMany
  @JoinColumn
  private List<MailAddress>  mailAddresses = new ArrayList<MailAddress>();

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Integer getId()
  {
    return this.id;
  }

  public List<MailAddress> getMailAddresses()
  {
    return this.mailAddresses;
  }
}
