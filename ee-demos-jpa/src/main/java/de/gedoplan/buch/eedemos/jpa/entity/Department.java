package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.baselibs.persistence.entity.UuidEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = Department.TABLE_NAME)
public class Department extends UuidEntity
{
  public static final String TABLE_NAME = "EEDEMOS_DEPARTMENT";

  private String             name;

  public Department(String name)
  {
    this.name = name;
  }

  protected Department()
  {
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{id=" + this.id + ",name=" + this.name + "}";
  }
}
