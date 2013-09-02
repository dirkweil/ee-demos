package de.gedoplan.buch.eedemos.jpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@IdClass(BranchId.class)
@Table(name = SuperMarketCompositeId.TABLE_NAME)
public class SuperMarketCompositeId
{
  public static final String TABLE_NAME = "EEDEMOS_SUPERMARKET";

  @Id
  private int                companyId;

  @Id
  private int                branchNo;

  private String             name;

  protected SuperMarketCompositeId()
  {
  }

  public SuperMarketCompositeId(int companyId, int branchNo, String name)
  {
    this.companyId = companyId;
    this.branchNo = branchNo;
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

  public int getCompanyId()
  {
    return this.companyId;
  }

  public int getBranchNo()
  {
    return this.branchNo;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.branchNo;
    result = prime * result + this.companyId;
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
    final SuperMarketCompositeId other = (SuperMarketCompositeId) obj;
    if (this.branchNo != other.branchNo)
    {
      return false;
    }
    if (this.companyId != other.companyId)
    {
      return false;
    }
    return true;
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{companyId=" + this.companyId + ",branchNo=" + this.branchNo + ",name=" + this.name + "}";
  }

}
