/*
 * Copyright (c) 2010
 * GEDOPLAN Unternehmensberatung und EDV-Organisation GmbH
 * Stieghorster Str. 60, D-33605 Bielefeld, Germany
 * http://www.gedoplan.de
 * All rights reserved.
 */

package de.gedoplan.baselibs.enterprise.testdata;

import java.io.Serializable;
import java.util.Date;

/**
 * Die Schnittstelle <code>TestDataService</code> dient zum Laden (Erzeugen) und Entladen (Zurücksetzen) des Test-Fixture für eine
 * beliebige Anzahl und Art von Testfällen für ein SUT. Im Standardfall wird ein vollständiges Test-Fixture geladen und entladen.
 * Durch einen Übergabeparameter <code>name</code> können ggf. verkleinerte Test-Fixture-Varianten verwaltet werden. <br>
 * 
 * Dieses Interface wird normalerweise zur Erzeugung einer CDI-Bean verwendet, die den Testdaten-Service implementiert:
 * 
 * <pre>
 * <code>
 * public class FirmaTestDataService implements TestDataService
 * {
 *  ...
 * </code>
 * </pre>
 * 
 * Der Aufruf geschieht durch eine von {@link MasterTestDataServiceImpl} abgeleitete EJB.
 */
public interface TestDataService extends Serializable
{
  /**
   * Laden (Erzeugen) von Testdaten.
   * 
   * <p/>
   * Über die Parameter kann gesteuert werden, für welches Szenario und welchen Referenzzeitpunkt Daten erzeugt werden. Der
   * Referenzzeitpunkt ist der Zeitpunkt, 'um den herum' die Testdaten erzeugt werden.
   * 
   * @param szenarioName Szenarioname oder <code>null</code> für Standardszenario
   * @param moment Referenzzeitpunkt oder <code>null</code> für Aufrufzeitpunkt
   * @throws Exception bei Fehlern
   */
  public void loadTestData(String szenarioName, Date moment) throws Exception;

  /**
   * Entladen (Zurücksetzen) von Testdaten.
   * 
   * <p/>
   * Es werden die (Test-)Daten aller Komponenten gelöscht!
   * 
   * @throws Exception bei Fehlern
   */
  public void unloadTestData() throws Exception;

  /**
   * Level der Testdaten holen.
   * 
   * <p/>
   * Das Level definiert eine relative Ordnung der verschiedenen Testdatenservices untereinander: Nur Komponenten eines höheren
   * Levels dürfen auf die niedrigeren Services zugreifen. Damit ergibt sich eine gleichgerichtete Abhängigkeit der Testdaten
   * untereinander: Testdaten höherer Levels referenzieren potenziell niedrigere Daten.
   * 
   * <p/>
   * Das Level wird hier genutzt, um eine Lade- bzw. Entladereihenfolge festzulegen:
   * <ul>
   * <li>Ladereihenfolge: Niedrigere Levels zuerst</li>
   * <li>Entladereihenfolge: Höhere Levels zuerst</li>
   * </ul>
   * 
   * @return Level der Testdaten
   */
  public int getLevel();
}