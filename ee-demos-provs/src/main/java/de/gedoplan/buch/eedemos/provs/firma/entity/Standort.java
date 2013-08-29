package de.gedoplan.buch.eedemos.provs.firma.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;
import de.gedoplan.baselibs.utils.constraint.NotEmpty;
import de.gedoplan.baselibs.utils.constraint.ValidMailAddress;
import de.gedoplan.baselibs.utils.constraint.ValidTelNumber;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.Valid;

/**
 * Entity-Klasse für Standorte.
 * 
 * Standorte werden als anhängige Objekte der Firmen geführt.
 * 
 * @author dw
 */
@Entity(name = "Standort")
@Access(AccessType.FIELD)
@Table(name = Standort.TABLE_NAME)
public class Standort extends GeneratedIntegerIdEntity
{
  /**
   * Tabellenname.
   */
  public static final String TABLE_NAME = "PROVS_STANDORT";

  /**
   * Name.
   */
  @NotEmpty
  @Column(name = "NAME")
  private String             name;

  /**
   * Besuchsadresse. Dient auch als Postadresse, wenn diese nicht gesetzt ist. Ist dieser Wert <code>null</code>, wird die Adresse
   * der zugehörigen Firma verwendet.
   */
  @Valid
  @AttributeOverrides(value = { @AttributeOverride(name = "strasse", column = @Column(name = "BESUCH_STRASSE")), @AttributeOverride(name = "hausNummer", column = @Column(name = "BESUCH_HAUS_NR")),
      @AttributeOverride(name = "plz", column = @Column(name = "BESUCH_PLZ")), @AttributeOverride(name = "ort", column = @Column(name = "BESUCH_ORT")) })
  @AssociationOverride(name = "land", joinColumns = @JoinColumn(name = "BESUCH_LAND_ID"))
  private StrassenAdresse    besuchsAdresse;

  /**
   * Postadresse. Ist dieser Wert <code>null</code>, wird das Postfach der zugehörigen Firma verwendet.
   */
  @Valid
  @AttributeOverrides(value = { @AttributeOverride(name = "typ", column = @Column(name = "POST_ADRESSTYP")), @AttributeOverride(name = "strasse", column = @Column(name = "POST_STR_PF")),
      @AttributeOverride(name = "hausNummer", column = @Column(name = "POST_HAUS_NR")), @AttributeOverride(name = "plz", column = @Column(name = "POST_PLZ")),
      @AttributeOverride(name = "ort", column = @Column(name = "POST_ORT")) })
  @AssociationOverride(name = "land", joinColumns = @JoinColumn(name = "POST_LAND_ID"))
  private PostAdresse        postAdresse;

  /**
   * Telefonnummer inklusive Landesvorwahl (Format wie +49 521 20889-10). Ist dieser Wert <code>null</code>, wird die
   * Telefonnummer der zugehörigen Firma verwendet.
   */
  @ValidTelNumber
  @Column(name = "TEL")
  private String             telefonNummer;

  /**
   * Faxnummer inklusive Landesvorwahl (Format wie +49 521 20889-10). Ist dieser Wert <code>null</code>, wird die Faxnummer der
   * zugehörigen Firma verwendet.
   */
  @ValidTelNumber
  @Column(name = "FAX")
  private String             faxNummer;

  /**
   * E-Mail-Adresse. Sollte zumindest "@" und die Domaine enthalten, kann aber auch eine vollständige Mail-Adresse sein
   * ("info@..."). Ist dieser Wert <code>null</code>, wird die Mail-Adresse der zugehörigen Firma verwendet.
   */
  @ValidMailAddress(nameOptional = true)
  @Column(name = "MAIL")
  private String             mailAdresse;

  /**
   * Kostruktor.
   * 
   * Standorte werden ausschliesslich durch {@link Firma#addStandort(String)} erstellt. Daher ist der Konstruktor nicht public.
   */
  protected Standort()
  {
    this.besuchsAdresse = new StrassenAdresse();
    this.postAdresse = new PostAdresse();
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
    this.telefonNummer = telefonNummer;
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
    this.faxNummer = faxNummer;
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
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.persistence.entity.SingleIdEntity#toString()
   */
  @Override
  public String toString()
  {
    return this.name;
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
    this.postAdresse.setUnusedFieldsNull();
  }
}
