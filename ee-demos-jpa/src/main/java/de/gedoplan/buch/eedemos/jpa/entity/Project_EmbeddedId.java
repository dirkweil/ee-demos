package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = Project_EmbeddedId.TABLE_NAME)
public class Project_EmbeddedId
{
  public static final String TABLE_NAME = "EEDEMOS_PROJECT";

  @EmbeddedId
  private ProjectId          id;

  @MapsId("department")
  @ManyToOne
  private Department         department;

  private String             name;

  private double             budget;

  public Project_EmbeddedId(Department department, String prjId, String name, double budget)
  {
    this.id = new ProjectId(department.getId(), prjId);
    this.department = department;
    this.name = name;
    this.budget = budget;
  }

  public Department getDepartment()
  {
    return this.department;
  }

  public String getPrjId()
  {
    return this.id.getPrjId();
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public double getBudget()
  {
    return this.budget;
  }

  public void setBudget(double budget)
  {
    this.budget = budget;
  }

  @Override
  public int hashCode()
  {
    return this.id != null ? this.id.hashCode() : 0;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }

    Project_EmbeddedId other = (Project_EmbeddedId) obj;

    return this.id != null && this.id.equals(other.id);
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{id=" + this.id + ",name=" + this.name + ",budget=" + this.budget + "}";
  }

  protected Project_EmbeddedId()
  {
  }
}
