package de.gedoplan.buch.eedemos.cdi.repository;

// CHECKSTYLE:OFF

import de.gedoplan.buch.eedemos.cdi.entity.CocktailOrder;

import java.io.Serializable;

public interface CocktailOrderRepository extends Serializable
{
  public void insert(CocktailOrder cocktailOrder);
}
