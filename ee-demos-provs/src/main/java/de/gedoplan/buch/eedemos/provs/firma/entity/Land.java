package de.gedoplan.buch.eedemos.provs.firma.entity;

import de.gedoplan.baselibs.persistence.entity.StringIdEntity;
import de.gedoplan.baselibs.utils.constraint.NotEmpty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Entity-Klasse für Länder.
 * 
 * Diese Entity liefert zu den konfigurierten Ländern ihre Stammdaten wie Namen, Vorwahl oder PLZ-Präfix. Die Id ist der
 * Ländercode nach ISO 3166.
 * 
 * @author dw
 */
@Entity(name = "Land")
@Access(AccessType.FIELD)
@Table(name = Land.TABLE_NAME)
public class Land extends StringIdEntity
{
  private static final long  serialVersionUID        = 1L;

  /**
   * Tabellenname.
   */
  public static final String TABLE_NAME              = "PROVS_LAND";

  /**
   * Name.
   */
  @NotEmpty
  @Column(name = "NAME")
  private String             name;

  /**
   * Postleitzahlenpräfix.
   */
  @NotEmpty
  @Column(name = "PLZ")
  private String             plzPraefix;

  /**
   * Adressformat für normale Adressen. Dieser Text ist ein Pattern für @{link MessageFormat}. Die Platzhalter darin sind:
   * <ul>
   * <li>{0} Land</li>
   * <li>{1} PLZ-Landespräfix</li>
   * <li>{2} PLZ</li>
   * <li>{3} Ort</li>
   * <li>{4} Strasse</li>
   * <li>{5} Hausnummer</li>
   * </ul>
   */
  @Column(name = "ADR_FMT")
  private String             adressFormat;

  /**
   * Default-Adressformat.
   */
  public static final String ADRESS_FORMAT_DEFAULT   = "{4} {5}, {1}-{2} {3}, {0}";

  /**
   * Adressformat für Postfach-Adressen. Dieser Text ist ein Pattern für @{link MessageFormat}. Die Platzhalter darin sind:
   * <ul>
   * <li>{0} Land</li>
   * <li>{1} PLZ-Landespräfix</li>
   * <li>{2} PLZ</li>
   * <li>{3} Ort</li>
   * <li>{4} Postfach</li>
   * </ul>
   */
  @Column(name = "POSTFACH_FMT")
  private String             postfachFormat;

  /**
   * Default-Adressformat.
   */
  public static final String POSTFACH_FORMAT_DEFAULT = "Postfach {4}, {1}-{2} {3}, {0}";

  /**
   * Telefonvorwahl.
   */
  @NotNull
  @Pattern(regexp = "\\+\\d{1,3}", message = "{de.gedoplan.buch.eedemos.provs.firma.entity.Land.telefonVorwahl.message}")
  @Column(name = "TEL")
  private String             telefonVorwahl;

  /**
   * Konstruktor.
   * 
   * @param isoCode Ländercode nach ISO 3166
   * @param name Name
   */
  public Land(String isoCode, String name)
  {
    this.id = isoCode;
    this.name = name;

    this.adressFormat = ADRESS_FORMAT_DEFAULT;
    this.postfachFormat = POSTFACH_FORMAT_DEFAULT;
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
   * Wert liefern: {@link #plzPraefix}.
   * 
   * @return Wert
   */
  public String getPlzPraefix()
  {
    return this.plzPraefix;
  }

  /**
   * Wert setzen: {@link #plzPraefix}.
   * 
   * @param plzPraefix Wert
   */
  public void setPlzPraefix(String plzPraefix)
  {
    this.plzPraefix = plzPraefix;
  }

  /**
   * Wert liefern: {@link #adressFormat}.
   * 
   * @return Wert
   */
  public String getAdressFormat()
  {
    return this.adressFormat;
  }

  /**
   * Wert setzen: {@link #adressFormat}.
   * 
   * @param adressFormat Wert
   */
  public void setAdressFormat(String adressFormat)
  {
    this.adressFormat = adressFormat;
  }

  /**
   * Wert liefern: {@link #postfachFormat}.
   * 
   * @return Wert
   */
  public String getPostfachFormat()
  {
    return this.postfachFormat;
  }

  /**
   * Wert setzen: {@link #postfachFormat}.
   * 
   * @param postfachFormat Wert
   */
  public void setPostfachFormat(String postfachFormat)
  {
    this.postfachFormat = postfachFormat;
  }

  /**
   * Wert liefern: {@link #telefonVorwahl}.
   * 
   * @return Wert
   */
  public String getTelefonVorwahl()
  {
    return this.telefonVorwahl;
  }

  /**
   * Wert setzen: {@link #telefonVorwahl}.
   * 
   * @param telefonVorwahl Wert
   */
  public void setTelefonVorwahl(String telefonVorwahl)
  {
    this.telefonVorwahl = telefonVorwahl;
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
  protected Land()
  {
  }

}
