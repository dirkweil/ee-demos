package de.gedoplan.buch.eedemos.jpa.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Access(AccessType.FIELD)
public class Employee
{
  @Id
  private int                 id;
  private String              name;

  private Address             address;

  // @AttributeOverrides({ @AttributeOverride(name = "zipCode", column = @Column(name = "homeZipCode")), @AttributeOverride(name =
  // "town", column = @Column(name = "homeTown")),
  // @AttributeOverride(name = "street", column = @Column(name = "homeStreet")) })
  // private Address homeAddress;

  @ElementCollection
  // @CollectionTable(name = "EMPLOYEE_SKILLS", joinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
  // @Column(name = "SKILLS")
  private List<String>        skills = new ArrayList<String>();

  @ElementCollection
  // @CollectionTable(name = "EMPLOYEE_PHONES", joinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
  // @MapKeyColumn(name = "PHONES_KEY")
  // @Column(name = "PHONES")
  private Map<String, String> phones = new HashMap<String, String>();

  protected Employee()
  {
  }

  public Employee(int id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public int getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Address getAddress()
  {
    return this.address;
  }

  public void setAddress(Address address)
  {
    this.address = address;
  }

  public void addSkill(String skill)
  {
    this.skills.add(skill);
  }

  public void setPhone(String type, String number)
  {
    this.phones.put(type, number);
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.id;
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
    Employee other = (Employee) obj;
    if (this.id != other.id)
    {
      return false;
    }
    return true;
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName() + "{id=" + this.id + ",name=" + this.name + ",address=" + this.address + ",skills=" + this.skills + ",phones=" + this.phones + "}";
  }

}
