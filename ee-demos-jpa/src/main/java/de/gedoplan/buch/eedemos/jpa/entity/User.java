package de.gedoplan.buch.eedemos.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
// USER ist für viele DB ein reserviertes Wort
@Table(name = "USERS")
public class User
{
  @Id
  private String            id;
  private String            name;

  @ManyToMany
  private List<Application> usableApplications;

  public User(String id, String name)
  {
    this.id = id;
    this.name = name;
    this.usableApplications = new ArrayList<>();
  }

  public String getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public List<Application> getUsableApplications()
  {
    return this.usableApplications;
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
    User other = (User) obj;
    if (this.id == null)
    {
      if (other.id != null)
      {
        return false;
      }
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
    return "User [id=" + this.id + ", name=" + this.name + "]";
  }

  protected User()
  {
  }
}
