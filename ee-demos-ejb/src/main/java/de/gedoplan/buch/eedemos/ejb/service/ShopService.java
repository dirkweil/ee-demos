package de.gedoplan.buch.eedemos.ejb.service;

import de.gedoplan.buch.eedemos.ejb.entity.ShopItem;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ShopService
{
  public List<ShopItem> getAllShopItems();

  public List<ShopItem> getAllOrderedItems();

  public void addItemToOrder(ShopItem shopItem);

  public void saveOrder();

  public void clearOrder();

  public void close();
}
