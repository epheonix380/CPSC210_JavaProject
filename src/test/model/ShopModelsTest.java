package model;

import errors.NotEnoughInventoryError;
import model.stock.NIStock;
import model.stock.Stock;
import model.stock.InventoryStock;
import model.shop.InventoryShop;
import model.shop.NonInventoryShop;
import model.shop.Shop;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopModelsTest {

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
        shop1.addToCatalogue("Cookie",1000,100);
        shop2.addToCatalogue("Cookie",1000,100);
        NIStock cookieStock1 = shop1.getCatalogue().get("Cookie");
        NIStock cookieStock2 = shop2.getCatalogue().get("Cookie");
        shop1.removeItemFromCatalogue(cookieStock1);
        shop2.removeItemFromCatalogue(cookieStock2);

        assertFalse(shop1.catalogueContains("Cookie"));
        assertFalse(shop2.catalogueContains("Cookie"));
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
    public void cartDestroyTest() {
        inventoryIn();

        NIStock stock1 = shop1.getCatalogue().get("Banana");
        NIStock stock2 = shop1.getCatalogue().get("Apple");
        shop1.addToCart(stock1, 5);
        shop1.destroyCart();

        assertTrue(shop1.getCart().isEmpty());
    }

    @Test
    public void cartModifyTest() {
        inventoryIn();

        NIStock stock1 = shop1.getCatalogue().get("Banana");
        shop1.addToCart(stock1, 5);
        shop1.modifyCart(shop1.getCart().get("Banana"),5);

        assertEquals(10,shop1.getCart().get("Banana").getQuantity());
    }

    @Test
    public void sellItemFromNonInventoryShopSuccessfully(){
        inventoryIn();

        NIStock stock1 = shop2.getCatalogue().get("Banana");
        NIStock stock2 = shop2.getCatalogue().get("Apple");
        shop2.addToCart(stock1, 5);
        shop2.addToCart(stock2, 5);

        assertEquals(5*300+5*500,shop2.getCartTotal());

        Receipt receipt = shop2.makePurchase();

        assertEquals(5*300+5*500,receipt.total);
        assertEquals(5,receipt.items.get("Banana").getQuantity());
        assertEquals("Banana",receipt.items.get("Banana").getName());
        assertEquals(5*300,receipt.items.get("Banana").getValue());
        assertEquals(5,receipt.items.get("Apple").getQuantity());
        assertEquals("Apple",receipt.items.get("Apple").getName());
        assertEquals(5*500,receipt.items.get("Apple").getValue());
    }

    @Test
    public void sellItemFromNonInventoryShopSuccessfullyWithCartModification() {
        inventoryIn();

        NIStock stock1 = shop2.getCatalogue().get("Banana");
        NIStock stock2 = shop2.getCatalogue().get("Apple");
        shop2.addToCart(stock1, 5);
        shop2.addToCart(stock2, 5);
        shop2.addToCart(stock1, 5);
        shop1.removeFromCart("Apple");
        shop1.addToCart(stock2,5);

        assertEquals(10*300+5*500,shop2.getCartTotal());

        Receipt receipt = shop2.makePurchase();

        assertEquals(10*300+5*500,receipt.total);
        assertEquals(10,receipt.items.get("Banana").getQuantity());
        assertEquals("Banana",receipt.items.get("Banana").getName());
        assertEquals(10*300,receipt.items.get("Banana").getValue());
        assertEquals(5,receipt.items.get("Apple").getQuantity());
        assertEquals("Apple",receipt.items.get("Apple").getName());
        assertEquals(5*500,receipt.items.get("Apple").getValue());
    }

    @Test
    public void sellItemFromInventoryShopSuccessfully(){
        inventoryIn();

        NIStock stock1 = shop1.getCatalogue().get("Banana");
        NIStock stock2 = shop1.getCatalogue().get("Apple");
        shop1.addToCart(stock1, 5);
        shop1.addToCart(stock2, 5);

        assertEquals(5*300+5*500,shop1.getCartTotal());

        Receipt receipt = shop1.makePurchase();

        assertEquals(5*300+5*500,receipt.total);
        assertEquals(5,receipt.items.get("Banana").getQuantity());
        assertEquals("Banana",receipt.items.get("Banana").getName());
        assertEquals(5*300,receipt.items.get("Banana").getValue());
        assertEquals(5,receipt.items.get("Apple").getQuantity());
        assertEquals("Apple",receipt.items.get("Apple").getName());
        assertEquals(5*500,receipt.items.get("Apple").getValue());
    }

    @Test
    public void sellItemFromInventoryShopSuccessfullyWithCartModification() {
        inventoryIn();

        NIStock stock1 = shop1.getCatalogue().get("Banana");
        NIStock stock2 = shop1.getCatalogue().get("Apple");
        shop1.addToCart(stock1, 5);
        shop1.addToCart(stock2, 5);
        shop1.addToCart(stock1, 5);
        shop1.removeFromCart("Apple");
        shop1.addToCart(stock2,5);

        assertEquals(10*300+5*500,shop1.getCartTotal());

        Receipt receipt = shop1.makePurchase();

        assertEquals(10*300+5*500,receipt.total);
        assertEquals(10,receipt.items.get("Banana").getQuantity());
        assertEquals("Banana",receipt.items.get("Banana").getName());
        assertEquals(10*300,receipt.items.get("Banana").getValue());
        assertEquals(5,receipt.items.get("Apple").getQuantity());
        assertEquals("Apple",receipt.items.get("Apple").getName());
        assertEquals(5*500,receipt.items.get("Apple").getValue());
    }

    @Test
    public void sellItemFromInventoryShopUnsuccessfully() {
        sellItemFromInventoryShopSuccessfully();

        NIStock stock1 = shop1.getCatalogue().get("Banana");
        NIStock stock2 = shop1.getCatalogue().get("Apple");
        shop1.addToCart(stock1, 5);

        NotEnoughInventoryError exception = assertThrows(NotEnoughInventoryError.class, () -> {
            shop1.addToCart(stock2, 5);
        });

        String expected = "5 items of the name Apple were requested but only 0 are available";
        String actual = exception.errorMessage();

        assertEquals(expected, actual);

    }

    @Test
    public void sellItemFromInventoryShopUnsuccessfullyWithCartModification() {
        inventoryIn();

        NIStock stock1 = shop1.getCatalogue().get("Banana");
        NIStock stock2 = shop1.getCatalogue().get("Apple");
        shop1.addToCart(stock1, 5);
        shop1.addToCart(stock2, 5);
        shop1.addToCart(stock1, 5);

        NotEnoughInventoryError exception = assertThrows(NotEnoughInventoryError.class, () -> {
            shop1.addToCart(stock2, 5);
        });

        String expected = "10 items of the name Apple were requested but only 5 are available";
        String actual = exception.errorMessage();

        assertEquals(expected, actual);

    }

    @Test
    public void sellItemFromInventoryShopUnsuccessfullyWithInventoryModification() {
        inventoryIn();

        NIStock stock1 = shop1.getCatalogue().get("Banana");
        NIStock stock2 = shop1.getCatalogue().get("Apple");
        shop1.addToCart(stock1, 5);
        shop1.addToCart(stock2, 5);
        shop1.addToCart(stock1, 5);
        shop1.addInventory(stock2, -5);

        NotEnoughInventoryError exception = assertThrows(NotEnoughInventoryError.class, () -> {
            shop1.makePurchase();
        });

        String expected = "5 items of the name Apple were requested but only 0 are available";
        String actual = exception.errorMessage();

        assertEquals(expected, actual);

    }

    @Test
    public void nonInventoryShopRecord(){
        sellItemFromNonInventoryShopSuccessfullyWithCartModification();

        List<Receipt> records = shop2.getRecords();
        Receipt receipt = records.get(0);

        assertEquals(10*300+5*500,receipt.total);
        assertEquals(10,receipt.items.get("Banana").getQuantity());
        assertEquals("Banana",receipt.items.get("Banana").getName());
        assertEquals(10*300,receipt.items.get("Banana").getValue());
        assertEquals(5,receipt.items.get("Apple").getQuantity());
        assertEquals("Apple",receipt.items.get("Apple").getName());
        assertEquals(5*500,receipt.items.get("Apple").getValue());
    }

    @Test
    public void inventoryShopRecord(){
        sellItemFromInventoryShopSuccessfullyWithCartModification();

        List<Receipt> records = shop1.getRecords();
        Receipt receipt = records.get(0);

        assertEquals(10*300+5*500,receipt.total);
        assertEquals(10,receipt.items.get("Banana").getQuantity());
        assertEquals("Banana",receipt.items.get("Banana").getName());
        assertEquals(10*300,receipt.items.get("Banana").getValue());
        assertEquals(5,receipt.items.get("Apple").getQuantity());
        assertEquals("Apple",receipt.items.get("Apple").getName());
        assertEquals(5*500,receipt.items.get("Apple").getValue());
    }
}