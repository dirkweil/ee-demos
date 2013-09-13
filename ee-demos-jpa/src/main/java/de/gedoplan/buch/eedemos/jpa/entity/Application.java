package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = Application.TABLE_NAME)
public class Application extends GeneratedIntegerIdEntity
{
  private static final long  serialVersionUID = 1L;

  public static final String TABLE_NAME       = "JPA_APPLICATION";

  private String             name;

  @ManyToMany(mappedBy = "usableApplications")
  private List<User>         authorizedUsers;

  public Application(String name)
  {
    this.name = name;
    this.authorizedUsers = new ArrayList<>();
  }

  public String getName()
  {
    return this.name;
  }

  public List<User> getAuthorizedUsers()
  {
    return this.authorizedUsers;
  }

  protected Application()
  {
  }
}
