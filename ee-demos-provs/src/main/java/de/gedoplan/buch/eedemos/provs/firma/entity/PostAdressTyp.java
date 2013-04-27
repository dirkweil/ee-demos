package de.gedoplan.buch.eedemos.provs.firma.entity;

import de.gedoplan.baselibs.utils.exception.BugException;

/**
 * Enumeration von Adress-Alternativen (Str vs. PLZ vs. wie Besuchsadresse)
 */
public enum PostAdressTyp
{
  // /**
  // * Strassenadresse.
  // */
  // STRASSE
  // {
  // @Override
  // public String toString()
  // {
  // return "Strasse";
  // }
  // },
  //
  // /**
  // * Postfach.
  // */
  // POSTFACH
  // {
  // @Override
  // public String toString()
  // {
  // return "Postfach";
  // }
  // },
  //
  // /**
  // * Wie Besuchsadresse.
  // */
  // WIE_BESUCHSADRESSE
  // {
  // @Override
  // public String toString()
  // {
  // return "wie Besuchsadresse";
  // }
  // };

  /**
   * Strassenadresse.
   */
  STRASSE,

  /**
   * Postfach.
   */
  POSTFACH,

  /**
   * Wie Besuchsadresse.
   */
  WIE_BESUCHSADRESSE;

  @Override
  public String toString()
  {
    switch (this)
    {
    case STRASSE:
      return "Strasse";
    case POSTFACH:
      return "Postfach";
    case WIE_BESUCHSADRESSE:
      return "wie Besuchsadresse";
    default:
      throw new BugException("Ungültiger Enum-Wert");
    }
  }

}
