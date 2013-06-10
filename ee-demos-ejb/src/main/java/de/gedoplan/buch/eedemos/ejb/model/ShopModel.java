package de.gedoplan.buch.eedemos.ejb.model;

import de.gedoplan.buch.eedemos.ejb.entity.ShopItem;
import de.gedoplan.buch.eedemos.ejb.service.ShopServiceBean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@ConversationScoped
@Model
public class ShopModel implements Serializable
{
  @Inject
  private ShopServiceBean shopService;

  @Inject
  private Conversation    conversation;

  public List<ShopItem> getShopItems()
  {
    return this.shopService.getAllShopItems();
  }

  public List<ShopItem> getOrderedItems()
  {
    return this.shopService.getAllOrderedItems();
  }

  public void order(ShopItem shopItem)
  {
    if (this.conversation.isTransient())
    {
      this.conversation.begin();
    }

    this.shopService.addItemToOrder(shopItem);
  }

  public void saveOrder()
  {
    if (!this.conversation.isTransient())
    {
      this.shopService.saveOrder();
      terminateOrder();
    }
  }

  public void terminateOrder()
  {
    if (!this.conversation.isTransient())
    {
      this.conversation.end();
      this.shopService.clearOrder();
    }
  }
}
