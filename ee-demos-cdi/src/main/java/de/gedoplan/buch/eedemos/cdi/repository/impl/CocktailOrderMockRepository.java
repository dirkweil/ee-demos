package de.gedoplan.buch.eedemos.cdi.repository.impl;

import de.gedoplan.buch.eedemos.cdi.entity.CocktailOrder;
import de.gedoplan.buch.eedemos.cdi.repository.CocktailOrderRepository;

import javax.transaction.Transactional;

/**
 * Mock-Implementierung des CocktailOrder-Repositories.
 */
@Transactional
public class CocktailOrderMockRepository implements CocktailOrderRepository
{
  private static final long serialVersionUID = 1L;

  @Override
  public void insert(CocktailOrder cocktailOrder)
  {
    // do nothing
  }

}
