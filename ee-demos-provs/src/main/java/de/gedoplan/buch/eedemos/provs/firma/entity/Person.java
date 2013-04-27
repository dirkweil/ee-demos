package de.gedoplan.buch.eedemos.provs.firma.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;
import de.gedoplan.baselibs.utils.validation.constraint.NotEmpty;
import de.gedoplan.baselibs.utils.validation.constraint.ValidMailAddress;
import de.gedoplan.baselibs.utils.validation.constraint.ValidTelNumber;
import de.gedoplan.baselibs.utils.validation.validator.TelNumberValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

/**
 * Entity-Klasse für Personen.
 * 
 * Diese Entity modelliert Personen zunächst unabhängig von ihrem geschäftlichen Status. Die Zuordnung zu einer Firma (oder
 * mehreren) wird durch eine entsprechende Relation ausgedrückt.
 * 
 * @author dw
 */
@Entity(name = "Person")
@Access(AccessType.FIELD)
@Table(name = Person.TABLE_NAME)
public class Person extends GeneratedIntegerIdEntity implements Comparable<Person>
{
  /**
   * Name der Haupt-Tabelle.
   */
  public static final String TABLE_NAME                = "PROVS_PERSON";

  /**
   * Name der Tabelle für Telefonnummern.
   */
  public static final String TABLE_NAME_TELEFONNUMMERN = "PROVS_PERSON_TEL";

  /**
   * Name.
   */
  @NotEmpty
  @Column(name = "NAME")
  private String             name;

  /**
   * Vorname.
   */
  @Size(min = 1)
  @Column(name = "VORNAME")
  private String             vorname;

  /**
   * (Privat-)Adresse.
   */
  @Valid
  private StrassenAdresse    adresse;

  /**
   * (Private) Telefonnummern inklusive Landesvorwahl (Format wie +49 521 20889-10). Dies stellt eine gepackte Liste dar, in der
   * die primäre Telefonnummer als erste eingetragen ist.
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
    if (this.telefonNummern != null)
    {
      for (String telNo : this.telefonNummern)
      {
        if (!TelNumberValidator.isValid(telNo))
        {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * (Private) E-Mail-Adresse.
   */
  @ValidMailAddress
  @Column(name = "MAIL")
  private String            mailAdresse;

  /**
   * User-Id. Ist nur bei Mitarbeitern gesetzt, die sich an ProVS anmelden können sollen. Die Id muss dann mit der in der Security
   * Realm übereinstimmen.
   */
  @Size(min = 1)
  @Column(name = "UID")
  private String            userId;

  /**
   * Mitarbeiter. Dies ist die Menge der Zuordnungen der Person zu allen ihren Firmen mit den zugehörigen Verknüpfungsattributen.
   */
  @OneToMany(mappedBy = "person", cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE }, orphanRemoval = true)
  private List<Mitarbeiter> mitarbeiter;

  /**
   * Kostruktor.
   * 
   * @param name Name
   * @param vorname Vorname
   */
  public Person(String name, String vorname)
  {
    clear();

    this.name = name;
    this.vorname = vorname;
  }

  /**
   * Wert liefern: {@link #name}.
   * 
   * @return Wert
   */
  public String getName()
  {
    return this.name;
  }

  /**
   * Wert setzen: {@link #name}.
   * 
   * @param name Wert
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Wert liefern: {@link #vorname}.
   * 
   * @return Wert
   */
  public String getVorname()
  {
    return this.vorname;
  }

  /**
   * Wert setzen: {@link #vorname}.
   * 
   * @param subName Wert
   */
  public void setVorname(String subName)
  {
    this.vorname = subName;
  }

  /**
   * Wert liefern: {@link #adresse}.
   * 
   * @return Wert
   */
  public StrassenAdresse getAdresse()
  {
    if (this.adresse == null)
    {
      this.adresse = new StrassenAdresse();
    }

    return this.adresse;
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

  // REVIEW: Duplizierter Code von Mitarbeiter; auslagern!
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
    this.mailAdresse = mailAdresse;
  }

  /**
   * Wert liefern: {@link #userId}.
   * 
   * @return Wert
   */
  public String getUserId()
  {
    return this.userId;
  }

  /**
   * Wert setzen: {@link #userId}.
   * 
   * @param userId Wert
   */
  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  /**
   * Wert liefern: {@link #mitarbeiter} .
   * 
   * @return Wert (unveränderlich)
   */
  public List<Mitarbeiter> getMitarbeiter()
  {
    return Collections.unmodifiableList(this.mitarbeiter);
  }

  /**
   * Mitarbeiter hinzufügen.
   * 
   * Diese Methode wird ausschliesslich zur Pflege der Relation in {@link Mitarbeiter} benötigt und ist deshalb nicht öffentlich.
   * 
   * @param mitarbeiter
   */
  /* package_private */void addMitarbeiter(Mitarbeiter mitarbeiter)
  {
    this.mitarbeiter.add(mitarbeiter);
  }

  /**
   * Mitarbeiter entfernen.
   * 
   * Diese Methode wird ausschliesslich zur Pflege der Relation in {@link Mitarbeiter} benötigt und ist deshalb nicht öffentlich.
   * 
   * @param mitarbeiter
   */
  /* package_private */void removeMitarbeiter(Mitarbeiter mitarbeiter)
  {
    this.mitarbeiter.remove(mitarbeiter);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.persistence.entity.SingleIdEntity#toString()
   */
  @Override
  public String toString()
  {
    String toString = this.name;
    if (this.vorname != null)
    {
      toString += ", " + this.vorname;
    }
    return toString;
  }

  /**
   * Konstruktor für JPA-interne Zwecke.
   */
  protected Person()
  {
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Person o)
  {
    return this.name.compareTo(o.name);
  }

  @SuppressWarnings("unused")
  @PostLoad
  private void postLoad()
  {
    /*
     * Die Spec sagt nicht eindeutig, ob ein Embeddable, dessen Atribute sämtlich null sind, instanziert wird. Um NPEs aus dem Weg
     * zu gehen, wird hier sichergestellt, dass die Adresse erzeugt wurde.
     */
    if (this.adresse == null)
    {
      this.adresse = new StrassenAdresse();
    }
  }

  /**
   * Neue Person mit gültigem Inhalt liefern.
   * 
   * @return Person
   */
  public static Person createValidInstance()
  {
    Person person = new Person();
    person.name = "Neuer Name";
    return person;
  }

  /**
   * Daten löschen.
   */
  public void clear()
  {
    this.name = null;
    this.vorname = null;

    this.adresse = new StrassenAdresse();

    this.telefonNummern = new ArrayList<>();
    this.mitarbeiter = new ArrayList<>();

    this.mailAdresse = null;
    this.userId = null;
  }
}
