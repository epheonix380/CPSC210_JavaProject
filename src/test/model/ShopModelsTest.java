package model;

import exceptions.NotEnoughInventory;
import model.shop.InventoryShop;
import model.shop.NonInventoryShop;
import model.shop.Shop;
import model.stock.InventoryStock;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ShopModelsTest {

    private InventoryShop shop1;
    private Shop shop2;

    public void createShop(){
        shop1 = new InventoryShop();
        shop2 = new NonInventoryShop();
    }

    @Test
    public void allShopAddItemToCatalogue(){
        createShop();

        // Adding items to Catalogue
        shop1.addToCatalogue("Banana",300,250);
        shop1.addToCatalogue("Apple",500,300);
        shop2.addToCatalogue("Banana",300,250);
        shop2.addToCatalogue("Apple",500,300);

        // Testing Inventory Shop
        assertTrue(shop1.catalogueContains("Banana"));
        assertTrue(shop1.catalogueContains("Apple"));
        assertEquals("Banana",shop1.getCatalogue().get("banana").getName());
        assertEquals("Apple",shop1.getCatalogue().get("apple").getName());
        assertEquals(300,shop1.getCatalogue().get("banana").getPrice());
        assertEquals(500,shop1.getCatalogue().get("apple").getPrice());
        assertEquals(250,shop1.getCatalogue().get("banana").getUnitCost());
        assertEquals(300,shop1.getCatalogue().get("apple").getUnitCost());

        // Testing NonInventoryShop
        assertTrue(shop2.catalogueContains("Banana"));
        assertTrue(shop2.catalogueContains("Apple"));
        assertEquals("Banana",shop2.getCatalogue().get("banana").getName());
        assertEquals("Apple",shop2.getCatalogue().get("apple").getName());
        assertEquals(300,shop2.getCatalogue().get("banana").getPrice());
        assertEquals(500,shop2.getCatalogue().get("apple").getPrice());
        assertEquals(250,shop2.getCatalogue().get("banana").getUnitCost());
        assertEquals(300,shop2.getCatalogue().get("apple").getUnitCost());
    }

    @Test
    public void allShopEditCatalogue(){
        createShop();

        // Adding items to Catalogue
        shop1.addToCatalogue("Banana",1300,1250);
        shop1.addToCatalogue("Apple",500,1300);
        shop2.addToCatalogue("Banana",1300,1250);
        shop2.addToCatalogue("Apple",1500,300);

        shop1.editCatalogue("Banana",300,250);
        shop1.editCatalogue("Apple",0,300);
        shop2.editCatalogue("Banana",300,250);
        shop2.editCatalogue("Apple",500,0);

        // Testing Inventory Shop
        assertTrue(shop1.catalogueContains("Banana"));
        assertTrue(shop1.catalogueContains("Apple"));
        assertEquals("Banana",shop1.getCatalogue().get("banana").getName());
        assertEquals("Apple",shop1.getCatalogue().get("apple").getName());
        assertEquals(300,shop1.getCatalogue().get("banana").getPrice());
        assertEquals(500,shop1.getCatalogue().get("apple").getPrice());
        assertEquals(250,shop1.getCatalogue().get("banana").getUnitCost());
        assertEquals(300,shop1.getCatalogue().get("apple").getUnitCost());

        // Testing NonInventoryShop
        assertTrue(shop2.catalogueContains("Banana"));
        assertTrue(shop2.catalogueContains("Apple"));
        assertEquals("Banana",shop2.getCatalogue().get("banana").getName());
        assertEquals("Apple",shop2.getCatalogue().get("apple").getName());
        assertEquals(300,shop2.getCatalogue().get("banana").getPrice());
        assertEquals(500,shop2.getCatalogue().get("apple").getPrice());
        assertEquals(250,shop2.getCatalogue().get("banana").getUnitCost());
        assertEquals(300,shop2.getCatalogue().get("apple").getUnitCost());
    }

    @Test
    public void allShopRemoveItemFromCatalogue() {
        createShop();

        // Adding then removing item from shop
        shop1.addToCatalogue("Cookie",1000,100);
        shop2.addToCatalogue("Cookie",1000,100);
        shop1.removeItemFromCatalogue("Cookie");
        shop2.removeItemFromCatalogue("Cookie");

        // Testing item existence
        assertFalse(shop1.catalogueContains("Cookie"));
        assertFalse(shop2.catalogueContains("Cookie"));
    }

    @Test
    public void inventoryShopInventoryIn(){
        allShopAddItemToCatalogue();

        shop1.addInventory("Banana", 50);
        shop1.addInventory("Apple", 5);

        assertTrue(shop1.inventoryContains("Banana"));
        assertTrue(shop1.inventoryContains("Apple"));
        assertEquals("Banana",shop1.getInventoryMap().get("banana").getName());
        assertEquals("Apple",shop1.getInventoryMap().get("apple").getName());
        assertEquals(300,shop1.getInventoryMap().get("banana").getPrice());
        assertEquals(500,shop1.getInventoryMap().get("apple").getPrice());
        assertEquals(250,shop1.getInventoryMap().get("banana").getUnitCost());
        assertEquals(300,shop1.getInventoryMap().get("apple").getUnitCost());
        assertEquals(50,shop1.getInventoryMap().get("banana").getQuantity());
        assertEquals(5,shop1.getInventoryMap().get("apple").getQuantity());
        assertEquals(50 * 300,shop1.getInventoryMap().get("banana").getValue());
        assertEquals(5 * 500,shop1.getInventoryMap().get("apple").getValue());
    }

    @Test
    public void inventoryShopCartAddTest() {
        inventoryShopInventoryIn();

        try {
            shop1.addToCart("Banana", 5);
        } catch (Exception e) {
            assertFalse(true);
        }

        assertFalse(shop1.getCart().isEmpty());
        assertEquals("Banana",shop1.getCart().get("banana").getName());
        assertEquals(300,shop1.getCart().get("banana").getPrice());
        assertEquals(300*5,shop1.getCart().get("banana").getValue());
        assertEquals(5,shop1.getCart().get("banana").getQuantity());
        assertEquals(250,shop1.getCart().get("banana").getUnitCost());
    }

    @Test
    public void inventoryShopCartDestroyTest() {
        inventoryShopInventoryIn();

        try {
            shop1.addToCart("Banana", 5);
        } catch (Exception e) {
            assertFalse(true);
        }
        shop1.destroyCart();

        assertTrue(shop1.getCart().isEmpty());
    }

    @Test
    public void inventoryShopCartModifyTest() {
        inventoryShopInventoryIn();

        try {
            shop1.addToCart("Banana", 5);
        } catch (Exception e) {
            assertFalse(true);
        }
        shop1.modifyCart("Banana",5);

        assertEquals(10,shop1.getCart().get("banana").getQuantity());
    }

    @Test
    public void nonInventoryShopCartAddTest() {
        allShopAddItemToCatalogue();

        try {
            shop2.addToCart("Banana", 5);
        } catch (Exception e) {
            assertFalse(true);
        }

        assertFalse(shop2.getCart().isEmpty());
        assertEquals("Banana",shop2.getCart().get("banana").getName());
        assertEquals(300,shop2.getCart().get("banana").getPrice());
        assertEquals(300*5,shop2.getCart().get("banana").getValue());
        assertEquals(5,shop2.getCart().get("banana").getQuantity());
        assertEquals(250,shop2.getCart().get("banana").getUnitCost());
    }

    @Test
    public void nonInventoryShopCartDestroyTest() {
        allShopAddItemToCatalogue();

        try {
            shop2.addToCart("Banana", 5);
        } catch (Exception e) {
            assertFalse(true);
        }
        shop2.destroyCart();

        assertTrue(shop2.getCart().isEmpty());
    }

    @Test
    public void nonInventoryShopCartModifyTest() {
        allShopAddItemToCatalogue();

        try {
            shop2.addToCart("Banana", 5);
        } catch (Exception e) {
            assertFalse(true);
        }
        shop2.modifyCart("Banana",5);

        assertEquals(10,shop2.getCart().get("banana").getQuantity());
    }

    @Test
    public void sellItemFromNonInventoryShopSuccessfully(){
        inventoryShopInventoryIn();

        try {
            shop2.addToCart("Banana", 5);
            shop2.addToCart("Apple", 5);
        } catch (Exception e) {
            assertFalse(true);
        }
        assertEquals(5*300+5*500,shop2.getCartTotal());
        Receipt receipt;
        try {
            receipt = shop2.makePurchase();
        } catch (Exception e) {
            Map<String, InventoryStock> map = new HashMap<>();
            receipt = new Receipt(0,map);
            assertFalse(true);
        }

        assertEquals(5*300+5*500,receipt.total);
        assertEquals(5,receipt.items.get("banana").getQuantity());
        assertEquals("Banana",receipt.items.get("banana").getName());
        assertEquals(5*300,receipt.items.get("banana").getValue());
        assertEquals(5,receipt.items.get("apple").getQuantity());
        assertEquals("Apple",receipt.items.get("apple").getName());
        assertEquals(5*500,receipt.items.get("apple").getValue());
    }

    @Test
    public void sellItemFromNonInventoryShopSuccessfullyWithCartModification() {
        inventoryShopInventoryIn();

        try {
            shop2.addToCart("Banana", 5);
            shop2.addToCart("Apple", 5);
            shop2.addToCart("Banana", 5);
            shop1.removeFromCart("Apple");
            shop1.addToCart("Apple", 5);
        } catch (Exception e) {
            assertFalse(true);
        }
        assertEquals(10*300+5*500,shop2.getCartTotal());
        Receipt receipt;
        try {
            receipt = shop2.makePurchase();
        } catch (Exception e) {
            Map<String, InventoryStock> map = new HashMap<>();
            receipt = new Receipt(0,map);
            assertFalse(true);
        }

        assertEquals(10*300+5*500,receipt.total);
        assertEquals(10,receipt.items.get("banana").getQuantity());
        assertEquals("Banana",receipt.items.get("banana").getName());
        assertEquals(10*300,receipt.items.get("banana").getValue());
        assertEquals(5,receipt.items.get("apple").getQuantity());
        assertEquals("Apple",receipt.items.get("apple").getName());
        assertEquals(5*500,receipt.items.get("apple").getValue());
    }

    @Test
    public void sellItemFromInventoryShopSuccessfully(){
        inventoryShopInventoryIn();

        try {
            shop1.addToCart("Banana", 5);
            shop1.addToCart("Apple", 5);
        } catch (Exception e) {
            assertFalse(true);
        }
        assertEquals(5*300+5*500,shop1.getCartTotal());

        Receipt receipt;
        try {
            receipt = shop1.makePurchase();
        } catch (Exception e) {
            Map<String, InventoryStock> map = new HashMap<>();
            receipt = new Receipt(0,map);
            assertFalse(true);
        }

        assertEquals(5*300+5*500,receipt.total);
        assertEquals(5,receipt.items.get("banana").getQuantity());
        assertEquals("Banana",receipt.items.get("banana").getName());
        assertEquals(5*300,receipt.items.get("banana").getValue());
        assertEquals(5,receipt.items.get("apple").getQuantity());
        assertEquals("Apple",receipt.items.get("apple").getName());
        assertEquals(5*500,receipt.items.get("apple").getValue());
    }

    @Test
    public void sellItemFromInventoryShopSuccessfullyWithCartModification() {
        inventoryShopInventoryIn();

        try {
            shop1.addToCart("Banana", 5);
            shop1.addToCart("Apple", 5);
            shop1.addToCart("Banana", 5);
            shop1.removeFromCart("Apple");
            shop1.addToCart("Apple", 5);
        } catch (Exception e) {
            assertFalse(true);
        }
        assertEquals(10*300+5*500,shop1.getCartTotal());

        Receipt receipt;
        try {
            receipt = shop1.makePurchase();
        } catch (Exception e) {
            Map<String, InventoryStock> map = new HashMap<>();
            receipt = new Receipt(0,map);
            assertFalse(true);
        }

        assertEquals(10*300+5*500,receipt.total);
        assertEquals(10,receipt.items.get("banana").getQuantity());
        assertEquals("Banana",receipt.items.get("banana").getName());
        assertEquals(10*300,receipt.items.get("banana").getValue());
        assertEquals(5,receipt.items.get("apple").getQuantity());
        assertEquals("Apple",receipt.items.get("apple").getName());
        assertEquals(5*500,receipt.items.get("apple").getValue());
    }

    @Test
    public void sellItemFromInventoryShopUnsuccessfully() {
        sellItemFromInventoryShopSuccessfully();

        try {
            shop1.addToCart("Banana", 5);
        } catch (Exception e) {
            assertFalse(true);
        }
        NotEnoughInventory exception = assertThrows(NotEnoughInventory.class, () -> {
            shop1.addToCart("Apple", 5);
        });

        String expected = "5 items of the name Apple were requested but only 0 are available";
        String actual = exception.errorMessage();

        assertEquals(expected, actual);

    }

    @Test
    public void sellItemFromInventoryShopUnsuccessfullyWithCartModification() {
        inventoryShopInventoryIn();

        try {
            shop1.addToCart("Banana", 5);
            shop1.addToCart("Apple", 5);
            shop1.addToCart("Banana", 5);
        } catch (Exception e) {
            assertFalse(true);
        }
        NotEnoughInventory exception = assertThrows(NotEnoughInventory.class, () -> {
            shop1.addToCart("Apple", 5);
        });

        String expected = "10 items of the name Apple were requested but only 5 are available";
        String actual = exception.errorMessage();

        assertEquals(expected, actual);

    }

    @Test
    public void sellItemFromInventoryShopUnsuccessfullyWithInventoryModification() {
        inventoryShopInventoryIn();

        try {
            shop1.addToCart("Banana", 5);
            shop1.addToCart("Apple", 5);
            shop1.addToCart("Banana", 5);
            shop1.addInventory("Apple", -5);
        } catch (Exception e) {
            assertFalse(true);
        }
        NotEnoughInventory exception = assertThrows(NotEnoughInventory.class, () -> {
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
        assertEquals(10,receipt.items.get("banana").getQuantity());
        assertEquals("Banana",receipt.items.get("banana").getName());
        assertEquals(10*300,receipt.items.get("banana").getValue());
        assertEquals(5,receipt.items.get("apple").getQuantity());
        assertEquals("Apple",receipt.items.get("apple").getName());
        assertEquals(5*500,receipt.items.get("apple").getValue());
    }

    @Test
    public void inventoryShopRecord(){
        sellItemFromInventoryShopSuccessfullyWithCartModification();

        List<Receipt> records = shop1.getRecords();
        Receipt receipt = records.get(0);

        Date date = new Date();
        assertEquals(receipt.getDateTime().getClass().getName(),date.getClass().getName());
        assertEquals(10*300+5*500,receipt.getTotal());
        assertEquals(10,receipt.getItems().get("banana").getQuantity());
        assertEquals("Banana",receipt.getItems().get("banana").getName());
        assertEquals(10*300,receipt.getItems().get("banana").getValue());
        assertEquals(5,receipt.getItems().get("apple").getQuantity());
        assertEquals("Apple",receipt.getItems().get("apple").getName());
        assertEquals(5*500,receipt.getItems().get("apple").getValue());
    }
}