package de.gedoplan.buch.eedemos.provs.firma.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;
import de.gedoplan.baselibs.utils.constraint.NotEmpty;
import de.gedoplan.baselibs.utils.constraint.ValidMailAddress;
import de.gedoplan.baselibs.utils.constraint.ValidTelNumber;
import de.gedoplan.buch.eedemos.provs.common.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.Valid;

/**
 * Entity-Klasse für Firmen.
 * 
 * Eine Firma ist ein (potenzieller) Geschäftspartner. Er hat ggf. Standorte und Mitarbeiter.
 * 
 * @author dw
 */
@Entity(name = "Firma")
@Access(AccessType.FIELD)
@Table(name = Firma.TABLE_NAME)
public class Firma extends GeneratedIntegerIdEntity
{
  private static final long  serialVersionUID = 1L;

  /**
   * Tabellenname.
   */
  public static final String TABLE_NAME       = "PROVS_FIRMA";

  /**
   * Name.
   */
  @NotEmpty
  @Column(name = "NAME", unique = true)
  private String             name;

  /**
   * Besuchsadresse. Dient auch als Postadresse, wenn diese nicht gesetzt ist.
   */
  @Valid
  @AttributeOverrides(value = { @AttributeOverride(name = "strasse", column = @Column(name = "BESUCH_STRASSE")), @AttributeOverride(name = "hausNummer", column = @Column(name = "BESUCH_HAUS_NR")),
      @AttributeOverride(name = "plz", column = @Column(name = "BESUCH_PLZ")), @AttributeOverride(name = "ort", column = @Column(name = "BESUCH_ORT")) })
  @AssociationOverride(name = "land", joinColumns = @JoinColumn(name = "BESUCH_LAND_ID"))
  private StrassenAdresse    besuchsAdresse;

  /**
   * Postadresse.
   */
  @Valid
  @AttributeOverrides(value = { @AttributeOverride(name = "typ", column = @Column(name = "POST_ADRESSTYP")), @AttributeOverride(name = "strasse", column = @Column(name = "POST_STR_PF")),
      @AttributeOverride(name = "hausNummer", column = @Column(name = "POST_HAUS_NR")), @AttributeOverride(name = "plz", column = @Column(name = "POST_PLZ")),
      @AttributeOverride(name = "ort", column = @Column(name = "POST_ORT")) })
  @AssociationOverride(name = "land", joinColumns = @JoinColumn(name = "POST_LAND_ID"))
  private PostAdresse        postAdresse;

  /**
   * Telefonnummer inklusive Landesvorwahl (Format wie +49 521 20889-10)
   */
  @ValidTelNumber
  @Column(name = "TEL")
  private String             telefonNummer;

  /**
   * Faxnummer inklusive Landesvorwahl (Format wie +49 521 20889-10)
   */
  @ValidTelNumber
  @Column(name = "FAX")
  private String             faxNummer;

  /**
   * E-Mail-Adresse. Sollte zumindest "@" und die Domaine enthalten, kann aber auch eine vollständige Mail-Adresse sein
   * ("info@...")
   */
  @ValidMailAddress(nameOptional = true)
  @Column(name = "MAIL")
  private String             mailAdresse;

