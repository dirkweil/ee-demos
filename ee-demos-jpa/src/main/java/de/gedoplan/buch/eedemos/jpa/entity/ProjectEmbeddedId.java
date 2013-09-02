package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.baselibs.persistence.entity.SingleIdEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = ProjectEmbeddedId.TABLE_NAME)
public class ProjectEmbeddedId extends SingleIdEntity<ProjectId>
{
  private static final long  serialVersionUID = 1L;

  public static final String TABLE_NAME       = "EEDEMOS_PROJECT";

  @EmbeddedId
  private ProjectId          id;

  @MapsId("department")
  @ManyToOne
  private Department         department;

  private String             name;

  private double             budget;

  public ProjectEmbeddedId(Department department, String prjId, String name, double budget)
  {
    this.id = new ProjectId(department.getId(), prjId);
    this.department = department;
    this.name = name;
    this.budget = budget;
  }

  @Override
  public ProjectId getId()
  {
    return this.id;
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

  protected ProjectEmbeddedId()
  {
  }
}
