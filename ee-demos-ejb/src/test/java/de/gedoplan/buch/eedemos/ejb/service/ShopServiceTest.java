package de.gedoplan.buch.eedemos.ejb.service;

import de.gedoplan.buch.eedemos.ejb.entity.ShopItem;
import de.gedoplan.buch.eedemos.ejb.util.LookupHelper;

import junit.framework.Assert;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.BeforeClass;
import org.junit.Test;

public class ShopServiceTest
{
  private static Context jndiCtx;
  private static String  lookupName;

  @BeforeClass
  public static void beforeClass() throws Exception
  {
    // Anmeldung am JNDI
    jndiCtx = new InitialContext();

    // Lookup-Namen der EJB ermitteln
    lookupName = LookupHelper.getEjbLookupName(ShopService.class, true);
  }

  @Test
  public void testClearOrder() throws Exception
  {
    // Lookup der Bean
    ShopService shopService = (ShopService) jndiCtx.lookup(lookupName);

    // Aufruf von Bean-Methoden
    List<ShopItem> shopItems = shopService.getAllShopItems();
    int shopItemCount = shopItems.size();
    Assert.assertNotSame("Keine ShopItems vorhanden", 0, shopItemCount);

    shopService.addItemToOrder(shopItems.get(0));
    shopService.addItemToOrder(shopItems.get(shopItemCount - 1));
    shopService.clearOrder();
    shopService.close();
  }

  @Test
  public void testSaveOrder() throws Exception
  {
    // Lookup der Bean
    ShopService shopService = (ShopService) jndiCtx.lookup(lookupName);

    // Aufruf von Bean-Methoden
    List<ShopItem> shopItems = shopService.getAllShopItems();
    int shopItemCount = shopItems.size();
    Assert.assertNotSame("Keine ShopItems vorhanden", 0, shopItemCount);

    shopService.addItemToOrder(shopItems.get(0));
    shopService.addItemToOrder(shopItems.get(shopItemCount - 1));
    shopService.saveOrder();
    shopService.close();
  }

}
