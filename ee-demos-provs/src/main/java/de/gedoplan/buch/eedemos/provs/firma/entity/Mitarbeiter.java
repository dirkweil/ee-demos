package de.gedoplan.buch.eedemos.provs.firma.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;
import de.gedoplan.baselibs.utils.constraint.ValidMailAddress;
import de.gedoplan.baselibs.utils.constraint.ValidTelNumber;
import de.gedoplan.baselibs.utils.validator.TelNumberValidator;
import de.gedoplan.buch.eedemos.provs.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity-Klasse für Mitarbeiter.
 * 
 * Objekte dieser Klasse verbinden Personen und Firmen miteinander und geben der Verknüpfung einige Attribute mit.
 * 
 * @author dw
 */
@Entity(name = "Mitarbeiter")
@Access(AccessType.FIELD)
@Table(name = Mitarbeiter.TABLE_NAME)
public class Mitarbeiter extends GeneratedIntegerIdEntity
{
  private static final long  serialVersionUID          = 1L;

  /**
   * Name der Haupt-Tabelle.
   */
  public static final String TABLE_NAME                = "PROVS_MITARBEITER";

  /**
   * Name der Tabelle für Telefonnummern.
   */
  public static final String TABLE_NAME_TELEFONNUMMERN = "PROVS_MITARBEITER_TEL";

  /**
   * Person.
   */
  @NotNull
  @ManyToOne
  @JoinColumn(name = "PERSON_ID")
  private Person             person;

  /**
   * Firma.
   */
  @NotNull
  @ManyToOne
  @JoinColumn(name = "FIRMA_ID")
  private Firma              firma;

  /**
   * Standort. Kann <code>null</code> sein, wenn unbekannt bzw. keine Standorte.
   */
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @JoinColumn(name = "STANDORT_ID")
  private Standort           standort;

  /**
   * Mitarbeitstyp. Kann <code>null</code> sein, wenn unbekannt.
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "MA_TYP")
  private MitarbeitsTyp      mitarbeitsTyp;

  /**
   * Abteilung. Kann <code>null</code> sein, wenn unbekannt.
   */
  @Size(min = 1)
  @Column(name = "ABT")
  private String             abteilung;

  /**
   * Telefonnummern inklusive Landesvorwahl (Format wie +49 521 20889-10). Dies stellt eine geordnete Liste dar, in der die
   * primäre Telefonnummer als erste eingetragen ist.
   */
  @ElementCollection
  @CollectionTable(name = TABLE_NAME_TELEFONNUMMERN)
  @Column(name = "TEL")
  @OrderColumn(name = "SORT_IDX")
  private List<String>       telefonNummern;

  /**
   * BV-Validierungsmethode für {@link #telefonNummern}.
   * 
   * @return <code>true</code>, wenn alle Telefonnummern valide sind
   */
  @AssertTrue(message = ValidTelNumber.MESSAGE)
  @Transient
  protected boolean isTelefonNummernValid()
  {
    for (String telNo : this.telefonNummern)
    {
      if (!TelNumberValidator.isValid(telNo))
      {
        return false;
      }
    }
    return true;
  }

  /**
   * E-Mail-Adresse.
   */
  @ValidMailAddress
  @Column(name = "MAIL")
  private String mailAdresse;

  /**
   * Konstruktor.
   * 
   * Mitarbeiter weder ausschliesslich mittels {@link Firma#addMitarbeiter()} erzeugt. Daher ist dieser Konstruktor nicht
   * öffentlich.
   * 
   * @param firma Firma
   */
  /* package_private */Mitarbeiter(Firma firma)
  {
    this.firma = firma;

    this.telefonNummern = new ArrayList<>();
  }

  /**
   * Wert liefern: {@link #person}.
   * 
   * @return Wert
   */
  public Person getPerson()
  {
    return this.person;
  }

  /**
   * Wert setzen: {@link #person}.
   * 
   * @param person Wert
   */
  public void setPerson(Person person)
  {
    if (this.person != null)
    {
      this.person.removeMitarbeiter(this);
    }

    this.person = person;

    if (this.person != null)
    {
      this.person.addMitarbeiter(this);
    }
  }

  /**
   * Wert liefern: {@link #firma}.
   * 
   * @return Wert
   */
  public Firma getFirma()
  {
    return this.firma;
  }

  /**
   * Wert liefern: {@link #standort}.
   * 
   * @return Wert
   */
  public Standort getStandort()
  {
    return this.standort;
  }

  /**
   * Wert setzen: {@link #standort}.
   * 
   * @param standort Wert
   */
  public void setStandort(Standort standort)
  {
    this.standort = standort;
  }

  /**
   * Wert liefern: {@link #mitarbeitsTyp}.
   * 
   * @return Wert
   */
  public MitarbeitsTyp getMitarbeitsTyp()
  {
    return this.mitarbeitsTyp;
  }

  /**
   * Wert setzen: {@link #mitarbeitsTyp}.
   * 
   * @param mitarbeitsTyp Wert
   */
  public void setMitarbeitsTyp(MitarbeitsTyp mitarbeitsTyp)
  {
    this.mitarbeitsTyp = mitarbeitsTyp;
  }

  /**
   * Wert liefern: {@link #abteilung}.
   * 
   * @return Wert
   */
  public String getAbteilung()
  {
    return this.abteilung;
  }

  /**
   * Wert setzen: {@link #abteilung}.
   * 
   * @param abteilung Wert
   */
  public void setAbteilung(String abteilung)
  {
    this.abteilung = StringUtil.interpretEmptyStringAsNull(abteilung);
  }

  /**
   * Wert liefern: {@link #telefonNummern}.
   * 
   * @return Wert
   */
  public List<String> getTelefonNummern()
  {
    return this.telefonNummern;
  }

  /**
   * Telefonnummern zeilenweise in einem String liefern.
   * 
   * @return Telefonnummern, getrennt durch '\n'
   */
  public String getTelefonNummernAsString()
  {
    StringBuilder buf = new StringBuilder();
    boolean first = true;
    for (String telNr : this.telefonNummern)
    {
      if (!first)
      {
        buf.append('\n');
      }
      buf.append(telNr);
      first = false;
    }
    return buf.toString();
  }

  /**
   * Telefonnummern setzen anhand eines Strings, in dem die Nummern zeilenweise enthalten sind.
   * 
   * @param s Telefonnummern, getrennt durch '\n'
   */
  public void setTelefonNummernAsString(String s)
  {
    this.telefonNummern.clear();
    if (s != null)
    {
      for (String telNr : s.trim().split("[ \\t\\x0B\\f\\r]*\\n[ \\t\\x0B\\f\\r]*"))
      {
        if (!telNr.isEmpty())
        {
          this.telefonNummern.add(telNr);
        }
      }
    }
  }

  /**
   * Wert liefern: {@link #mailAdresse}.
   * 
   * @return Wert
   */
  public String getMailAdresse()
  {
    return this.mailAdresse;
  }

  /**
   * Wert setzen: {@link #mailAdresse}.
   * 
   * @param mailAdresse Wert
   */
  public void setMailAdresse(String mailAdresse)
  {
    this.mailAdresse = StringUtil.interpretEmptyStringAsNull(mailAdresse);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.persistence.entity.SingleIdEntity#toString()
   */
  @Override
  public String toString()
  {
    return this.person + " (" + this.firma + ")";
  }

  /**
   * Konstruktor für JPA-interne Zwecke.
   */
  protected Mitarbeiter()
  {
  }
}
