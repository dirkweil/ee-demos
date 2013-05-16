package de.gedoplan.buch.eedemos.cdi.repository.impl;

import de.gedoplan.baselibs.enterprise.interceptor.TransactionRequired;
import de.gedoplan.buch.eedemos.cdi.entity.Cocktail;
import de.gedoplan.buch.eedemos.cdi.repository.CocktailRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
 * Achtung: Dies ist eine unvollständige Implementierung des DB-Zugriffs:
 * Es ist nur findAll implementiert, und das auch nur rudimentär (die Zutaten werden nicht gelesen).
 * 
 * Da wir später im Buch aber JPA zur Verfügung haben, lohnt sich hier kein weiterer Aufwand ...
 */
public class CocktailJdbcRepository implements CocktailRepository
{
  @Inject
  private Connection dbConnection;

  @Override
  @TransactionRequired
  public void insert(Cocktail cocktail)
  {
    throw new RuntimeException("Not yet implemented");
  }

  @Override
  public Cocktail findById(String id)
  {
    throw new RuntimeException("Not yet implemented");
  }

  @Override
  public List<Cocktail> findAll()
  {
    List<Cocktail> cocktails = new ArrayList<>();
    Statement statement = null;
    try
    {
      statement = this.dbConnection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT ID,NAME FROM COCKTAIL");
      while (resultSet.next())
      {
        cocktails.add(new Cocktail(resultSet.getString(1), resultSet.getString(2)));
      }
      return cocktails;
    }
    catch (SQLException sqlException)
    {
      throw new RuntimeException(sqlException);
    }
    finally
    {
      try
      {
        statement.close();
      }
      catch (SQLException ignore)
      {
      }
    }
  }

  @Override
  @TransactionRequired
  public void update(Cocktail cocktail)
  {
    throw new RuntimeException("Not yet implemented");
  }

  @Override
  @TransactionRequired
  public void delete(String id)
  {
    throw new RuntimeException("Not yet implemented");
  }
}
