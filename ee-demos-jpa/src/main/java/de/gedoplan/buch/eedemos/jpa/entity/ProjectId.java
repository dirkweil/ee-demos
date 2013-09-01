package de.gedoplan.buch.eedemos.jpa.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class ProjectId implements Serializable
{
  private static final long serialVersionUID = 1L;

  private String            department;
  private String            prjId;

  public ProjectId(String departmentId, String prjId)
  {
    this.department = departmentId;
    this.prjId = prjId;
  }

  public ProjectId()
  {
  }

  public String getDepartmentId()
  {
    return this.department;
  }

  public String getPrjId()
  {
    return this.prjId;
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
    ProjectId other = (ProjectId) obj;
    return this.department.equals(other.department) && this.prjId.equals(other.prjId);
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{department=" + this.department + ",prjId=" + this.prjId + "}";
  }

}
