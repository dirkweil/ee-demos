package de.gedoplan.buch.eedemos.provs.firma.entity;

/**
 * Enumeration von Mitarbeitstypen.
 */
public enum MitarbeitsTyp
{
  /**
   * Geschäftsführer / Vorstand.
   */
  GF
  {
    @Override
    public String toString()
    {
      return "Geschäftsführer / Vorstand";
    }
  },

  /**
   * Abteilungsleiter.
   */
  AL
  {
    @Override
    public String toString()
    {
      return "Abteilungsleiter";
    }
  },

  /**
   * Projektleiter.
   */
  PL
  {
    @Override
    public String toString()
    {
      return "Projektleiter";
    }
  };
}
