package de.gedoplan.buch.eedemos.cdi.model;

import de.gedoplan.buch.eedemos.cdi.qualifier.TempDb;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class DbModel implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Inject
  private Connection        dbConnection;

  public String getDbMake()
  {
    return getDescription(this.dbConnection);
  }

  @Inject
  @TempDb
  private Connection tempDbConnection;

  public String getTempDbMake()
  {
    return getDescription(this.tempDbConnection);
  }

  private static String getDescription(Connection connection)
  {
    if (connection == null)
    {
      return "null";
    }

    String url = "";
    try
    {
      url = connection.getMetaData().getURL();
    }
    catch (Exception ignore) // CHECKSTYLE:IGNORE
    {
    }

    try
    {
      DatabaseMetaData metaData = connection.getMetaData();
      return metaData.getDatabaseProductName() + " " + metaData.getDatabaseProductVersion() + " " + url;
    }
    catch (SQLException e)
    {
      return e.toString();
    }
  }

}
