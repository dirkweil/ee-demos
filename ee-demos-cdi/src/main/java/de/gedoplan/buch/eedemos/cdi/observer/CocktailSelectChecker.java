package de.gedoplan.buch.eedemos.cdi.observer;

// CHECKSTYLE:OFF

import de.gedoplan.buch.eedemos.cdi.entity.Cocktail;
import de.gedoplan.buch.eedemos.cdi.entity.CocktailZutat;
import de.gedoplan.buch.eedemos.cdi.qualifier.Selected;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class CocktailSelectChecker
{
  public void select(@Observes @Selected Cocktail cocktail)
  {
    // Hart kodierter Test auf "Rum" - waere im echten Leben z. B. Test auf Lagermengen etc.
    for (CocktailZutat zutat : cocktail.getZutaten())
    {
      if ("Rum".equals(zutat.getName()))
      {
        throw new RuntimeException("Rum ist aus!");
      }
    }
  }

}
