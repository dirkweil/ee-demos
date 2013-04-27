package de.gedoplan.buch.eedemos.provs.common.testdata;

/**
 * TestLevel definiert die Reihenfolge, in der Testdaten geladen bzw. entladen werden: Die zuerst genannten Komponenten werden zuerst geladen und zuletzt entladen.
 * 
 * Die Testdatenklassen der Komponenten referenzieren einander in der gleichen Art wie die Daten der Komponenten, d.h. Testdatenklassen (wie auch Daten) höherer Komponenten referenzieren die der
 * darunter liegenden Komponenten. Aus diesem Grund müssen die Lade- und Entladeoperationen in einer definierten Reihenfolge 'von unten nach oben' bzw. umgekehrt ablaufen.
 * 
 */
public enum TestDataLevel
{
  /**
   * Testlevel der Komponente common.
   */
  COMMON,

  /**
   * Testlevel der Komponente firmenmgmt.
   */
  FIRMENMGMT,

  /**
   * Testlevel der Komponente projektmgmt.
   */
  PROJEKTMGMT,
  
  
  /**
   * Testelevel für ProVS-Web-Komponente ProVS-web .
   */
  
  PROVSWEB;

}
