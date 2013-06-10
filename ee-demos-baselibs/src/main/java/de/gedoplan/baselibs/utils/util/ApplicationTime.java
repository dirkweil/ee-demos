/*
 * Copyright (c) 2009
 * GEDOPLAN Unternehmensberatung und EDV-Organisation GmbH
 * Stieghorster Str. 60, D-33605 Bielefeld, Germany
 * http://www.gedoplan.de
 * All rights reserved.
 */

package de.gedoplan.baselibs.utils.util;

import java.util.Date;

/**
 * Klasse zum Ermitteln und Setzen der Applikationszeit.
 * 
 * Dies ist eine eingeschränkte Version, die sich unmittelbar auf der Systemzeit abstützt. Die Originalversion lässt darüber
 * hinaus eine Veränderung der Zeit und ihrer Geschwindigkeit zu.
 */
public final class ApplicationTime
{
  // Instanzierung verhindern
  private ApplicationTime()
  {
  }

  /**
   * Aktuelle Applikationszeit liefern.
   * 
   * @return Applikationszeit in Millisekunden seit 1.1.1970
   */
  public static long getCurrentMillis()
  {
    return System.currentTimeMillis();
  }

  /**
   * Aktuelle Applikationszeit liefern.
   * 
   * @return Applikationszeit
   */
  public static Date getCurrentDate()
  {
    return new Date(getCurrentMillis());
  }
}