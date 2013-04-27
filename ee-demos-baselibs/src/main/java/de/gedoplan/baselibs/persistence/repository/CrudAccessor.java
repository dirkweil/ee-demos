/*
 * Copyright (c) 2009
 * GEDOPLAN Unternehmensberatung und EDV-Organisation GmbH
 * Stieghorster Str. 60, D-33605 Bielefeld, Germany
 * http://www.gedoplan.de
 * All rights reserved.
 */

package de.gedoplan.baselibs.persistence.repository;

import java.util.List;

/**
 * Allgemeine Schnittstelle für eine Klasse, die die CRUD-Operationen (create, read, update, delete = CRUD) auf einer Entity
 * anbietet.
 * 
 * @param <K> Klasse des Schlüssels der Entity
 * @param <E> Entity-Klasse
 */
public interface CrudAccessor<K, E>
{
  /**
   * Neuen Eintrag in der Datenbank speichern.
   * 
   * @param entity neues Entity-Objekt
   */
  public void persist(E entity);

  /**
   * Eintrag in der Datenbank ändern.
   * 
   * <p/>
   * Ein bereits vorhandenes Objekt wird aktualisiert. Als Ergebnis wird das entsprechende Managed-Object geliefert; dies ist eine
   * Kopie, wenn <code>entity</code> ein Detached-Object ist.
   * 
   * <p/>
   * Bemerkung: Ist das übergebene Objekt bislang nicht vorhanden, versucht <code>update</code> es in die DB einzufügen. Es wird
   * aber nicht garantiert, dass dies erfolgreich ist. Für Inserts daher bitte {@link #insert} verwenden!
   * 
   * @param entity Entity-Objekt
   * @return Managed-Object
   */
  public E merge(E entity);

  /**
   * Eintrag aus der Datenbank löschen. <code>entity</code> darf ein Transient/Detached-Object sein.
   * 
   * @param entity Entity-Objekt
   * @return true, falls ein Eintrag gelöscht wurde
   */
  public boolean remove(E entity);

  /**
   * Alle gesammelten Entity-Operationen werden ausgeführt.
   */
  public void flush();

  /**
   * Ist die Entity mit dem EntityManager verbunden?
   * 
   * @param entity Entity
   * @return <code>true</code>, wenn die Entitymit dem EntityManager verbunden ist
   */
  public boolean isAttached(E entity);

  /**
   * Ist die ID bereits gesetzt?
   * 
   * @param entity Entity
   * @return <code>true</code>, falls die ID gesetzt ist
   */
  public boolean isIdSet(E entity);

  /**
   * Eintrag in der Datenbank anhand des Primärschlüssels suchen.
   * 
   * @param id Primärschlüsselwert
   * @return gefundenes Entity-Objekt oder <code>null</code>
   */
  public E findById(K id);

  /**
   * Alle Entity-Objekte vom Typ <code>E</code> suchen.
   * 
   * @return Liste der gefundenen Entity-Objekte oder leere Liste
   */
  public List<E> findAll();

  /**
   * Entity-Objekte vom Typ <code>E</code> suchen und eine Portion davon liefern.
   * 
   * @param firstResult Index des ersten zu liefernden Ergebnisses
   * @param maxResults max. Anzahl zu liefernder Ergebnisse
   * @param sortFieldName Name des Feldes, nach dem sortiert werden soll; <code>null</code> für "keine Sortierung"
   * @param sortDescending <code>true</code>, falls absteigend sortiert werden soll
   * @param queryFilter Liste von Query-Filtern; kann <code>null</code> sein
   * @return Liste der gefundenen Entity-Objekte oder leere Liste
   */
  public List<E> findAll(int firstResult, int maxResults, String sortFieldName, boolean sortDescending, List<QueryFilter> queryFilter);

  /**
   * Entity-Objekte zählen.
   * 
   * @return Anzahl gefundener Entity-Objekte
   */
  public long countAll();

  /**
   * Entity-Objekte zählen.
   * 
   * @param queryFilter Liste von Query-Filtern; kann <code>null</code> sein
   * @return Anzahl gefundener Entity-Objekte
   */
  public long countAll(List<QueryFilter> queryFilter);
}
