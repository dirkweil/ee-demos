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

@Entity
@Access(AccessType.FIELD)
public class Person
{
  @Id
  @GeneratedValue
  private Integer           id;
  private String            name;

  @OneToMany
  @JoinColumn
  private List<MailAddress> mailAddresses = new ArrayList<MailAddress>();

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
