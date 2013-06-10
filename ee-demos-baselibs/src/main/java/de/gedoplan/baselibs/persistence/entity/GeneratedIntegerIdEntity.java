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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

/**
 * Basisklasse für Entities mit einer generierten Integer-Id.
 * 
 * Entities im Sinne dieser Klasse haben genau ein Integer-Schlüsselattribut, dessen Wert vom EntityManager automatisch vergeben
 * wird.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class GeneratedIntegerIdEntity extends SingleIdEntity<Integer>
{
  /**
   * Id.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "IntegerIdGenerator")
  @TableGenerator(name = "IntegerIdGenerator", allocationSize = 1000, table = "EE_DEMOS_ID_GENERATOR", pkColumnName = "NAME", valueColumnName = "NEXT_VALUE")
  @Column(name = "ID")
  protected Integer id;

  // /**
  // * Konstruktor.
  // */
  // protected GeneratedIntegerIdEntity()
  // {
  // }

  /**
   * Id liefern.
   * 
   * @return Id.
   */
  @Override
  public Integer getId()
  {
    return this.id;
  }
}
