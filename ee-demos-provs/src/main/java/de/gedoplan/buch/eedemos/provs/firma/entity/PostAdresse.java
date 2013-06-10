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

import java.text.MessageFormat;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 * Postalischen Kontaktdaten. Dieser Typ ist in Entities einbettbar.
 * 
 */
@Embeddable
@Access(AccessType.FIELD)
@AttributeOverrides(@AttributeOverride(name = "strasse", column = @Column(name = "STR_PF")))
public class PostAdresse extends StrassenAdresse
{
  /**
   * Typ der Adresse.
   */
  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "TYP")
  private PostAdressTyp typ;

  /**
   * Konstruktor.
   */
  public PostAdresse()
  {
    this.typ = PostAdressTyp.WIE_BESUCHSADRESSE;
  }

  /**
   * Konstruktor.
   * 
   * @param typ Adresstyp
   * @param strasseOderFach Strasse und Hausnummer (für Strassenadressen) bzw. Postfachnummer (für Postfachadressen)
   * @param hausNummer Hausnummer (nur für Strassenadressen, sonst <code>null</code>)
   * @param plz Postleitzahl
   * @param ort Ort
   * @param land Land
   */
  public PostAdresse(PostAdressTyp typ, String strasseOderFach, String hausNummer, String plz, String ort, Land land)
  {
    super(strasseOderFach, hausNummer, plz, ort, land);
    this.typ = typ;
  }

  /**
   * Wert liefern: {@link #typ}.
   * 
   * @return Wert
   */
  public PostAdressTyp getTyp()
  {
    return this.typ;
  }

  /**
   * Wert setzen: {@link #typ}.
   * 
   * @param typ Wert
   */
  public void setTyp(PostAdressTyp typ)
  {
    this.typ = typ;
  }

  /**
   * Postfach liefern. Diese Conveniance-Methode entspricht {@link #getStrasse()}.
   * 
   * @return Wert
   */
  public String getPostfach()
  {
    return getStrasse();
  }

  /**
   * Postfach setzen: Diese Conveniance-Methode entspricht {@link #setStrasse(String)}.
   * 
   * @param postfach Wert
   */
  public void setPostfach(String postfach)
  {
    setStrasse(postfach);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.buch.eedemos.provs.firma.entity.StrassenAdresse#toString()
   */
  @Override
  public String toString()
  {
    switch (this.typ)
    {
    case STRASSE:
      return super.toString();

    case POSTFACH:
      return MessageFormat.format(getLand().getPostfachFormat(), getLand().getName(), getLand().getPlzPraefix(), getPlz(), getOrt(), getPostfach());

    default:
      return null;
    }
  }

  /**
   * Feldkonsistenz herstellen.
   * 
   * Je nach Typ der Adresse werden die nicht genutzten Felder auf <code>null</code> gesetzt.
   * 
   * Diese Methode kann in einer @PrePersist/@PreUpdate-Methode der einbettenden Entity aufgerufen werden, um die enthaltenen
   * Werte konsistent zueinander zu machen. Leiden kann in einem Embeddable nicht direkt @PrePersist oder @PreUpdate verwendet
   * werden.
   */
  public void setUnusedFieldsNull()
  {
    switch (this.typ)
    {
    case POSTFACH:
      setHausNummer(null);
      return;

    case WIE_BESUCHSADRESSE:
      setStrasse(null);
      setHausNummer(null);
      setPlz(null);
      setOrt(null);
      setLand(null);
      return;

    default:
      return;
    }

  }

}
