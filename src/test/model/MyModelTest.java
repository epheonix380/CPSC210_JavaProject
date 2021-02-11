package model;

import model.stock.NIStock;
import model.stock.Stock;
import model.stock.InventoryStock;
import model.shop.InventoryShop;
import model.shop.NonInventoryShop;
import model.shop.Shop;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyModelTest {
    // delete or rename this class!

    private InventoryShop shop1;
    private Shop shop2;

    public void createShop(){
        shop1 = new InventoryShop();
        shop2 = new NonInventoryShop();
    }

    @Test
    public void addItemToShop(){
        createShop();

        shop1.addToCatalogue("Banana",300,250);
        shop1.addToCatalogue("Apple",500,300);
        shop2.addToCatalogue("Banana",300,250);
        shop2.addToCatalogue("Apple",500,300);

        assertTrue(shop1.catalogueContains("Banana"));
        assertTrue(shop1.catalogueContains("Apple"));
        assertEquals("Banana",shop1.getCatalogue().get("Banana").getName());
        assertEquals("Apple",shop1.getCatalogue().get("Apple").getName());
        assertEquals(300,shop1.getCatalogue().get("Banana").getPrice());
        assertEquals(500,shop1.getCatalogue().get("Apple").getPrice());
        assertEquals(250,shop1.getCatalogue().get("Banana").getUnitCost());
        assertEquals(300,shop1.getCatalogue().get("Apple").getUnitCost());

        assertTrue(shop2.catalogueContains("Banana"));
        assertTrue(shop2.catalogueContains("Apple"));
        assertEquals("Banana",shop2.getCatalogue().get("Banana").getName());
        assertEquals("Apple",shop2.getCatalogue().get("Apple").getName());
        assertEquals(300,shop2.getCatalogue().get("Banana").getPrice());
        assertEquals(500,shop2.getCatalogue().get("Apple").getPrice());
        assertEquals(250,shop2.getCatalogue().get("Banana").getUnitCost());
        assertEquals(300,shop2.getCatalogue().get("Apple").getUnitCost());
    }

    @Test
    public void inventoryIn(){
        addItemToShop();

        NIStock stock1 = shop1.getCatalogue().get("Banana");
        NIStock stock2 = shop1.getCatalogue().get("Apple");
        shop1.addInventory(stock1, 50);
        shop1.addInventory(stock2, 5);

        assertTrue(shop1.inventoryContains("Banana"));
        assertTrue(shop1.inventoryContains("Apple"));
        assertEquals("Banana",shop1.getInventoryMap().get("Banana").getName());
        assertEquals("Apple",shop1.getInventoryMap().get("Apple").getName());
        assertEquals(300,shop1.getInventoryMap().get("Banana").getPrice());
        assertEquals(500,shop1.getInventoryMap().get("Apple").getPrice());
        assertEquals(250,shop1.getInventoryMap().get("Banana").getUnitCost());
        assertEquals(300,shop1.getInventoryMap().get("Apple").getUnitCost());
        assertEquals(50,shop1.getInventoryMap().get("Banana").getQuantity());
        assertEquals(5,shop1.getInventoryMap().get("Apple").getQuantity());
        assertEquals(50 * 300,shop1.getInventoryMap().get("Banana").getValue());
        assertEquals(5 * 500,shop1.getInventoryMap().get("Apple").getValue());
    }

    @Test
    public void sellItemFromShopSuccessfully(){

    }

    @Test
    public void shopRecord(){

    }

    @Test
    public void shopReceipt(){

    }
}