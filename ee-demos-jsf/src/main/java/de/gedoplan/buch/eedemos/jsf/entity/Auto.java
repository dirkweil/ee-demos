package de.gedoplan.buch.eedemos.jsf.entity;

import de.gedoplan.buch.eedemos.jsf.validation.constraint.ValidAuto;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Access(AccessType.FIELD)
@ValidAuto
public class Auto
{
  @Id
  @NotNull
  @Size(min = 1)
  private String  name;

  private boolean kombi;

  @Min(2)
  @Max(5)
  private int     anzahlTueren;

  public Auto(String name, boolean kombi, int anzahlTueren)
  {
    this.name = name;
    this.kombi = kombi;
    this.anzahlTueren = anzahlTueren;
  }

  public Auto()
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

  public boolean isKombi()
  {
    return this.kombi;
  }

  public void setKombi(boolean kombi)
  {
    this.kombi = kombi;
  }

  public int getAnzahlTueren()
  {
    return this.anzahlTueren;
  }

  public void setAnzahlTueren(int anzahlTueren)
  {
    this.anzahlTueren = anzahlTueren;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
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
    Auto other = (Auto) obj;
    if (this.name == null)
    {
      if (other.name != null)
      {
        return false;
      }
    }
    else
      if (!this.name.equals(other.name))
      {
        return false;
      }
    return true;
  }

  @Override
  public String toString()
  {
    return "Auto [name=" + this.name + ", kombi=" + this.kombi + ", anzahlTueren=" + this.anzahlTueren + "]";
  }

}
