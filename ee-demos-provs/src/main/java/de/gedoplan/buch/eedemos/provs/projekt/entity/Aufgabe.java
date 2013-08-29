package de.gedoplan.buch.eedemos.provs.projekt.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;
import de.gedoplan.baselibs.utils.constraint.NotEmpty;
import de.gedoplan.buch.eedemos.provs.firma.entity.Mitarbeiter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;

/**
 * Basis-Klasse für Projekte und Teilprojekte.
 * 
 * @author dw
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Access(AccessType.FIELD)
@Table(name = Aufgabe.TABLE_NAME)
public class Aufgabe extends GeneratedIntegerIdEntity
{
  /**
   * Tabellenname.
   */
  public static final String       TABLE_NAME = "PROVS_AUFGABE";

  /**
   * (Teil-)Projektnummer.
   */
  @Column(name = "NR")
  @NotEmpty
  private String                   nummer;

  /**
   * Titel.
   */
  @Column(name = "TITEL")
  @NotEmpty
  private String                   titel;

  /**
   * Limits.
   */
  @Embedded
  private Limit                    limit;

  /**
   * Anteil fakturierbarer Zeit in Prozent der Projektzeit. Falls <code>null</code>, gilt der entsprechende Wert der
   * Vater-Aufgabe, wenn vorhanden, sonst 100%.
   */
  @Column(name = "FAKTURAPROZ")
  private Integer                  fakturaProzent;

  /**
   * Anteil Arbeitszeit in Prozent der Projektzeit. Falls <code>null</code>, gilt der entsprechende Wert der Vater-Aufgabe, wenn
   * vorhanden, sonst 100%.
   */
  @Column(name = "ARBEITPROZ")
  private Integer                  arbeitProzent;

  /**
   * Vater-Aufgabe. Ist <code>null</code>, wenn diese Aufgabe ein Projekt ist.
   */
  @ManyToOne
  private Aufgabe                  vaterAufgabe;

  /**
   * Teilaufgaben.
   */
  @OneToMany(mappedBy = "vaterAufgabe", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Aufgabe>            teilAufgaben;

  /**
   * Zugeordnete Mitarbeiter (-aufgaben).
   */
  @OneToMany(mappedBy = "aufgabe", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<MitarbeiterAufgabe> mitarbeiterAufgaben;

  /**
   * Konstruktor.
   * 
   * @param nummer Projekt- bzw. Aufgabennummer; darf zunächst <code>null</code> sein, muss aber vor dem Speichern gefüllt werden
   * @param titel Titel; darf zunächst <code>null</code> sein, muss aber vor dem Speichern gefüllt werden
   */
  protected Aufgabe(String nummer, String titel)
  {
    this.nummer = nummer;
    this.titel = titel;

    this.limit = new Limit();
    this.teilAufgaben = new ArrayList<>();
    this.mitarbeiterAufgaben = new ArrayList<>();
  }

  /**
   * Wert liefern: {@link #nummer}.
   * 
   * @return Wert
   */
  public String getNummer()
  {
    return this.nummer;
  }

  /**
   * Wert setzen: {@link #nummer}.
   * 
   * @param nummer Wert
   */
  public void setNummer(String nummer)
  {
    this.nummer = nummer;
  }

  /**
   * Wert liefern: {@link #titel}.
   * 
   * @return Wert
   */
  public String getTitel()
  {
    return this.titel;
  }

  /**
   * Wert setzen: {@link #titel}.
   * 
   * @param titel Wert
   */
  public void setTitel(String titel)
  {
    this.titel = titel;
  }

  /**
   * Wert liefern: {@link #limit}.
   * 
   * @return Wert
   */
  public Limit getLimit()
  {
    return this.limit;
  }

  /**
   * Wert liefern: {@link #fakturaProzent}.
   * 
   * @return Wert
   */
  public Integer getFakturaProzent()
  {
    return this.fakturaProzent;
  }

  /**
   * Wert setzen: {@link #fakturaProzent}.
   * 
   * @param fakturaProzent Wert
   */
  public void setFakturaProzent(Integer fakturaProzent)
  {
    this.fakturaProzent = fakturaProzent;
  }

  /**
   * Wert liefern: {@link #arbeitProzent}.
   * 
   * @return Wert
   */
  public Integer getArbeitProzent()
  {
    return this.arbeitProzent;
  }

  /**
   * Wert setzen: {@link #arbeitProzent}.
   * 
   * @param arbeitProzent Wert
   */
  public void setArbeitProzent(Integer arbeitProzent)
  {
    this.arbeitProzent = arbeitProzent;
  }

  /**
   * Wert liefern: {@link #vaterAufgabe}.
   * 
   * @return Wert
   */
  public Aufgabe getVaterAufgabe()
  {
    return this.vaterAufgabe;
  }

  /**
   * Wert liefern: {@link #teilAufgaben}.
   * 
   * @return Wert (unveränderlich)
   */
  public List<Aufgabe> getTeilAufgaben()
  {
    return Collections.unmodifiableList(this.teilAufgaben);
  }

  /**
   * Teilaufgabe hinzufügen.
   * 
   * @param nummer Aufgabennummer; darf zunächst <code>null</code> sein, muss aber vor dem Speichern gefüllt werden
   * @param titel Titel; darf zunächst <code>null</code> sein, muss aber vor dem Speichern gefüllt werden
   * @return neue Teilaufgabe
   */
  public Aufgabe addTeilAufgabe(String nummer, String titel)
  {
    Aufgabe teilAufgabe = new Aufgabe(nummer, titel);
    teilAufgabe.vaterAufgabe = this;
    this.teilAufgaben.add(teilAufgabe);
    return teilAufgabe;
  }

  /**
   * Teilaufgabe entfernen.
   * 
   * @param teilAufgabe Teilaufgabe
   */
  public void removeTeilAufgabe(Aufgabe teilAufgabe)
  {
    if (!this.teilAufgaben.remove(teilAufgabe))
    {
      throw new IllegalArgumentException("Unbekannte Teilaufgabe " + teilAufgabe + ", aufgabe=" + this);
    }
    teilAufgabe.vaterAufgabe = null;
  }

  /**
   * Wert liefern: {@link #mitarbeiterAufgaben}.
   * 
   * @return Wert (unveränderlich)
   */
  public List<MitarbeiterAufgabe> getMitarbeiterAufgaben()
  {
    return Collections.unmodifiableList(this.mitarbeiterAufgaben);
  }

  /**
   * Mitarbeiter zur Aufgabe hinzufügen.
   * 
   * Der Mitarbeiter darf zunächst <code>null</code> sein, muss aber vor dem Speichern gefüllt werden.
   * 
   * @param mitarbeiter Mitarbeiter
   * @return neue Mitarbeiteraufgabe
   */
  public MitarbeiterAufgabe addMitarbeiterAufgabe(Mitarbeiter mitarbeiter)
  {
    MitarbeiterAufgabe mitarbeiterAufgabe = new MitarbeiterAufgabe(mitarbeiter, this);
    this.mitarbeiterAufgaben.add(mitarbeiterAufgabe);
    return mitarbeiterAufgabe;
  }

  /**
   * Mitarbeiteraufgabe entfernen.
   * 
   * @param mitarbeiterAufgabe Mitarbeiteraufgabe
   */
  public void removeMitarbeiterAufgabe(MitarbeiterAufgabe mitarbeiterAufgabe)
  {
    this.mitarbeiterAufgaben.remove(mitarbeiterAufgabe);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.persistence.entity.SingleIdEntity#toString()
   */
  @Override
  public String toString()
  {
    return this.titel + " (" + this.nummer + ")";
  }

  /**
   * Konstruktor für JPA-interne Zwecke.
   */
  protected Aufgabe()
  {
  }

  /**
   * Absolute Aufgabennummer liefern.
   * 
   * Die absolute Nummer ergibt sich durch Konkatenation aller Vater-Aufgaben-Nummern und der aktuellen Nummer, jeweils getrennt
   * mit einem ':'.
   * 
   * @return absolute Aufgabennummer
   */
  public String getAbsoluteNummer()
  {
    if (this.nummer == null)
    {
      return null;
    }

    if (this.vaterAufgabe != null)
    {
      return this.vaterAufgabe.getAbsoluteNummer() + ":" + this.nummer;
    }

    return this.nummer;
  }

  // /**
  // * Ermittelt die Summe der gebuchten Zeiten für die zugeordneten Mitarbeiteraufgaben.
  // *
  // * Dabei findet keine rekursion über die abhänigen TeilProjekte statt. Summiert werden an dieser Stelle nur die direkt
  // abhänigen
  // * Mitarbeiteraufgaben
  // *
  // * Zur Summierung des gesamten Teil-Projekt-Baumes siehe: {@link #getGebuchteProjektZeiten()}
  // *
  // * @return Summe der gebuchten Minuten (int)
  // */
  // private int getGebuchteMinutenVonMitarbeiteraufgaben()
  // {
  // int zeitenSumme = 0;
  //
  // for (MitarbeiterAufgabe mitarbeiterAufgabe : this.getMitarbeiterAufgaben())
  // {
  // zeitenSumme += mitarbeiterAufgabe.getGebuchteProjektzeiten();
  // }
  //
  // return zeitenSumme;
  // }
  //
  // /**
  // * Summe des gesamten Teil-Projekt-Baumes
  // *
  // * Methode ruft sich rekursiv für alle zugeordneten TeilProjekten auf und summiert für jedes Teilprojekt die gesamten
  // * Mitarbeiteraufgaben.
  // *
  // * @return Summe der gebuchten Minuten des gesamten Teil-Projekt-Baumes
  // */
  // public int getGebuchteProjektZeiten()
  // {
  // int zeitenSumme = 0;
  // zeitenSumme += getGebuchteMinutenVonMitarbeiteraufgaben();
  // for (TeilProjekt teilprojekt : this.getTeilProjekte())
  // {
  // zeitenSumme += teilprojekt.getGebuchteProjektZeiten();
  // }
  //
  // return zeitenSumme;
  // }
  //
  // /**
  // * Ermittelt das zugeordnete oberste Projekt .
  // *
  // * @return Root-Projekt
  // */
  // public Projekt getRootProjekt()
  // {
  // Aufgabe aufgabe = this;
  // while (aufgabe instanceof TeilProjekt)
  // {
  // aufgabe = ((TeilProjekt) aufgabe).getParent();
  // }
  //
  // return (Projekt) aufgabe;
  // }

  @PostLoad
  private void postLoad()
  {
    /*
     * Die Spec sagt nicht eindeutig, ob ein Embeddable, dessen Attribute sämtlich null sind, instanziert wird. Um NPEs aus dem
     * Weg zu gehen, wird hier sichergestellt, dass das Limit erzeugt wurde.
     */
    if (this.limit == null)
    {
      this.limit = new Limit();
    }
  }

}
