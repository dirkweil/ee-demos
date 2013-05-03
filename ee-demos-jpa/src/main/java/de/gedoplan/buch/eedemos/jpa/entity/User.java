package de.gedoplan.buch.eedemos.jpa.entity;

import de.gedoplan.baselibs.persistence.entity.StringIdEntity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = User.TABLE_NAME)
public class User extends StringIdEntity
{
  public static final String TABLE_NAME                    = "EEDEMOS_USER";
  public static final String USABLEAPPLICATIONS_TABLE_NAME = "EEDEMOS_USER_APPLICATION";

  private String             name;

  @ManyToMany
  @JoinTable(name = USABLEAPPLICATIONS_TABLE_NAME)
  private List<Application>  usableApplications;

  public User(String id, String name)
  {
    super(id);
    this.name = name;
    this.usableApplications = new ArrayList<>();
  }

  @Override
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

  protected User()
  {
  }
}
