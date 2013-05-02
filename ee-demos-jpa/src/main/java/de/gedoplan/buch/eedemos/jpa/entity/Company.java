package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = Company.TABLE_NAME)
public class Company
{
  public static final String TABLE_NAME = "EEDEMOS_COMPANY";

  @Id
  private Integer            id;
  private String             name;

  @OneToOne
  // @PrimaryKeyJoinColumn
  private CommRegEntry       commRegEntry;

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public CommRegEntry getCommRegEntry()
  {
    return this.commRegEntry;
  }

  public void setCommRegEntry(CommRegEntry commRegEntry)
  {
    this.commRegEntry = commRegEntry;
  }

  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

}
