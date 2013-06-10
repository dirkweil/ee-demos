package de.gedoplan.buch.eedemos.jpa.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class BranchId implements Serializable
{
  private int companyId;
  private int branchNo;

  public BranchId()
  {
  }

  public BranchId(int companyId, int branchNo)
  {
    this.companyId = companyId;
    this.branchNo = branchNo;
  }

  public int getCompanyId()
  {
    return this.companyId;
  }

  public void setCompanyId(int companyId)
  {
    this.companyId = companyId;
  }

  public int getBranchNo()
  {
    return this.branchNo;
  }

  public void setBranchNo(int branchNo)
  {
    this.branchNo = branchNo;
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
    final BranchId other = (BranchId) obj;
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
    return this.getClass().getSimpleName() + "{companyId=" + this.companyId + ",branchNo=" + this.branchNo + "}";
  }

}
