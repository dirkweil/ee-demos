package de.gedoplan.buch.eedemos.provs.common.testdata;

import de.gedoplan.baselibs.utils.exception.BugException;
import de.gedoplan.baselibs.utils.util.ResourceUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Helferklasse zum Laden von Testdaten.
 * 
 * @author dw
 */
public final class TestDataLoaderUtil
{
  /**
   * Testdaten aus Ressourcen-File laden.
   * 
   * Der Dateiname wird so zusammengesetzt: "testdata/" + szenario.name() + "_" + qualifier + ".csv";
   * 
   * @param szenario Test-Szenario
   * @param moment Zeitpunkt
   * @param qualifier Qualifizierer für den Dateinamen
   * @param testDataLineLoader Funktionsobjekt zum Laden einer einzelnen Zeile
   */
  public static void loadTestDataFromFile(TestDataSzenario szenario, Date moment, String qualifier, TestDataLineLoader testDataLineLoader)
  {
    InputStream resourceStream = ResourceUtil.getResourceAsStream("testdata/" + szenario.name() + "_" + qualifier + ".csv");
    if (resourceStream == null)
    {
      resourceStream = ResourceUtil.getResourceAsStream("testdata/DEFAULT_" + qualifier + ".csv");
    }
    BufferedReader bufferedReader = null;
    try
    {
      bufferedReader = new BufferedReader(new InputStreamReader(resourceStream, "UTF-8"));

      while (true)
      {
        String line = bufferedReader.readLine();
        if (line == null)
        {
          break;
        }

        line = line.trim();

        if (!line.isEmpty() && !line.startsWith(";"))
        {
          testDataLineLoader.loadTestDataLine(szenario, moment, line);
        }
      }
    }
    catch (Exception ex) // CHECKSTYLE:IGNORE
    {
      throw new BugException("Kann Testdaten nicht laden", ex);
    }
    finally
    {
      try
      {
        bufferedReader.close();
      }
      catch (Exception e) // CHECKSTYLE:IGNORE
      {
        // ignore
      }
      try
      {
        resourceStream.close();
      }
      catch (Exception e) // CHECKSTYLE:IGNORE
      {
        // ignore
      }
    }
  }

  /**
   * Interface für Funktionsobjekte zum Laden von Testdaten aus einer Einzelzeile.
   * 
   * @author dw
   * 
   */
  public static interface TestDataLineLoader
  {
    /**
     * Eine Zeile mit Testdaten laden.
     * 
     * @param szenario Test-Szenario
     * @param moment Zeitpunkt
     * @param line Eingabezeile
     */
    public void loadTestDataLine(TestDataSzenario szenario, Date moment, String line);
  }

  private TestDataLoaderUtil()
  {
  }
}
