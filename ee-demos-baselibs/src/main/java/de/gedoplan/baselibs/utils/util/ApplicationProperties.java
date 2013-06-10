/*
 * Copyright (c) 2009
 * GEDOPLAN Unternehmensberatung und EDV-Organisation GmbH
 * Stieghorster Str. 60, D-33605 Bielefeld, Germany
 * http://www.gedoplan.de
 * All rights reserved.
 */

package de.gedoplan.baselibs.utils.util;

/**
 * Utility-Klasse zur Ermittelung globaler Laufzeitparameter.
 * 
 * Dies ist eine eingeschränkte Version, die die Parameter ausschliesslich aus System Properties bezieht.
 * 
 * Die Originalversion dieser Klasse bezieht die Parameter darüber hinaus aus Properties-Dateien und Remote-Services.
 */
public final class ApplicationProperties
{
  /**
   * Property abrufen.
   * 
   * @param id Id
   * @param defaultValue Default-Wert
   * @return Wert
   */
  public static String getProperty(String id, String defaultValue)
  {
    String value = getProperty(id);
    return value != null ? value : defaultValue;
  }

  /**
   * Property abrufen.
   * 
   * Entspricht {@link #getProperty(String, String) getProperty(id, null)}.
   * 
   * @param id Id
   * @return Wert
   */
  public static String getProperty(String id)
  {
    return System.getProperty(id);
  }

  /**
   * Boole'sche Property abrufen.
   * 
   * Property mit {@link #getProperty(String) getProperty(id)} suchen. Wenn gefunden, als Boole'schen Wert parsen, sonst
   * Default-Wert liefern.
   * 
   * @param id Id
   * @param defaultValue Default-Wert
   * @return Wert
   */
  public static boolean getPropertyAsBoolean(String id, boolean defaultValue)
  {
    String value = getProperty(id);
    return value != null ? Boolean.parseBoolean(value) : defaultValue;
  }

  /**
   * Boole'sche Property abrufen.
   * 
   * Entspricht {@link #getPropertyAsBoolean(String, boolean) getProperty(id, false)}.
   * 
   * @param id Id
   * @return Wert
   */
  public static boolean getPropertyAsBoolean(String id)
  {
    return getPropertyAsBoolean(id, false);
  }

  /**
   * Int-Property abrufen.
   * 
   * Property mit {@link #getProperty(String) getProperty(id)} suchen. Wenn gefunden, als Int-Wert parsen, sonst Default-Wert
   * liefern.
   * 
   * @param id Id
   * @param defaultValue Default-Wert
   * @return Wert
   */
  public static int getPropertyAsInt(String id, int defaultValue)
  {
    String value = getProperty(id);
    if (value != null)
    {
      try
      {
        return Integer.parseInt(value);
      }
      catch (NumberFormatException ex)
      {
        // ignore
      }
    }
    return defaultValue;
  }

  /**
   * Int-Property abrufen.
   * 
   * Entspricht {@link #getPropertyAsInt(String, int) getProperty(key, 0)}.
   * 
   * @param id Id
   * @return Wert
   */
  public static int getPropertyAsInt(String id)
  {
    return getPropertyAsInt(id, 0);
  }

  /**
   * Long-Property abrufen.
   * 
   * Property mit {@link #getProperty(String) getProperty(id)} suchen. Wenn gefunden, als Long-Wert parsen, sonst Default-Wert
   * liefern.
   * 
   * @param id Id
   * @param defaultValue Default-Wert
   * @return Wert
   */
  public static long getPropertyAsLong(String id, long defaultValue)
  {
    String value = getProperty(id);
    if (value != null)
    {
      try
      {
        return Long.parseLong(value);
      }
      catch (NumberFormatException ex)
      {
        // ignore
      }
    }
    return defaultValue;
  }

  /**
   * Long-Property abrufen.
   * 
   * Entspricht {@link #getPropertyAsLong(String, long) getProperty(key, 0)}.
   * 
   * @param id Id
   * @return Wert
   */
  public static long getPropertyAsLong(String id)
  {
    return getPropertyAsLong(id, 0);
  }

  // Instanzierung verhindern
  private ApplicationProperties()
  {
  }
}
