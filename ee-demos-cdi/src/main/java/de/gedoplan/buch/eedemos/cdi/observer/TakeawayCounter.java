package de.gedoplan.buch.eedemos.cdi.observer;

import de.gedoplan.buch.eedemos.cdi.beans.CocktailOrder;
import de.gedoplan.buch.eedemos.cdi.qualifier.Takeaway;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.commons.logging.Log;

@ApplicationScoped
public class TakeawayCounter
{
  @Inject
  private Log  logger;

  private int  count;

  private Date start = new Date();

  public void count(@Observes @Takeaway CocktailOrder cocktailOrder)
  {
    ++this.count;
    this.logger.info(this.count + " Takeaway-Cocktails bestellt (seit " + this.start + ")");
  }

}
