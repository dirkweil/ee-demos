package de.gedoplan.buch.eedemos.provs.projekt.entity;

import de.gedoplan.buch.eedemos.provs.firma.entity.Firma;
import de.gedoplan.buch.eedemos.provs.firma.entity.Mitarbeiter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity-Klasse für Projekte.
 * 
 * Projekte haben über die Eigenschaften der Basisklasse {@link Aufgabe} hinaus Informationen über Typ, Status, Auftraggeber und
 * Auftragnehmer.
 * 
 * @author dw
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = Projekt.TABLE_NAME)
public class Projekt extends Aufgabe
{
  /**
   * Tabellenname.
   */
  public static final String TABLE_NAME = "PROVS_PROJEKT";

  /**
   * Typ.
   */
  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "TYP")
  private ProjektTyp         typ        = ProjektTyp.AW;

  /**
   * Status.
   */
  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS")
  private ProjektStatus      status     = ProjektStatus.ANFRAGE;

  /**
   * Auftragsnummer
   */
  @Size(min = 1)
  @Column(name = "AUFTRAGSNUMMER")
  private String             auftragsnummer;

  /**
   * Wert liefern: {@link #auftragsnummer}.
   * 
   * @return Wert
   */
  public String getAuftragsnummer()
  {
    return this.auftragsnummer;
  }

  /**
   * Wert setzen: {@link #auftragsnummer}.
   * 
   * @param auftragsnummer Wert
   */
  public void setAuftragsnummer(String auftragsnummer)
  {
    this.auftragsnummer = auftragsnummer;
  }

  /**
   * Auftraggeber.
   */
  @ManyToOne
  @JoinColumn(name = "AG_ID")
  private Firma       auftraggeber;

  /**
   * Ansprechpartner beim Auftraggeber.
   */
  @ManyToOne
  @JoinColumn(name = "AG_AP_ID")
  private Mitarbeiter auftraggeberAnsprechpartner;

  /**
   * Auftraggeber.
   */
  @ManyToOne
  @JoinColumn(name = "AN_ID")
  private Firma       auftragnehmer;

  /**
   * Ansprechpartner beim Auftragnehmer.
   */
  @ManyToOne
  @JoinColumn(name = "AN_AP_ID")
  private Mitarbeiter auftragnehmerAnsprechpartner;

  /**
   * Konstruktor.
   * 
   * Die Konstruktorparameter dürfen zunächst <code>null</code> sein, müssen aber vor dem Speichern gefüllt werden.
   * 
   * @param projektnummer Projektnummer
   * @param titel Titel
   */
  public Projekt(String projektnummer, String titel)
  {
    super(projektnummer, titel);
  }

  /**
   * Wert liefern: {@link #typ}.
   * 
   * @return Wert
   */
  public ProjektTyp getTyp()
  {
    return this.typ;
  }

  /**
   * Wert setzen: {@link #typ}.
   * 
   * @param typ Wert
   */
  public void setTyp(ProjektTyp typ)
  {
    this.typ = typ;
  }

  /**
   * Wert liefern: {@link #status}.
   * 
   * @return Wert
   */
  public ProjektStatus getStatus()
  {
    return this.status;
  }

  /**
   * Wert setzen: {@link #status}.
   * 
   * @param status Wert
   */
  public void setStatus(ProjektStatus status)
  {
    this.status = status;
  }

  /**
   * Wert liefern: {@link #auftraggeber}.
   * 
   * @return Wert
   */
  public Firma getAuftraggeber()
  {
    return this.auftraggeber;
  }

  /**
   * Wert setzen: {@link #auftraggeber}.
   * 
   * @param auftraggeber Wert
   */
  public void setAuftraggeber(Firma auftraggeber)
  {
    this.auftraggeber = auftraggeber;
  }

  /**
   * Wert liefern: {@link #auftraggeberAnsprechpartner}.
   * 
   * @return Wert
   */
  public Mitarbeiter getAuftraggeberAnsprechpartner()
  {
    return this.auftraggeberAnsprechpartner;
  }

  /**
   * Wert setzen: {@link #auftraggeberAnsprechpartner}.
   * 
   * @param auftraggeberAnsprechpartner Wert
   */
  public void setAuftraggeberAnsprechpartner(Mitarbeiter auftraggeberAnsprechpartner)
  {
    this.auftraggeberAnsprechpartner = auftraggeberAnsprechpartner;
  }

  /**
   * Wert liefern: {@link #auftragnehmer}.
   * 
   * @return Wert
   */
  public Firma getAuftragnehmer()
  {
    return this.auftragnehmer;
  }

  /**
   * Wert setzen: {@link #auftragnehmer}.
   * 
   * @param auftragnehmer Wert
   */
  public void setAuftragnehmer(Firma auftragnehmer)
  {
    this.auftragnehmer = auftragnehmer;
  }

  /**
   * Wert liefern: {@link #auftragnehmerAnsprechpartner}.
   * 
   * @return Wert
   */
  public Mitarbeiter getAuftragnehmerAnsprechpartner()
  {
    return this.auftragnehmerAnsprechpartner;
  }

  /**
   * Wert setzen: {@link #auftragnehmerAnsprechpartner}.
   * 
   * @param auftragnehmerAnsprechpartner Wert
   */
  public void setAuftragnehmerAnsprechpartner(Mitarbeiter auftragnehmerAnsprechpartner)
  {
    this.auftragnehmerAnsprechpartner = auftragnehmerAnsprechpartner;
  }

  /**
   * Konstruktor für JPA-interne Zwecke.
   */
  protected Projekt()
  {
  }

  /**
   * Neues Projekt mit gültigem Inhalt liefern.
   * 
   * @return PerProjektson
   */
  public static Projekt createValidInstance()
  {
    Projekt projekt = new Projekt("Neue Nummer", "Neuer Titel");
    return projekt;
  }

  /**
   * Daten löschen.
   */
  public void clear()
  {
    setNummer(null);
    setTitel(null);

    this.typ = ProjektTyp.AW;
    this.status = ProjektStatus.ANFRAGE;
    this.auftraggeber = null;
    this.auftraggeberAnsprechpartner = null;
    this.auftragnehmer = null;
    this.auftragnehmerAnsprechpartner = null;
  }
}
