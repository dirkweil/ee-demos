package de.gedoplan.buch.eedemos.ejb.repository;

import de.gedoplan.buch.eedemos.ejb.entity.ShopOrder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ShopOrderRepository
{
  @PersistenceContext(unitName = "ee-demos")
  private EntityManager entityManager;

  public void insert(ShopOrder shopOrder)
  {
    this.entityManager.persist(shopOrder);
  }
}
