package de.gedoplan.buch.eedemos.cdi.repository;

import de.gedoplan.buch.eedemos.cdi.entity.Cocktail;

import java.io.Serializable;
import java.util.List;

public interface CocktailRepository extends Serializable
{
  public void insert(Cocktail cocktail);

  public Cocktail findById(String id);

  public List<Cocktail> findAll();

  public void update(Cocktail cocktail);

  public void delete(String id);
}
