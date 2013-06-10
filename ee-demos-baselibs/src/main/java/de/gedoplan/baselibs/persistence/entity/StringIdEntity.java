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
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Basisklasse für Entities mit einer String-Id.
 * 
 * Entities im Sinne dieser Klasse haben genau ein String-basiertes Schlüsselattribut.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class StringIdEntity extends SingleIdEntity<String>
{
  /**
   * Id.
   */
  @Id
  @Column(name = "ID")
  protected String id;

  /**
   * Konstruktor.
   */
  protected StringIdEntity()
  {
  }

  /**
   * Konstruktor.
   * 
   * @param id Id.
   */
  protected StringIdEntity(String id)
  {
    this.id = id;
  }

  /**
   * Id liefern.
   * 
   * @return Id.
   */
  @Override
  public String getId()
  {
    return this.id;
  }
}
