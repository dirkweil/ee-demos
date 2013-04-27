/*
 * Copyright (c) 2008
 * GEDOPLAN Unternehmensberatung und EDV-Organisation GmbH
 * Stieghorster Str. 60, D-33605 Bielefeld, Germany
 * http://www.gedoplan.de
 * All rights reserved.
 */
package de.gedoplan.buch.eedemos.provs.common.testdata;

/**
 * TestSzenario definiert die Konstanten, die als Szenarios bei der Erzeugung von Testdaten (d.h. als Parameter von {@link de.gedoplan.fw.service.TestDataServiceOld#createTestFixture} bzw.
 * {@link de.gedoplan.fw.service.TestDataServiceOld#loadTestData}) genutzt werden können.
 * 
 * Die Test-Szenarien sind nachfolgend in aufsteigender Reihenfolge in Bezug auf den Testdatenumfang aufgeführt. Dies bedeutet, dass jedes Test-Szenario i.d.R. alle Testdaten beinhaltet, die in dem
 * vorhergehenden Test-Szenario bereits geladen sind.
 */
public enum TestDataSzenario
{
  /**
   * Test-Szenario MINIMAL. Das Test-Szenario MINIMAL definiert den Testdatenumfang, der als definierte Basis für den funktionalen Entwicklungs- und Integrationstest des Systems dient und ist wie
   * folgt gekennzeichnet: <br>
   * <ul>
   * <li>Sämtliche Stammdaten</li>
   * <li>Keinerlei Bewegungsdaten</li>
   * </ul>
   */
  MINIMAL,

  /**
   * Test-Szenario DEFAULT. Das Test-Szenario DEFAULT definiert den Testdatenumfang für den funktionalen Test im Zuge der Entwicklung des Systems und ist wie folgt gekennzeichnet: <br>
   * <ul>
   * <li>Daten von {@link #MINIMAL}</li>
   * <li>Für den Entwicklungstest benötigte Bewegungsdaten</li>
   * </ul>
   */
  DEFAULT,

  /**
   * Test-Szenario DEMO. Das Test-Szenario DEMO definiert den Testdatenumfang für eine Demonstration des Systems und ist wie folgt gekennzeichnet: <br>
   * <ul>
   * <li>Sämtliche Stammdaten</li>
   * <li>Keinerlei Bewegungsdaten</li>
   * </ul>
   */
  DEMO;

  /**
   * Szenario zu angegebenem Namen ermitteln.
   * 
   * <p/>
   * Dies entspricht der Methode {@link #valueOf(String)} mit der Abwandlung, dass {@link #DEFAULT} immer dann geliefert wird, wenn der Szenarioname <code>null</code> oder unbekannt ist.
   * 
   * @param szenarioName Szenarioname
   * @return Szenario
   */
  public static TestDataSzenario getSzenario(final String szenarioName)
  {
    TestDataSzenario szenario = DEFAULT;

    if (szenarioName != null)
    {
      try
      {
        szenario = TestDataSzenario.valueOf(szenarioName);
      }
      catch (IllegalArgumentException ex)
      {
        // ignore
      }
    }

    return szenario;
  }
}