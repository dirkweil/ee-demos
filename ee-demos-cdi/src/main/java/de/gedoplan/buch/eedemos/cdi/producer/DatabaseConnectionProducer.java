package de.gedoplan.buch.eedemos.cdi.producer;

import de.gedoplan.buch.eedemos.cdi.qualifier.TempDb;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@ApplicationScoped
public class DatabaseConnectionProducer
{
  private static Log LOG = LogFactory.getLog(DatabaseConnectionProducer.class);

  /*
   * Achtung: Dies benötigt eine im Server konfigurierte DataSource. S. readme.txt
   */
  @Resource(lookup = "jdbc/ee-demos")
  @Produces
  private DataSource dataSource;

  @Produces
  public Connection createConnection() throws SQLException
  {
    Connection connection = this.dataSource.getConnection();
    LOG.debug("Produced " + connection);
    return connection;
  }

  /*
   * Achtung: Dies benutzt die im serverspezifischen Deskriptor Ressourcen-Referenz für den Namen jdbc/tempDb.
   * 
   * Für GlassFish referenziert der Eintrag die eingebaute Derby-DB, die explizit gestartet werden muss:
   *   asdadmin start-database
   * Bei Start des GLF in Eclipse kann in den Preferences gewählt werden, dass die DB mit dem Server mit gestartet
   * werden soll (Window -> Preferences -> GlassFish.
   * 
   * Für JBoss/WildFly referenziert der Eintrag die ExampleDS, die standardmäßig verfügbar ist.
   */
  // @Resource(lookup = "java:comp/env/jdbc/tempDb")
  @Resource(name = "jdbc/tempDb")
  @Produces
  @TempDb
  private DataSource tempDataSource;

  @Produces
  @TempDb
  public Connection createTempConnection() throws SQLException
  {
    Connection connection = this.tempDataSource.getConnection();
    LOG.debug("Produced " + connection);
    return connection;
  }

  public static boolean disposeConnection(@Disposes @Any Connection connection) throws SQLException
  {
    if (connection != null && !connection.isClosed())
    {
      LOG.debug("Dispose " + connection);
      connection.close();
    }

    return true;
  }
}
