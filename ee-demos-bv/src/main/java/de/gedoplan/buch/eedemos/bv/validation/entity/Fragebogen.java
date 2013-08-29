package de.gedoplan.buch.eedemos.bv.validation.entity;

import de.gedoplan.baselibs.utils.constraint.NotEmpty;
import de.gedoplan.buch.eedemos.bv.validation.group.InitialInput;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

@Entity
@Access(AccessType.FIELD)
public class Fragebogen implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Integer           id;

  @NotNull(groups = { InitialInput.class, Default.class })
  @Past(groups = { InitialInput.class, Default.class })
  @Temporal(TemporalType.DATE)
  private Date              umfrageDatum;

  // @NotNull(groups={InitialInput.class, Default.class})
  // @Size(min = 1, groups={InitialInput.class, Default.class})
  @NotEmpty(groups = { InitialInput.class, Default.class })
  private String            name;

  @Valid
  private Adresse           adresse          = new Adresse();

  @Min(value = 18, groups = { InitialInput.class, Default.class })
  @Max(value = 120, groups = { InitialInput.class, Default.class })
  private int               lebensalter;

  @Pattern(regexp = "[^@]+@[^@]+\\.[^@]+", groups = { InitialInput.class, Default.class })
  private String            email;

  // implizit: groups = Default.class
  @NotNull
  @Size(min = 10, max = 140, groups = { InitialInput.class, Default.class })
  private String            bemerkungen;

  public Date getUmfrageDatum()
  {
    return this.umfrageDatum;
  }

  public void setUmfrageDatum(Date pollDate)
  {
    this.umfrageDatum = pollDate;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Adresse getAdresse()
  {
    return this.adresse;
  }

  public int getLebensalter()
  {
    return this.lebensalter;
  }

  public void setLebensalter(int age)
  {
    this.lebensalter = age;
  }

  public String getEmail()
  {
    return this.email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getBemerkungen()
  {
    return this.bemerkungen;
  }

  public void setBemerkungen(String comment)
  {
    this.bemerkungen = comment;
  }

  public Integer getId()
  {
    return this.id;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
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
    Fragebogen other = (Fragebogen) obj;
    if (this.id == null)
    {
      return false;
    }
    else if (!this.id.equals(other.id))
    {
      return false;
    }
    return true;
  }

  @Override
  public String toString()
  {
    return "Fragebogen [id=" + this.id + ", pollDate=" + this.umfrageDatum + ", name=" + this.name + ", adresse=" + this.adresse + ", age=" + this.lebensalter + ", email=" + this.email + ", comment="
        + this.bemerkungen + "]";
  }

}
