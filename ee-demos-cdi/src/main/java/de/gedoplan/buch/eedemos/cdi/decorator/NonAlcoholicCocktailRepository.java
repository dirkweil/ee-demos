package de.gedoplan.buch.eedemos.cdi.decorator;

import de.gedoplan.buch.eedemos.cdi.entity.Cocktail;
import de.gedoplan.buch.eedemos.cdi.repository.CocktailRepository;

import java.util.Iterator;
import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@Decorator
public abstract class NonAlcoholicCocktailRepository implements CocktailRepository
{
  @Inject
  @Delegate
  @Any
  private CocktailRepository cocktailRepository;

  @Override
  public List<Cocktail> findAll()
  {
    List<Cocktail> result = this.cocktailRepository.findAll();

    Iterator<Cocktail> iterator = result.iterator();
    while (iterator.hasNext())
    {
      Cocktail cocktail = iterator.next();
      if (cocktail.isAlcoholic())
      {
        iterator.remove();
      }
    }

    return result;
  }

}
