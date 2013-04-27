package de.gedoplan.buch.eedemos.cdi.observer;

import de.gedoplan.buch.eedemos.cdi.beans.CocktailOrder;
import de.gedoplan.buch.eedemos.cdi.entity.Cocktail;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.commons.logging.Log;

public class CocktailMixer
{
  @Inject
  private Log logger;

  public void mixCocktail(@Observes CocktailOrder cocktailOrder)
  {
    this.logger.info("Cocktails mixen:");
    for (Cocktail cocktail : cocktailOrder.getCocktails())
    {
      this.logger.info("  " + cocktail.getName());
    }
  }

}
