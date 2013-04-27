package de.gedoplan.buch.eedemos.provs.projekt.entity;

import de.gedoplan.baselibs.utils.exception.BugException;

/**
 * Projekttyp.
 * 
 * @author dw
 */
public enum ProjektTyp
{
  /**
   * Aufwand.
   */
  AW,

  /**
   * Höchstpreis.
   */
  HP,

  /**
   * Festpreis.
   */
  FP;

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString()
  {
    switch (this)
    {
    case AW:
      return "Aufwand";
    case HP:
      return "Höchstpreis";
    case FP:
      return "Festpreis";
    default:
      throw new BugException("Ungültiger Projekttyp");
    }
  }

}
