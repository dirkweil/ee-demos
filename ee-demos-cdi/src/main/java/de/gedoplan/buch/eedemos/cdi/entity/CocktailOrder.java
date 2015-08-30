package de.gedoplan.buch.eedemos.cdi.entity;

//CHECKSTYLE:OFF

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = CocktailOrder.TABLE_NAME)
public class CocktailOrder extends GeneratedIntegerIdEntity
{
  public static final String TABLE_NAME           = "CDI_COCKTAIL_ORDER";
  public static final String TABLE_NAME_COCKTAILS = "CDI_COCKTAIL_ORDER_COCKTAILS";

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = TABLE_NAME_COCKTAILS, joinColumns = @JoinColumn(name = "COCKTAIL_ID"), inverseJoinColumns = @JoinColumn(name = "ZUTAT_ID"))
  private List<Cocktail>     cocktails            = new ArrayList<>();

  public List<Cocktail> getCocktails()
  {
    return this.cocktails;
  }

}
