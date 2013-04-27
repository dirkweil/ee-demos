package de.gedoplan.baselibs.enterprise.testdata;

import java.util.Date;

/**
 * Business-Interface des Master-Testdaten-Services.
 * 
 * Zur Verwendung siehe {@link MasterTestDataServiceImpl}.
 * 
 * @author dw
 */
public interface MasterTestDataService
{
  /**
   * Laden (Erzeugen) von Testdaten.
   * 
   * <p/>
   * Über die Parameter kann gesteuert werden, für welches Szenario, für welchen Referenzzeitpunkt und bis zu welchem Level Daten erzeugt werden. Der Referenzzeitpunkt ist der Zeitpunkt, 'um den
   * herum' die Testdaten erzeugt werden.
   * 
   * 
   * @param szenarioName Szenarioname oder <code>null</code> für Standardszenario
   * @param moment Referenzzeitpunkt oder <code>null</code> für Aufrufzeitpunkt
   * @param maxLevel Maximales Level
   */
  public void loadAllTestData(String szenarioName, Date moment, int maxLevel);

  /**
   * Laden (Erzeugen) von Testdaten.
   * 
   * <p/>
   * Entspricht {@link #loadAllTestData(String, Date, int) loadAllTestData(szenarioName, moment, Integer.MAX_VALUE)}.
   * 
   * 
   * @param szenarioName Szenarioname oder <code>null</code> für Standardszenario
   * @param moment Referenzzeitpunkt oder <code>null</code> für Aufrufzeitpunkt
   */
  public void loadAllTestData(String szenarioName, Date moment);

  /**
   * Entladen (Zurücksetzen) von Testdaten.
   * 
   * <p/>
   * Es werden die (Test-)Daten aller Komponenten gelöscht!
   */
  public void unloadAllTestData();

  /**
   * Neues Test-Fixture erzeugen.
   * 
   * <p/>
   * Zunächst werden alle ggf. noch vorhandenen Testdaten entladen (d. h. gelöscht). Anschließend werden die gewünschten Testdaten geladen (d. h. erzeugt).
   * 
   * <p/>
   * Über die Parameter kann gesteuert werden, für welches Szenario und welchen Referenzzeitpunkt Daten erzeugt werden. Der Referenzzeitpunkt ist der Zeitpunkt, 'um den herum' die Testdaten erzeugt
   * werden.
   * 
   * @param szenarioName Szenarioname oder <code>null</code> für Standardszenario
   * @param moment Referenzzeitpunkt oder <code>null</code> für Aufrufzeitpunkt
   */
  public void createTestFixture(String szenarioName, Date moment);

}
