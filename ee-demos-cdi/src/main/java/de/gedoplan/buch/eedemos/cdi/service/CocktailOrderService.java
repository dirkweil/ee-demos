package de.gedoplan.buch.eedemos.cdi.service;

import de.gedoplan.buch.eedemos.cdi.entity.Cocktail;
import de.gedoplan.buch.eedemos.cdi.entity.CocktailOrder;
import de.gedoplan.buch.eedemos.cdi.qualifier.Ordered;
import de.gedoplan.buch.eedemos.cdi.repository.CocktailOrderRepository;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@ConversationScoped
public class CocktailOrderService implements Serializable
{
  private de.gedoplan.buch.eedemos.cdi.entity.CocktailOrder cocktailOrder = new CocktailOrder();

  @Inject
  CocktailOrderRepository                                   cocktailOrderRepository;

  @Inject
  @Ordered
  private Event<Cocktail>                                   cocktailOrderEvent;

  @Inject
  private Event<Cocktail>                                   cocktailEvent;

  public List<Cocktail> getCocktails()
  {
    return Collections.unmodifiableList(this.cocktailOrder.getCocktails());
  }

  public void addCocktail(Cocktail cocktail)
  {
    this.cocktailOrder.getCocktails().add(cocktail);
  }

  public void order()
  {
    this.cocktailOrderRepository.insert(this.cocktailOrder);

    for (Cocktail cocktail : this.cocktailOrder.getCocktails())
    {
      // Version 1: Spezielle Event Source
      this.cocktailOrderEvent.fire(cocktail);

      // Version 2: Dynamisch hinzugefügter Qualifier
      // this.cocktailEvent.select(new AnnotationLiteral<Ordered>(){}).fire(cocktail);

      // Version 2b: Wie 2, aber mit Annotationsliteral
      // this.cocktailOrderEvent.select(TakeawayLiteral.INSTANCE).fire(this.cocktailOrder);
      // this.cocktailEvent.select(OrderedLiteral.INSTANCE).fire(cocktail);
    }
  }
}
