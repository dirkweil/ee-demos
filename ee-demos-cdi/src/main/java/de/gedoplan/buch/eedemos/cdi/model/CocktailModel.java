package de.gedoplan.buch.eedemos.cdi.model;

import de.gedoplan.buch.eedemos.cdi.entity.Cocktail;
import de.gedoplan.buch.eedemos.cdi.qualifier.Selected;
import de.gedoplan.buch.eedemos.cdi.repository.CocktailRepository;
import de.gedoplan.buch.eedemos.cdi.service.CocktailOrderService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Model
public class CocktailModel implements Serializable
{
  private static final long  serialVersionUID = 1L;

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
  private CocktailOrderService cocktailOrderService;

  public List<Cocktail> getSelectedCocktails()
  {
    return this.cocktailOrderService.getCocktails();
  }

  @Inject
  @Selected
  private Event<Cocktail> cocktailSelectEvent;

  public void selectCocktail(Cocktail cocktail)
  {
    try
    {
      this.cocktailSelectEvent.fire(cocktail);

      if (this.conversation.isTransient())
      {
        this.conversation.begin();
      }

      this.cocktailOrderService.addCocktail(cocktail);
    }
    catch (Exception e)
    {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
    }
  }

  public String bestellen()
  {
    this.cocktailOrderService.order();

    return abbrechen();
  }

  public String abbrechen()
  {
    if (!this.conversation.isTransient())
    {
      this.conversation.end();
    }

    return FacesContext.getCurrentInstance().getViewRoot().getViewId() + "?faces-redirect=true";
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
