package de.gedoplan.buch.eedemos.provs.projekt.entity;

import de.gedoplan.baselibs.utils.exception.BugException;

/**
 * Projektstatus.
 * 
 * @author dw
 */
public enum ProjektStatus
{
  /**
   * Anfrage.
   */
  ANFRAGE,

  /**
   * Angebot.
   */
  ANGEBOT,

  /**
   * Auftrag.
   */
  AUFTRAG,

  /**
   * Beendet.
   */
  BEENDET,

  /**
   * Abgelehnt.
   */
  ABGELEHNT;

  @Override
  public String toString()
  {
    switch (this)
    {
    case ANFRAGE:
      return "Anfrage";
    case ANGEBOT:
      return "Angebot";
    case AUFTRAG:
      return "Auftrag";
    case BEENDET:
      return "beendet";
    case ABGELEHNT:
      return "abgelehnt";
    default:
      throw new BugException("Ungültiger Projektstatus");
    }
  }
}
