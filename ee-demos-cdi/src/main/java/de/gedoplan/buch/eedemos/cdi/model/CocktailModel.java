package de.gedoplan.buch.eedemos.cdi.model;

import de.gedoplan.buch.eedemos.cdi.beans.CocktailOrder;
import de.gedoplan.buch.eedemos.cdi.entity.Cocktail;
import de.gedoplan.buch.eedemos.cdi.qualifier.Inhouse;
import de.gedoplan.buch.eedemos.cdi.qualifier.Takeaway;
import de.gedoplan.buch.eedemos.cdi.repository.CocktailRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@ConversationScoped
@Model
public class CocktailModel implements Serializable
{
  private List<Cocktail>     nonAlcoholicCocktails;
  private List<Cocktail>     alcoholicCocktails;

  @Inject
  private CocktailRepository cocktailRepository;

  public List<Cocktail> getNonAlcoholicCocktails()
  {
    return this.nonAlcoholicCocktails;
  }

  public List<Cocktail> getAlcoholicCocktails()
  {
    return this.alcoholicCocktails;
  }

  @Inject
  private Conversation         conversation;

  @Inject
  private CocktailOrder        cocktailOrder;

  @Inject
  @Inhouse
  private Event<CocktailOrder> cocktailOrderEvent;

  @Inject
  @Takeaway
  private Event<CocktailOrder> cocktailTakeawayOrderEvent;

  public void selectCocktail(Cocktail cocktail)
  {
    if (this.conversation.isTransient())
    {
      this.conversation.begin();
    }

    this.cocktailOrder.addCocktail(cocktail);
  }

  public List<Cocktail> getSelectedCocktails()
  {
    return this.cocktailOrder.getCocktails();
  }

  public void bestellungAbschliessen(boolean takeaway)
  {
    if (!this.conversation.isTransient())
    {
      if (takeaway)
      {
        // Version 1: Spezielle Event Source
        this.cocktailTakeawayOrderEvent.fire(this.cocktailOrder);

        // Version 2: Dynamisch hinzugefügter Qualifier
        // this.cocktailOrderEvent.select(new AnnotationLiteral<Takeaway>(){}).fire(this.cocktailOrder);
      }
      else
      {
        this.cocktailOrderEvent.fire(this.cocktailOrder);
      }

      beenden();
    }
  }

  public void beenden()
  {
    if (!this.conversation.isTransient())
    {
      this.conversation.end();

      this.cocktailOrder.clear();
    }
  }

  @PostConstruct
  public void init()
  {
    this.nonAlcoholicCocktails = new ArrayList<Cocktail>();
    this.alcoholicCocktails = new ArrayList<Cocktail>();

    for (Cocktail c : this.cocktailRepository.findAll())
    {
      if (c.isAlcoholic())
      {
        this.alcoholicCocktails.add(c);
      }
      else
      {
        this.nonAlcoholicCocktails.add(c);
      }
    }

    Collections.sort(this.nonAlcoholicCocktails);
    Collections.sort(this.alcoholicCocktails);
  }

}
