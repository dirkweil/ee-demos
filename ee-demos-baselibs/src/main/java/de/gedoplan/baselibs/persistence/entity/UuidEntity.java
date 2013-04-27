/*
 * Copyright (c) 2011
 * GEDOPLAN Unternehmensberatung und EDV-Organisation GmbH
 * Stieghorster Str. 60, D-33605 Bielefeld, Germany
 * http://www.gedoplan.de
 * All rights reserved.
 */

package de.gedoplan.baselibs.persistence.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

/**
 * Basisklasse für alle Entities, welche die Generierung/Verwaltung von eindeutigen Objekt-IDs übernimmt.
 * 
 * Als Schlüssel wird ein einzelnes, String-basiertes Attribut verwendet (siehe {@link StringIdEntity}), das beim Erzeugen eines
 * Objektes auf einen eindeutigen Wert gesetzt wird. Dazu werden UUIDs vom Typ 1 (mit MAC-Adresse und Timestamp) verwendet, um
 * auch über Rechnergrenzen bei multiplen Benutzern eindeutig zu sein.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class UuidEntity extends StringIdEntity
{
  /**
   * Konstruktor.
   */
  public UuidEntity()
  {
    // super(new com.eaio.uuid.UUID().toString());
    super(java.util.UUID.randomUUID().toString());
  }
}