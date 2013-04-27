package de.gedoplan.buch.eedemos.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
@Access(AccessType.FIELD)
public class Application
{
  @Id
  @GeneratedValue
  private Integer    id;
  private String     name;

  @ManyToMany(mappedBy = "usableApplications")
  private List<User> authorizedUsers;

  public Application(String name)
  {
    this.name = name;
    this.authorizedUsers = new ArrayList<>();
  }

  public Integer getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public List<User> getAuthorizedUsers()
  {
    return this.authorizedUsers;
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
    Application other = (Application) obj;
    return this.id != null && this.id.equals(other.id);
  }

  @Override
  public String toString()
  {
    return "Application [id=" + this.id + ", name=" + this.name + "]";
  }

  protected Application()
  {
  }
}
