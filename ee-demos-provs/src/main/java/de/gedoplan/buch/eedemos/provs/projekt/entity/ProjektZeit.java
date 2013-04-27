package de.gedoplan.buch.eedemos.provs.projekt.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Entity-Klasse für ProjektZeiten.
 * 
 * Objekte dieser Klasse einer Mitarbeiteraufgabe (und damit einem Projekt bzw. einer Aufgabe darin) Abrechnungszeiten hinzu.
 * 
 * @author dw
 */

@Entity
@Access(AccessType.FIELD)
@Table(name = ProjektZeit.TABLE_NAME, uniqueConstraints = { @UniqueConstraint(columnNames = { "MITARBEITERAUFGABE_ID", "TAG" }) })
public class ProjektZeit extends GeneratedIntegerIdEntity
{
  /**
   * Tabellenname.
   */
  public static final String TABLE_NAME  = "PROVS_PROJEKTZEIT";

  /**
   * Buchungstag.
   */
  @NotNull
  @Temporal(TemporalType.DATE)
  @Column(name = "TAG")
  private Date               buchungstag;

  /**
   * Mitarbeiteraufgabe.
   */
  @NotNull
  @ManyToOne
  MitarbeiterAufgabe         mitarbeiterAufgabe;

  /**
   * Verbrauchte Zeit in Minuten.
   */
  @Min(0)
  @Column(name = "MINUTEN")
  private int                minuten;

  /**
   * Flag: Zeit wurde abgerechnet.
   */
  @Column(name = "ABGERECHNET")
  private boolean            abgerechnet = false;

  /**
   * Konstruktor.
   * 
   * @param buchungstag Buchungstag
   * @param mitarbeiterAufgabe Mitarbeiteraufgabe
   * @param minuten Minuten
   */
  public ProjektZeit(Date buchungstag, MitarbeiterAufgabe mitarbeiterAufgabe, int minuten)
  {
    this.buchungstag = buchungstag;
    this.mitarbeiterAufgabe = mitarbeiterAufgabe;
    this.minuten = minuten;
  }

  /**
   * Wert liefern: {@link #minuten}.
   * 
   * @return Wert
   */
  public int getMinuten()
  {
    return this.minuten;
  }

  /**
   * Wert setzen: {@link #minuten}.
   * 
   * @param minuten Wert
   */
  public void setMinuten(int minuten)
  {
    this.minuten = minuten;
  }

  /**
   * Wert liefern: {@link #abgerechnet}.
   * 
   * @return Wert
   */
  public boolean isAbgerechnet()
  {
    return this.abgerechnet;
  }

  /**
   * Wert setzen: {@link #abgerechnet}.
   * 
   * @param abgerechnet Wert
   */
  public void setAbgerechnet(boolean abgerechnet)
  {
    this.abgerechnet = abgerechnet;
  }

  /**
   * Buchungstag liefern.
   * 
   * @return Buchungstag
   * @see {@link ProjektZeitId#getBuchungstag()}
   */
  public Date getBuchungstag()
  {
    return this.buchungstag;
  }

  /**
   * Mitarbeiteraufgabe liefern.
   * 
   * @return Mitarbeiteraufgabe
   * @see {@link ProjektZeitId#getMitarbeiterAufgabe()}
   */
  public MitarbeiterAufgabe getMitarbeiterAufgabe()
  {
    return this.mitarbeiterAufgabe;
  }

  /**
   * Mitarbeiteraufagbe setzen.
   * 
   * 
   * @param mitarbeiterAufgabe Mitarbeiteraufgabe
   */
  public void setMitarbeiterAufgabe(MitarbeiterAufgabe mitarbeiterAufgabe)
  {
    this.mitarbeiterAufgabe = mitarbeiterAufgabe;
  }

  /**
   * Konstruktor für JPA-interne Zwecke.
   */
  protected ProjektZeit()
  {
  }
}
