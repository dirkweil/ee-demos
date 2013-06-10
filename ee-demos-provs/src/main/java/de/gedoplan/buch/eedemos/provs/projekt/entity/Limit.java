package de.gedoplan.buch.eedemos.provs.projekt.entity;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

/**
 * Limitierung von Zeit und/oder Budget für Projekte, Teilprojekte und Mitarbeiterzuordnungen.
 * 
 * @author dw
 */
@Embeddable
@Access(AccessType.FIELD)
public class Limit
{
  /**
   * Start.
   */
  @Temporal(TemporalType.DATE)
  @Column(name = "START")
  private Date    start;

  /**
   * Ende.
   */
  @Temporal(TemporalType.DATE)
  @Column(name = "ENDE")
  private Date    ende;

  /**
   * Budget in EUR. <code>null</code> bedeutet 'unlimitiert'.
   */
  @Column(name = "MAX_EUR")
  @Min(0)
  private Double  budget;

  /**
   * Zeitlimit in Minuten. <code>null</code> bedeutet 'unlimitiert'.
   */
  @Column(name = "MAX_MIN")
  @Min(0)
  private Integer maxMinuten;

  /**
   * Konstruktor.
   */
  public Limit()
  {
  }

  /**
   * Wert liefern: {@link #start}.
   * 
   * @return Wert
   */
  public Date getStart()
  {
    return this.start;
  }

  /**
   * Wert setzen: {@link #start}.
   * 
   * @param start Wert
   */
  public void setStart(Date start)
  {
    this.start = start;
  }

  /**
   * Wert liefern: {@link #ende}.
   * 
   * @return Wert
   */
  public Date getEnde()
  {
    return this.ende;
  }

  /**
   * Wert setzen: {@link #ende}.
   * 
   * @param ende Wert
   */
  public void setEnde(Date ende)
  {
    this.ende = ende;
  }

  /**
   * Wert liefern: {@link #budget}.
   * 
   * @return Wert
   */
  public Double getBudget()
  {
    return this.budget;
  }

  /**
   * Wert setzen: {@link #budget}.
   * 
   * @param budget Wert
   */
  public void setBudget(Double budget)
  {
    this.budget = budget;
  }

  /**
   * Wert liefern: {@link #maxMinuten}.
   * 
   * @return Wert
   */
  public Integer getMaxMinuten()
  {
    return this.maxMinuten;
  }

  /**
   * Wert setzen: {@link #maxMinuten}.
   * 
   * @param zeitLimit Wert
   */
  public void setMaxMinuten(Integer zeitLimit)
  {
    this.maxMinuten = zeitLimit;
  }
}
