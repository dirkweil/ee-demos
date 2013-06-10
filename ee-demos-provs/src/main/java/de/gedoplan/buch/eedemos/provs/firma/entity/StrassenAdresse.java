/**
 * Copyright (c) Gedoplan GmbH, Stieghorster Str. 60, 33605 Bielefeld.
 * www.gedoplan.de
 * 
 * THIS SOURCE CODE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * THIS SOURCE CODE IS CONFIDENTIAL AND PROPRIETARY INFORMATION OF GEDOPLAN GMBH.
 * 
 */
package de.gedoplan.buch.eedemos.provs.firma.entity;

import de.gedoplan.buch.eedemos.provs.firma.validation.constraint.ValidStrassenAdresse;

import java.text.MessageFormat;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Adresse. Dieser Typ ist in Entities einbettbar.
 * 
 */
@Embeddable
@MappedSuperclass
@Access(AccessType.FIELD)
@ValidStrassenAdresse
public class StrassenAdresse
{
  /**
   * Strasse.
   */
  @Column(name = "STRASSE")
  private String strasse;

  /**
   * Hausnummer.
   */
  @Column(name = "HAUS_NR")
  private String hausNummer;

  /**
   * Postleitzahl.
   */
  @Column(name = "PLZ")
  private String plz;

  /**
   * Ort.
   */
  @Column(name = "ORT")
  private String ort;

  /**
   * Land.
   */
  @ManyToOne
  @JoinColumn(name = "LAND_ID")
  private Land   land;

  /**
   * Konstruktor.
   * 
   * @param strasse Strasse
   * @param hausNummer Hausnummer
   * @param plz Postleitzahl
   * @param ort Ort
   * @param land Land
   */
  public StrassenAdresse(String strasse, String hausNummer, String plz, String ort, Land land)
  {
    this.strasse = strasse;
    this.hausNummer = hausNummer;
    this.plz = plz;
    this.ort = ort;
    this.land = land;
  }

  /**
   * Wert liefern: {@link #strasse}.
   * 
   * @return Wert
   */
  public String getStrasse()
  {
    return this.strasse;
  }

  /**
   * Wert setzen: {@link #strasse}.
   * 
   * @param strasse Wert
   */
  public void setStrasse(String strasse)
  {
    this.strasse = strasse;
  }

  /**
   * Wert liefern: {@link #hausNummer}.
   * 
   * @return Wert
   */
  public String getHausNummer()
  {
    return this.hausNummer;
  }

  /**
   * Wert setzen: {@link #hausNummer}.
   * 
   * @param hausNummer Wert
   */
  public void setHausNummer(String hausNummer)
  {
    this.hausNummer = hausNummer;
  }

  /**
   * Wert liefern: {@link #plz}.
   * 
   * @return Wert
   */
  public String getPlz()
  {
    return this.plz;
  }

  /**
   * Wert setzen: {@link #plz}.
   * 
   * @param plz Wert
   */
  public void setPlz(String plz)
  {
    this.plz = plz;
  }

  /**
   * Wert liefern: {@link #ort}.
   * 
   * @return Wert
   */
  public String getOrt()
  {
    return this.ort;
  }

  /**
   * Wert setzen: {@link #ort}.
   * 
   * @param ort Wert
   */
  public void setOrt(String ort)
  {
    this.ort = ort;
  }

  /**
   * Wert liefern: {@link #land}.
   * 
   * @return Wert
   */
  public Land getLand()
  {
    return this.land;
  }

  /**
   * Wert setzen: {@link #land}.
   * 
   * @param land Wert
   */
  public void setLand(Land land)
  {
    this.land = land;
  }

  /**
   * Konstruktor für JPA-interne Zwecke.
   */
  protected StrassenAdresse()
  {
  }

  @Override
  public String toString()
  {
    if (this.land == null)
    {
      return null;
    }

    return MessageFormat.format(this.land.getAdressFormat(), this.land.getName(), this.land.getPlzPraefix(), this.plz, this.ort, this.strasse, this.hausNummer);
  }
}
