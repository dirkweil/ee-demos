/*
 * Copyright (c) 2009
 * GEDOPLAN Unternehmensberatung und EDV-Organisation GmbH
 * Stieghorster Str. 60, D-33605 Bielefeld, Germany
 * http://www.gedoplan.de
 * All rights reserved.
 */

package de.gedoplan.baselibs.utils.exception;

/**
 * Exception, die einen Programmfehler symbolisiert.
 */
@SuppressWarnings("serial")
public class BugException extends RuntimeException
{
  /**
   * Exception-Objekt erzeugen.
   *
   * @param message Meldungstext
   * @param cause Auslösende Exception
   */
  public BugException(String message, Throwable cause)
  {
    super(message, cause);
  }

  /**
   * Exception-Objekt erzeugen.
   *
   * @param message Meldungstext
   */
  public BugException(String message)
  {
    super(message);
  }

  /**
   * Exception-Objekt erzeugen.
   *
   * @param cause Auslösende Exception
   */
  public BugException(Throwable cause)
  {
    super(cause);
  }
}