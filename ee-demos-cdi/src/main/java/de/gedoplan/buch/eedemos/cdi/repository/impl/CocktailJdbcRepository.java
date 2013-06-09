package de.gedoplan.buch.eedemos.cdi.repository.impl;

import de.gedoplan.buch.eedemos.cdi.entity.Cocktail;
import de.gedoplan.buch.eedemos.cdi.repository.CocktailRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

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
  @Transactional(value = TxType.REQUIRED /*, dontRollbackOn={HarmlessException.class}*/)
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
  @Transactional(value = TxType.REQUIRED /*, dontRollbackOn={HarmlessException.class}*/)
  public void update(Cocktail cocktail)
  {
    throw new RuntimeException("Not yet implemented");
  }

  @Override
  @Transactional(value = TxType.REQUIRED /*, dontRollbackOn={HarmlessException.class}*/)
  public void delete(String id)
  {
    throw new RuntimeException("Not yet implemented");
  }
}