  /**
   * Standorte.
   */
  @Valid
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "FIRMA_ID", referencedColumnName = "ID")
  @OrderBy("name")
  private List<Standort>     standorte;

  /**
   * Mitarbeiter. Dies ist die Menge der Zuordnungen der Firma zu allen ihren Personen mit den zugehörigen Verknüpfungsattributen.
   */
  @Valid
  @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Mitarbeiter>  mitarbeiter;

  /**
   * Konstruktor.
   * 
   * @param name Name der Firma
   */
  public Firma(String name)
  {
    clear();

    this.name = name;
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
    this.name = StringUtil.interpretEmptyStringAsNull(name);
  }

  /**
   * Wert liefern: {@link #besuchsAdresse}.
   * 
   * @return Wert
   */
  public StrassenAdresse getBesuchsAdresse()
  {
    return this.besuchsAdresse;
  }

  /**
   * Wert liefern: {@link #postAdresse}.
   * 
   * @return Wert
   */
  public PostAdresse getPostAdresse()
  {
    return this.postAdresse;
  }

  /**
   * Wert liefern: {@link #telefonNummer}.
   * 
   * @return Wert
   */
  public String getTelefonNummer()
  {
    return this.telefonNummer;
  }

  /**
   * Wert setzen: {@link #telefonNummer}.
   * 
   * @param telefonNummer Wert
   */
  public void setTelefonNummer(String telefonNummer)
  {
    this.telefonNummer = StringUtil.interpretEmptyStringAsNull(telefonNummer);
  }

  /**
   * Wert liefern: {@link #faxNummer}.
   * 
   * @return Wert
   */
  public String getFaxNummer()
  {
    return this.faxNummer;
  }

  /**
   * Wert setzen: {@link #faxNummer}.
   * 
   * @param faxNummer Wert
   */
  public void setFaxNummer(String faxNummer)
  {
    this.faxNummer = StringUtil.interpretEmptyStringAsNull(faxNummer);
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
   * Wert liefern: {@link #standorte}.
   * 
   * @return Wert (unveränderlich)
   */
  public List<Standort> getStandorte()
  {
    return Collections.unmodifiableList(this.standorte);
  }

  /**
   * Standort suchen.
   * 
   * Diese Conveniance-Methode durchsucht die vorhandenen Standorte nach einem mit dem angegebenen Namen.
   * 
   * @param standortName Name des Standorts
   * @return gefundener Standort oder <code>null</code>
   */
  public Standort getStandort(String standortName)
  {
    for (Standort standort : this.standorte)
    {
      if (standort.getName().equals(standortName))
      {
        return standort;
      }
    }
    return null;
  }

  /**
   * Standort hinzufügen.
   * 
   * @return Neuer Standort
   */
  public Standort addStandort()
  {
    Standort standort = new Standort();
    this.standorte.add(standort);
    return standort;
  }

  /**
   * Standort entfernen.
   * 
   * @param standort Standort
   */
  public void removeStandort(Standort standort)
  {
    for (Mitarbeiter ma : this.mitarbeiter)
    {
      ma.setStandort(null);
    }

    this.standorte.remove(standort);
  }

  /**
   * Wert liefern: {@link #mitarbeiter}.
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
   * @return neuer Mitarbeiter
   */
  public Mitarbeiter addMitarbeiter()
  {
    Mitarbeiter newMa = new Mitarbeiter(this);
    this.mitarbeiter.add(newMa);
    return newMa;
  }

  /**
   * Mitarbeiter entfernen.
   * 
   * @param mitarbeiter Mitarbeiter
   */
  public void removeMitarbeiter(Mitarbeiter mitarbeiter)
  {
    // Mitarbeiter von Person lösen, um Neuanlage durch Kaskadierung nach dem Löschen zu verhindern
    mitarbeiter.setPerson(null);
    this.mitarbeiter.remove(mitarbeiter);
  }

  /**
   * Neue Firma mit gültigem Inhalt liefern.
   * 
   * @return Firma
   */
  public static Firma createValidInstance()
  {
    Firma firma = new Firma();
    firma.name = "Neuer Name";
    return firma;
  }

  /**
   * Daten löschen.
   */
  public void clear()
  {
    this.name = null;

    this.besuchsAdresse = new StrassenAdresse();
    this.postAdresse = new PostAdresse();

    this.standorte = new ArrayList<Standort>();
    this.mitarbeiter = new ArrayList<Mitarbeiter>();

    this.telefonNummer = null;
    this.faxNummer = null;
    this.mailAdresse = null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.persistence.entity.SingleIdEntity#toString()
   */
  @Override
  public String toString()
  {
    return this.name;
  }

  /**
   * Konstruktor für JPA-interne Zwecke.
   */
  protected Firma()
  {
  }

  @PostLoad
  private void postLoad()
  {
    /*
     * Die Spec sagt nicht eindeutig, ob ein Embeddable, dessen Atribute sämtlich null sind, instanziert wird. Um NPEs aus dem Weg
     * zu gehen, wird hier sichergestellt, dass die Adressen erzeugt wurden.
     */
    if (this.besuchsAdresse == null)
    {
      this.besuchsAdresse = new StrassenAdresse();
    }
    if (this.postAdresse == null)
    {
      this.postAdresse = new PostAdresse();
    }
  }

  @PrePersist
  @PreUpdate
  private void preStore()
  {
    if (this.postAdresse != null)
    {
      this.postAdresse.setUnusedFieldsNull();
    }
  }
}
