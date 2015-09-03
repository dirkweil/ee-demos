package de.gedoplan.buch.eedemos.rs.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Access(AccessType.FIELD)
@Table(name = Track.TABLE_NAME)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Track extends GeneratedIntegerIdEntity
{
  public static final String TABLE_NAME       = "RS_TRACK";
  public static final String TABLE_NAME_TALKS = "RS_TRACK_TALK";

  private String             name;

  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(name = TABLE_NAME_TALKS, joinColumns = @JoinColumn(name = "TALK_ID"), inverseJoinColumns = @JoinColumn(name = "TRACK_ID"))
  private Set<Talk>          talks;

  public Track(String name)
  {
    this.name = name;
    this.talks = new HashSet<>();
  }

  public Track()
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

  public Set<Talk> getTalks()
  {
    return this.talks;
  }
}
