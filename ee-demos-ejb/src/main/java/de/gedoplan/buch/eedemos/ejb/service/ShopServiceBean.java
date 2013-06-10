package de.gedoplan.buch.eedemos.ejb.service;

import de.gedoplan.buch.eedemos.ejb.entity.ShopItem;
import de.gedoplan.buch.eedemos.ejb.entity.ShopOrder;
import de.gedoplan.buch.eedemos.ejb.repository.ShopItemRepository;
import de.gedoplan.buch.eedemos.ejb.repository.ShopOrderRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.inject.Inject;

import org.apache.commons.logging.Log;

@Stateful
@StatefulTimeout(value = 10, unit = TimeUnit.MINUTES)
@LocalBean
// Alternativ hier statt im Interface: @Remote(ShopService.class)
// @TransactionManagement(TransactionManagementType.CONTAINER)
// @TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ShopServiceBean implements ShopService
{
  @Inject
  private ShopItemRepository  shopItemRepository;

  @Inject
  private ShopOrderRepository shopOrderRepository;

  @Inject
  private Log                 log;

  private ShopOrder           shopOrder = new ShopOrder();

  private List<ShopItem>      allShopItems;

  @Override
  public List<ShopItem> getAllShopItems()
  {
    if (this.allShopItems == null)
    {
      initAllShopItems();
    }

    return this.allShopItems;
  }

  private void initAllShopItems()
  {
    this.allShopItems = this.shopItemRepository.findAll();

    // Der folgende Code dient nur der bequemen Befüllung der Shop-Item-Tabelle in diesem Demo-Programm
    if (this.allShopItems.isEmpty())
    {
      ShopItem shopItem = new ShopItem("Hammer", 9.60);
      this.allShopItems.add(shopItem);
      this.shopItemRepository.insert(shopItem);

      shopItem = new ShopItem("Säge", 17.50);
      this.allShopItems.add(shopItem);
      this.shopItemRepository.insert(shopItem);

      shopItem = new ShopItem("Verbandskasten", 25.80);
      this.allShopItems.add(shopItem);
      this.shopItemRepository.insert(shopItem);
    }
  }

  @Override
  public List<ShopItem> getAllOrderedItems()
  {
    return this.shopOrder.getShopItems();
  }

  @Override
  public void addItemToOrder(ShopItem shopItem)
  {
    this.shopOrder.getShopItems().add(shopItem);
  }

  @Override
  public void saveOrder()
  {
    this.shopOrderRepository.insert(this.shopOrder);

    this.log.debug("ShopOrder " + this.shopOrder.getId() + " gespeichert:");
    for (ShopItem shopItem : this.shopOrder.getShopItems())
    {
      this.log.debug("  " + shopItem);
    }
  }

  @Override
  public void clearOrder()
  {
    this.shopOrder.getShopItems().clear();
  }

  @Override
  @Remove
  public void close()
  {
  }

  @PostConstruct
  public void postConstruct()
  {
    this.log.debug("created");
  }

  @PreDestroy
  public void preDestroy()
  {
    this.log.debug("remove");
  }
}
