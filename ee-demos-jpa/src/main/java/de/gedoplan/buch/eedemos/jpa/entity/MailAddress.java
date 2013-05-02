package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = MailAddress.TABLE_NAME)
public class MailAddress
{
  public static final String TABLE_NAME = "EEDEMOS_MAILADDRESS";

  @Id
  @GeneratedValue
  private Integer            id;
  private String             userId;
  private String             domain;

  public String getUserId()
  {
    return this.userId;
  }

  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  public String getDomain()
  {
    return this.domain;
  }

  public void setDomain(String domain)
  {
    this.domain = domain;
  }

  public Integer getId()
  {
    return this.id;
  }
}
