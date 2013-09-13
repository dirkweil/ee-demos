package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = SuperMarketEmbeddedId.TABLE_NAME)
public class SuperMarketEmbeddedId
{
  public static final String TABLE_NAME = "JPA_SUPERMARKET";

  @EmbeddedId
  private BranchId           id;

  private String             name;

  protected SuperMarketEmbeddedId()
  {
  }

  public SuperMarketEmbeddedId(int companyId, int branchNo, String name)
  {
    this.id = new BranchId(companyId, branchNo);
    this.name = name;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public BranchId getId()
  {
    return this.id;
  }

  @Override
  public int hashCode()
  {
    return this.id.hashCode();
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
    final SuperMarketEmbeddedId other = (SuperMarketEmbeddedId) obj;
    return this.id.equals(other.id);
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{id=" + this.id + ",name=" + this.name + "}";
  }

}
