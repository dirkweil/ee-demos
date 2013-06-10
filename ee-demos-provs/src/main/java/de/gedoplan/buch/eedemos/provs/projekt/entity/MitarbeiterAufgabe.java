package de.gedoplan.buch.eedemos.provs.projekt.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;
import de.gedoplan.baselibs.utils.util.ApplicationTime;
import de.gedoplan.buch.eedemos.provs.firma.entity.Mitarbeiter;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Entity-Klasse für die Zuordnung von Mitarbeitern zu Aufgaben (Projekten und Teilprojekten).
 * 
 * Objekte dieser Klasse werden als abhängige Objekte von {@link Aufgabe} geführt. Sie verknüpfen die Aufgabe mit einem
 * Mitarbeiter und führen Abrechnungsparameter für die Zuordnung mit.
 * 
 * @author dw
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = MitarbeiterAufgabe.TABLE_NAME, uniqueConstraints = { @UniqueConstraint(columnNames = { "AUFGABE_ID", "MITARBEITER_ID" }) })
public class MitarbeiterAufgabe extends GeneratedIntegerIdEntity
{
  /**
   * Tabellenname.
   */
  public static final String TABLE_NAME  = "PROVS_MITARBEITER_AUFGABE";

  @NotNull
  @ManyToOne
  private Aufgabe            aufgabe;

  @NotNull
  @ManyToOne
  private Mitarbeiter        mitarbeiter;

  /**
   * Verkaufs-Stundensatz in EUR/h.
   */
  @Column(name = "VK")
  private double             vkSatz;

  /**
   * Einkaufs-Stundensatz in EUR/h. Ist nur bei Einsatz externer Mitarbeiter gefüllt.
   */
  @Column(name = "EK")
  private double             ekSatz;

  /**
   * Anteil für die Aufgabe geplante Zeit in Prozent der verfügbaren Zeit (bezogen auf eine Vollzeitstelle).
   */
  @Min(0)
  @Max(100)
  @Column(name = "PLANPROZ")
  private int                planProzent = 100;

  /**
   * Limits.
   */
  @Valid
  @Embedded
  private Limit              limit;

  /**
   * Buchung von Zeiten und Kosten ist erst ab diesem Datum erlaubt. Wird nach jeder Abrechnung entsprechend hochgesetzt.
   */
  @NotNull
  @Temporal(TemporalType.DATE)
  @Column(name = "BUCHUNG_AB")
  private Date               buchungErlaubtAb;

  /**
   * Inaktiv-Flag. wenn dieser Wert <code>true</code> ist, dürfen keine Buchungen auf diese Mitarbeiteraufgabe mehr erfolgen.
   */
  @Column(name = "INAKTIV")
  private boolean            inaktiv;

  /**
   * Konstruktor.
   * 
   * @param mitarbeiter Mitarbeiter
   * @param aufgabe Aufgabe
   */
  /* package_private */MitarbeiterAufgabe(Mitarbeiter mitarbeiter, Aufgabe aufgabe)
  {
    this.aufgabe = aufgabe;
    this.mitarbeiter = mitarbeiter;
    this.buchungErlaubtAb = ApplicationTime.getCurrentDate();
    this.limit = new Limit();
  }

  /**
   * Wert setzen: {@link #aufgabe} .
   * 
   * @param aufgabe Wert.
   */
  public void setAufgabe(Aufgabe aufgabe)
  {
    this.aufgabe = aufgabe;
  }

  /**
   * Wert setzen: {@link #mitarbeiter} .
   * 
   * @param mitarbeiter Wert
   */
  public void setMitarbeiter(Mitarbeiter mitarbeiter)
  {
    this.mitarbeiter = mitarbeiter;
  }

  /**
   * Wert liefern: {@link #vkSatz}.
   * 
   * @return Wert
   */
  public double getVkSatz()
  {
    return this.vkSatz;
  }

  /**
   * Wert setzen: {@link #vkSatz}.
   * 
   * @param vkSatz Wert
   */
  public void setVkSatz(double vkSatz)
  {
    this.vkSatz = vkSatz;
  }

  /**
   * Wert liefern: {@link #ekSatz}.
   * 
   * @return Wert
   */
  public double getEkSatz()
  {
    return this.ekSatz;
  }

  /**
   * Wert setzen: {@link #ekSatz}.
   * 
   * @param ekSatz Wert
   */
  public void setEkSatz(double ekSatz)
  {
    this.ekSatz = ekSatz;
  }

  /**
   * Wert liefern: {@link #planProzent}.
   * 
   * @return Wert
   */
  public int getPlanProzent()
  {
    return this.planProzent;
  }

  /**
   * Wert setzen: {@link #planProzent}.
   * 
   * @param planProzent Wert
   */
  public void setPlanProzent(int planProzent)
  {
    this.planProzent = planProzent;
  }

  /**
   * Wert liefern: {@link #limit}.
   * 
   * @return Wert
   */
  public Limit getLimit()
  {
    if (this.limit == null)
    {
      this.limit = new Limit();
    }
    return this.limit;
  }

