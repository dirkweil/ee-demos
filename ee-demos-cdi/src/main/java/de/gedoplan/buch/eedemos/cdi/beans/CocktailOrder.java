package de.gedoplan.buch.eedemos.cdi.beans;

import de.gedoplan.buch.eedemos.cdi.entity.Cocktail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CocktailOrder implements Serializable
{
  private List<Cocktail> cocktails = new ArrayList<>();

  public List<Cocktail> getCocktails()
  {
    return this.cocktails;
  }

  public void addCocktail(Cocktail cocktail)
  {
    this.cocktails.add(cocktail);
  }

  public void clear()
  {
    this.cocktails.clear();
  }

}
