package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@IdClass(ProjectId.class)
@Table(name = Project_CompositeId.TABLE_NAME)
public class Project_CompositeId
{
  public static final String TABLE_NAME = "EEDEMOS_PROJECT";

  @Id
  @ManyToOne
  private Department         department;

  @Id
  private String             prjId;

  private String             name;

  private double             budget;

  public Project_CompositeId(Department department, String prjId, String name, double budget)
  {
    this.department = department;
    this.prjId = prjId;
    this.name = name;
    this.budget = budget;
  }

  public Department getDepartment()
  {
    return this.department;
  }

  public String getPrjId()
  {
    return this.prjId;
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
    final int prime = 31;
    int result = 1;
    result = prime * result + this.department.hashCode();
    result = prime * result + this.prjId.hashCode();
    return result;
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

    Project_CompositeId other = (Project_CompositeId) obj;

    return this.department.equals(other.department) && this.prjId.equals(other.prjId);
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{department=" + this.department + ",prjId=" + this.prjId + ",name=" + this.name + ",budget=" + this.budget + "}";
  }

  protected Project_CompositeId()
  {
  }
}
