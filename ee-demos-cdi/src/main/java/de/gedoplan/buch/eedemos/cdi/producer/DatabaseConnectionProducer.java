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

@ApplicationScoped
public class DatabaseConnectionProducer
{
  @Resource(lookup = "jdbc/ee-demos")
  @Produces
  private DataSource dataSource;

  @Produces
  public Connection createConnection() throws SQLException
  {
    return this.dataSource.getConnection();
  }

  // @Resource(lookup = "java:comp/env/jdbc/tempDb")
  @Resource(name = "jdbc/tempDb")
  @Produces
  @TempDb
  private DataSource tempDataSource;

  @Produces
  @TempDb
  public Connection createTempConnection() throws SQLException
  {
    return this.tempDataSource.getConnection();
  }

  public boolean disposeConnection(@Disposes @Any Connection connection) throws SQLException
  {
    if (!connection.isClosed())
    {
      connection.close();
    }

    return true;
  }
}
