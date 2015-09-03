package de.gedoplan.buch.eedemos.rs.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Access(AccessType.FIELD)
@Table(name = Talk.TABLE_NAME)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Talk extends GeneratedIntegerIdEntity
{
  public static final String TABLE_NAME          = "RS_TALK";
  public static final String TABLE_NAME_SPEAKERS = "RS_TALK_SPEAKER";

  /**
   * Title.
   */
  @NotNull
  @Size(min = 5)
  private String             title;

  /**
   * Speakers.
   */
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = TABLE_NAME_SPEAKERS)
  private List<String>       speakers;

  /**
   * Type of talk.
   */
  @NotNull
  private TalkType           talkType;

  /**
   * Start date and time.
   */
  @Temporal(TemporalType.TIMESTAMP)
  private Date               start;

  /**
   * Duration in minutes.
   */
  @Min(15)
  private Integer            duration;

  public Talk(String title, TalkType talkType, Date start, int duration, String... speakers)
  {
    this.title = title;
    this.talkType = talkType;
    this.start = start;
    this.duration = duration;
    this.speakers = new ArrayList<>();
    for (String speaker : speakers)
    {
      this.speakers.add(speaker);
    }
  }

  public Talk()
  {
  }

  public String getTitle()
  {
    return this.title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public List<String> getSpeakers()
  {
    return this.speakers;
  }

  public TalkType getTalkType()
  {
    return this.talkType;
  }

  public void setTalkType(TalkType talkType)
  {
    this.talkType = talkType;
  }

  public Date getStart()
  {
    return this.start;
  }

  public void setStart(Date start)
  {
    this.start = start;
  }

  public Integer getDuration()
  {
    return this.duration;
  }

  public void setDuration(Integer duration)
  {
    this.duration = duration;
  }

}
