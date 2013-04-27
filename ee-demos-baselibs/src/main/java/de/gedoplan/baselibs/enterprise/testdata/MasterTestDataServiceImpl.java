package de.gedoplan.baselibs.enterprise.testdata;

import de.gedoplan.baselibs.utils.exception.BugException;
import de.gedoplan.baselibs.utils.util.ApplicationTime;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.NavigableSet;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Master-Testdaten-Services.
 * 
 * Diese Klasse kann direkt als CDI-Bean verwendet werden, wenn nur lokaler Zugriff nötig ist:
 * 
 * <pre>
 * <code>
 * &#x0040;Inject
 * MasterTestDataService masterTestDataService;
 * ...
 * masterTestDataService.createTestFixture(...);
 * </code>
 * </pre>
 * 
 * Wenn Remote-Zugriff gebraucht wird, muss eine EJB als Wrapper verwendet werden.
 * 
 * Die Klasse führt keinerlei Transaktionssteuerung durch. Dies muss also vom Aufrufer getan werden!
 * 
 * @author dw
 */
public class MasterTestDataServiceImpl implements MasterTestDataService, Serializable
{
  private static final Log                         LOGGER                       = LogFactory.getLog(MasterTestDataServiceImpl.class);

  private static final Comparator<TestDataService> TEST_DATA_SERVICE_COMPARATOR = new Comparator<TestDataService>()
                                                                                {
                                                                                  @Override
                                                                                  public int compare(TestDataService s1, TestDataService s2)
                                                                                  {
                                                                                    return s1.getLevel() - s2.getLevel();
                                                                                  }
                                                                                };

  @Inject
  @Any
  private Instance<TestDataService>                testDataServices;

  private NavigableSet<TestDataService>            orderedTestDataServices;

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.fw.test.MasterTestDataService#loadAllTestData(java.lang.String, java.util.Date, int)
   */
  @Override
  public void loadAllTestData(String szenarioName, Date moment, int maxLevel)
  {
    if (moment == null)
    {
      moment = ApplicationTime.getCurrentDate();
    }

    try
    {
      for (TestDataService tds : this.orderedTestDataServices)
      {
        if (tds.getLevel() <= maxLevel)
        {
          tds.loadTestData(szenarioName, moment);
        }
      }
    }
    catch (RuntimeException ex) // CHECKSTYLE:IGNORE Catch von RuntimeException ausnahmsweise erlaubt, da nur zum Logging
                                // verwendet
    {
      LOGGER.error("Kann Testdaten nicht laden", ex);
      throw ex;
    }
    catch (Exception ex) // CHECKSTYLE:IGNORE Catch von Exception ausnahmsweise erlaubt, da nur zum Logging und Retyping verwendet
    {
      LOGGER.error("Kann Testdaten nicht laden", ex);
      throw new BugException(ex);
    }

  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.fw.test.MasterTestDataService#loadAllTestData(java.lang.String, java.util.Date)
   */
  @Override
  public void loadAllTestData(String szenarioName, Date moment)
  {
    loadAllTestData(szenarioName, moment, Integer.MAX_VALUE);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.fw.test.MasterTestDataService#unloadAllTestData()
   */
  @Override
  public void unloadAllTestData()
  {
    try
    {
      for (TestDataService tds : this.orderedTestDataServices.descendingSet())
      {
        tds.unloadTestData();
      }
    }
    catch (RuntimeException ex) // CHECKSTYLE:IGNORE Catch von RuntimeException ausnahmsweise erlaubt, da nur zum Logging
                                // verwendet
    {
      LOGGER.error("Kann Testdaten nicht entladen", ex);
      throw ex;
    }
    catch (Exception ex) // CHECKSTYLE:IGNORE Catch von Exception ausnahmsweise erlaubt, da nur zum Logging und Retyping verwendet
    {
      LOGGER.error("Kann Testdaten nicht entladen", ex);
      throw new BugException(ex);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.fw.test.MasterTestDataService#unloadAllTestData()
   */
  @Override
  public void createTestFixture(String szenarioName, Date moment)
  {
    unloadAllTestData();
    loadAllTestData(szenarioName, moment);
  }

  /**
   * Testdatenservices nach ihrem Level sortiert bereitstellen.
   */
  @PostConstruct
  protected void sortTestDataServices()
  {
    this.orderedTestDataServices = new TreeSet<TestDataService>(TEST_DATA_SERVICE_COMPARATOR);
    for (TestDataService testDataService : this.testDataServices)
    {
      this.orderedTestDataServices.add(testDataService);
    }

    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("TestDataServices:");
      for (TestDataService testDataService : this.orderedTestDataServices)
      {
        LOGGER.debug(String.format("  %2d %s", testDataService.getLevel(), testDataService));
      }
    }
  }
}
