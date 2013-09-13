package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = CommRegEntry.TABLE_NAME)
public class CommRegEntry
{
  public static final String TABLE_NAME = "JPA_COMMREGENTRY";

  @Id
  private Integer            id;

  private String             legalForm;

  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getLegalForm()
  {
    return this.legalForm;
  }

  public void setLegalForm(String legalForm)
  {
    this.legalForm = legalForm;
  }

}
