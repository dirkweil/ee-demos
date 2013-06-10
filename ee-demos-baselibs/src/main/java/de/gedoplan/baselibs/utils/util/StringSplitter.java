package de.gedoplan.baselibs.utils.util;

/**
 * String-Aufspalter.
 * 
 * @author dw
 */
public class StringSplitter
{
  private String[] feld;

  /**
   * Konstruktor.
   * 
   * @param string String
   * @param trennPattern Trenn-Pattern (RegEx)
   */
  public StringSplitter(String string, String trennPattern)
  {
    feld = string.split(trennPattern);
  }

  /**
   * Teilstring liefern.
   * 
   * Es wird der Teil des Originalstrings geliefert, der an der durch den Index adressierten Stelle stand. Nicht vorhandene oder leere Teile werden als <code>null</code> geliefert.
   * 
   * @param idx Index
   * @return Teilstring
   */
  public String get(int idx)
  {
    if (idx >= feld.length)
    {
      return null;
    }

    String part = feld[idx].trim();
    if (part.isEmpty())
    {
      return null;
    }

    return part;
  }

}
