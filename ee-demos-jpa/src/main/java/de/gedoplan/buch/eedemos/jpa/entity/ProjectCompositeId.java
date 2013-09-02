package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.baselibs.persistence.entity.SingleIdEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Access(AccessType.FIELD)
@IdClass(ProjectId.class)
@Table(name = ProjectCompositeId.TABLE_NAME)
public class ProjectCompositeId extends SingleIdEntity<ProjectId>
{
  private static final long  serialVersionUID = 1L;

  public static final String TABLE_NAME       = "EEDEMOS_PROJECT";

  @Id
  @ManyToOne
  private Department         department;

  @Id
  private String             prjId;

  private String             name;

  private double             budget;

  @Transient
  private ProjectId          id;

  public ProjectCompositeId(Department department, String prjId, String name, double budget)
  {
    this.department = department;
    this.prjId = prjId;
    this.name = name;
    this.budget = budget;
  }

  @Override
  public ProjectId getId()
  {
    if (this.id == null)
    {
      this.id = new ProjectId(this.department.getId(), this.prjId);
    }
    return this.id;
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

  protected ProjectCompositeId()
  {
  }
}