  /**
   * Wert liefern: {@link #buchungErlaubtAb}.
   * 
   * @return Wert
   */
  public Date getBuchungErlaubtAb()
  {
    return this.buchungErlaubtAb;
  }

  /**
   * Wert setzen: {@link #buchungErlaubtAb}.
   * 
   * @param buchungErlaubtAb Wert
   */
  public void setBuchungErlaubtAb(Date buchungErlaubtAb)
  {
    this.buchungErlaubtAb = buchungErlaubtAb;
  }

  /**
   * Wert liefern: {@link #inaktiv}.
   * 
   * @return Wert
   */
  public boolean isInaktiv()
  {
    return this.inaktiv;
  }

  /**
   * Wert setzen: {@link #inaktiv}.
   * 
   * @param inaktiv Wert
   */
  public void setInaktiv(boolean inaktiv)
  {
    this.inaktiv = inaktiv;
  }

  /**
   * Aufgabe liefern.
   * 
   * @return Aufgabe
   * @see {@link MitarbeiterAufgabeId#getAufgabe()}
   */
  public Aufgabe getAufgabe()
  {
    return this.aufgabe;
  }

  /**
   * Mitarbeiter liefern.
   * 
   * @return Mitarbeiter
   * @see {@link MitarbeiterAufgabeId#getMitarbeiter()}
   */
  public Mitarbeiter getMitarbeiter()
  {
    return this.mitarbeiter;
  }

  /**
   * Konstruktor für JPA-interne Zwecke.
   */
  protected MitarbeiterAufgabe()
  {
  }

  // /**
  // * Ermittelt die Summe der gebuchten Zeiten des Mitarbeiters.
  // *
  // * @return Summe der gebuchten Minuten (long)
  // */
  // public long getGebuchteProjektzeiten()
  // {
  // long zeitenSumme = 0L;
  // Set<ProjektZeit> zeiten = this.getProjektZeiten();
  // for (ProjektZeit projektZeit : zeiten)
  // {
  // zeitenSumme += projektZeit.getMinuten();
  // }
  //
  // return zeitenSumme;
  // }

  // /**
  // * Liefert bereits gespeicherten wert zu gegebenem Zeitpunkt.
  // *
  // * Bei keiner Übereinstimmung wird ein neues Objekt zurückgeliefert
  // *
  // * @param date zu selektierendes Datum
  // * @return Übereinstimmendes ProjektZeit-Objekt oder Null (keine Übereinstimmung)
  // */
  // public ProjektZeit getGebuchteProjektzeiten(Date date)
  // {
  // ProjektZeit selectedProjektZeit = null;
  //
  // for (ProjektZeit projektZeit : this.getProjektZeiten())
  // {
  // if (projektZeit.getBuchungstag().compareTo(date) == 0)
  // {
  // selectedProjektZeit = projektZeit;
  // break;
  // }
  // }
  //
  // return selectedProjektZeit;
  // }

  // /**
  // * Liefert für die Summe alle ProjektZeiten für angegebenes Datum.
  // *
  // * @param datumVon Datumseingrenzung (von)
  // * @param datumBis Datumseingrenzung (bis)
  // * @param nurOffeneZeiten boolean ob ausschließlich nicht gebuchte Zeiten (true) berücksichtigt werden sollen
  // * @return Summe der bereits gebuchten Projektzeit
  // */
  // public double getSummeGebuchteZeiten(Date datumVon, Date datumBis, boolean nurOffeneZeiten)
  // {
  // int summe = 0;
  // for (ProjektZeit projektZeit : this.getProjektZeitenFuerZeitSpanne(datumVon, datumBis))
  // {
  // if (nurOffeneZeiten && !projektZeit.isAbgerechnet())
  // {
  // summe += projektZeit.getMinuten();
  // }
  // }
  //
  // double result = summe / 60.;
  // result = Math.round(result * 100.) / 100.;
  // return result;
  // }
  //
  // /**
  // * Liefert für eine Liste alle ProjektZeiten für angegebenes Datum.
  // *
  // * @param datumVon Datumseingrenzung (von)
  // * @param datumBis Datumseingrenzung (bis)
  // * @return Liste der uebereinstimmenden Projektzeiten
  // */
  // public List<ProjektZeit> getProjektZeitenFuerZeitSpanne(Date datumVon, Date datumBis)
  // {
  // List<ProjektZeit> selektierteProjektZeiten = new ArrayList<ProjektZeit>();
  //
  // for (ProjektZeit projektZeit : this.getProjektZeiten())
  // {
  // if (projektZeit.getBuchungstag().after(datumVon) && projektZeit.getBuchungstag().before(datumBis) &&
  // !projektZeit.isAbgerechnet())
  // {
  // selektierteProjektZeiten.add(projektZeit);
  // }
  // }
  //
  // return selektierteProjektZeiten;
  // }

}
