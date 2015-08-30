package de.gedoplan.buch.eedemos.cdi.observer;

// CHECKSTYLE:OFF

import de.gedoplan.buch.eedemos.cdi.entity.Cocktail;
import de.gedoplan.buch.eedemos.cdi.qualifier.Ordered;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.commons.logging.Log;

@ApplicationScoped
public class CocktailCounter
{
  private int count = 0;

  @Inject
  private Log logger;

  public void logCocktail(@Observes @Ordered Cocktail cocktail)
  {
    if (this.logger.isDebugEnabled())
    {
      this.logger.debug("Cocktail ordered: " + cocktail.getName());
    }

    ++this.count;
    this.logger.info("Cocktail count: " + this.count);
  }

}
