package de.gedoplan.buch.eedemos.ejb.repository;

import de.gedoplan.buch.eedemos.ejb.entity.ShopItem;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ShopItemRepository
{
  @PersistenceContext(unitName = "ee-demos")
  private EntityManager entityManager;

  public void insert(ShopItem shopItem)
  {
    this.entityManager.persist(shopItem);
  }

  public List<ShopItem> findAll()
  {
    return this.entityManager.createQuery("select s from ShopItem s", ShopItem.class).getResultList();
  }
}
