package model;

import exceptions.ItemAlreadyExists;
import exceptions.ItemNotFound;
import exceptions.NotEnoughInventory;
import exceptions.NotPositiveInteger;
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
        shop1 = new InventoryShop("invTestShop");
        shop2 = new NonInventoryShop("nonInvTestShop");
    }

    @Test
    public void allShopAddItemToCatalogue(){
        createShop();

        // Adding items to Catalogue
        try {
            shop1.addToCatalogue("Banana",300,250);
            shop1.addToCatalogue("Apple",500,300);
            shop2.addToCatalogue("Banana",300,250);
            shop2.addToCatalogue("Apple",500,300);
        } catch (Exception e) {
            assertFalse(true);
        }

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
    public void allShopAddItemToCatalogueException() {
        allShopAddItemToCatalogue();

        ItemAlreadyExists itemAlreadyExists1 = assertThrows(ItemAlreadyExists.class, () -> {
            shop1.addToCatalogue("Banana", 200,100);
        });
        ItemAlreadyExists itemAlreadyExists2 = assertThrows(ItemAlreadyExists.class, () -> {
            shop2.addToCatalogue("Banana", 200,100);
        });
        NotPositiveInteger negativeInteger1a = assertThrows(NotPositiveInteger.class, () -> {
            shop1.addToCatalogue("Unique Name", 0, 100);
        });
        NotPositiveInteger negativeInteger1b = assertThrows(NotPositiveInteger.class, () -> {
            shop1.addToCatalogue("Unique Name", 100, -200);
        });
        NotPositiveInteger negativeInteger2a = assertThrows(NotPositiveInteger.class, () -> {
            shop2.addToCatalogue("Unique Name", -150, 100);
        });
        NotPositiveInteger negativeInteger2b = assertThrows(NotPositiveInteger.class, () -> {
            shop2.addToCatalogue("Unique Name", 100, -250);
        });
        String strA = "The inputted integer ";
        String strB = " is either zero or smaller. The int needs to be a positive integer";
        String pt1 = "The item you attempted to create already exists,";
        String pt2 = " please use edit to change already existing items";
        String str1 = strA + "0" + strB;
        String str2 = strA + "-200" + strB;
        String str3 = strA + "-150" + strB;
        String str4 = strA + "-250" + strB;
        String str5 = pt1 + pt2;
        assertEquals(str5,itemAlreadyExists1.getMessage());
        assertEquals(str5,itemAlreadyExists2.getMessage());
        assertEquals(str1,negativeInteger1a.getMessage());
        assertEquals(str2,negativeInteger1b.getMessage());
        assertEquals(str3,negativeInteger2a.getMessage());
        assertEquals(str4,negativeInteger2b.getMessage());
    }

    @Test
    public void allShopEditCatalogue(){
        createShop();

        // Adding items to Catalogue
        try {
            shop1.addToCatalogue("Banana", 1300, 1250);
            shop1.addToCatalogue("Apple", 500, 1300);
            shop2.addToCatalogue("Banana", 1300, 1250);
            shop2.addToCatalogue("Apple", 1500, 300);

            shop1.editCatalogue("Banana", 300, 250);
            shop1.editCatalogue("Apple", 0, 300);
            shop2.editCatalogue("Banana", 300, 250);
            shop2.editCatalogue("Apple", 500, 0);
        } catch (Exception e) {
            assertFalse(true);
        }
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
    public void allShopEditCatalogueException(){
        allShopEditCatalogue();

        ItemNotFound itemNotFound1 = assertThrows(ItemNotFound.class, () -> {
            shop1.editCatalogue("Dragon Fruit", 200,100);
        });
        ItemNotFound itemNotFound2 = assertThrows(ItemNotFound.class, () -> {
            shop2.editCatalogue("Dragon Fruit", 200,100);
        });
        NotPositiveInteger negativeInteger1a = assertThrows(NotPositiveInteger.class, () -> {
            shop1.editCatalogue("Banana", -1, 100);
        });
        NotPositiveInteger negativeInteger1b = assertThrows(NotPositiveInteger.class, () -> {
            shop1.editCatalogue("Banana", 100, -200);
        });
        NotPositiveInteger negativeInteger2a = assertThrows(NotPositiveInteger.class, () -> {
            shop2.editCatalogue("Banana", -150, 100);
        });
        NotPositiveInteger negativeInteger2b = assertThrows(NotPositiveInteger.class, () -> {
            shop2.editCatalogue("Banana", 100, -250);
        });
        String strA = "The inputted integer ";
        String strB = " is either zero or smaller. The int needs to be a positive integer";
        String str1 = strA + "-1" + strB;
        String str2 = strA + "-200" + strB;
        String str3 = strA + "-150" + strB;
        String str4 = strA + "-250" + strB;
        assertEquals(str1,negativeInteger1a.getMessage());
        assertEquals(str2,negativeInteger1b.getMessage());
        assertEquals(str3,negativeInteger2a.getMessage());
        assertEquals(str4,negativeInteger2b.getMessage());
    }

    @Test
    public void allShopRemoveItemFromCatalogue() {
        createShop();

        // Adding then removing item from shop
        try {
            shop1.addToCatalogue("Cookie", 1000, 100);
            shop2.addToCatalogue("Cookie", 1000, 100);
            shop1.removeItemFromCatalogue("Cookie");
            shop2.removeItemFromCatalogue("Cookie");
        } catch (Exception e) {
            assertFalse(true);
        }
        // Testing item existence
        assertFalse(shop1.catalogueContains("Cookie"));
        assertFalse(shop2.catalogueContains("Cookie"));
    }

    @Test
    public void allShopRemoveItemFromCatalogueException() {
        createShop();

        // Adding then removing item from shop
        try {
            shop1.addToCatalogue("Cookie", 1000, 100);
            shop2.addToCatalogue("Cookie", 1000, 100);
        } catch (Exception e) {
            assertFalse(true);
        }

        Exception itemNotFound1 = assertThrows(ItemNotFound.class, () -> {
            shop1.removeItemFromCatalogue("Dragon Fruit");
        });
        Exception itemNotFound2 = assertThrows(ItemNotFound.class, () -> {
            shop2.removeItemFromCatalogue("Dragon Fruit");
        });
    }

    @Test
    public void inventoryShopInventoryIn(){
        allShopAddItemToCatalogue();

        try {
            shop1.addInventory("Banana", 50);
            shop1.addInventory("Apple", 5);
        } catch (Exception e) {
            assertFalse(true);
        }
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
    public void inventoryShopInventoryInException(){
        allShopAddItemToCatalogue();

        Exception itemNotFound1 = assertThrows(ItemNotFound.class, () -> {
            shop1.addInventory("Dragon Fruit", 50);
        });
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
    public void inventoryShopCartAddTestExceptions(){
        inventoryShopInventoryIn();

        ItemNotFound itemNotFound1 = assertThrows(ItemNotFound.class, () -> {
            shop1.addToCart("Dragon Fruit", 5);
        });
        NotEnoughInventory notEnoughInventory1 = assertThrows(NotEnoughInventory.class, () -> {
            shop1.addToCart("Banana", 10000);
        });
        NotPositiveInteger notPositiveInteger1 = assertThrows(NotPositiveInteger.class, () -> {
            shop1.addToCart("Banana", 0);
        });
        NotPositiveInteger notPositiveInteger2 = assertThrows(NotPositiveInteger.class, () -> {
            shop1.addToCart("Banana", -1);
        });
        String strA = "The inputted integer ";
        String strB = " is either zero or smaller. The int needs to be a positive integer";
        String str1 = strA + "0" + strB;
        String str2 = strA + "-1" + strB;
        assertEquals(str1,notPositiveInteger1.getMessage());
        assertEquals(str2,notPositiveInteger2.getMessage());
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
            shop1.addToCart("Apple", 5);
            shop1.modifyCart("Banana",5);
            shop1.removeFromCart("Apple");
        } catch (Exception e) {
            assertFalse(true);
        }

        assertEquals(10,shop1.getCart().get("banana").getQuantity());
        assertFalse(shop1.getCart().containsKey("apple"));
    }

    @Test
    public void inventoryShopCartModifyTestException() {
        inventoryShopInventoryIn();

        Exception itemNotFound1 = assertThrows(ItemNotFound.class, () -> {
            shop1.modifyCart("Dragon Fruit", 50);
        });
        Exception itemNotFound2 = assertThrows(ItemNotFound.class, () -> {
            shop1.removeFromCart("Dragon Fruit");
        });
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
    public void nonInventoryShopCartAddTestExceptions(){
        allShopAddItemToCatalogue();

        ItemNotFound itemNotFound1 = assertThrows(ItemNotFound.class, () -> {
            shop2.addToCart("Dragon Fruit", 5);
        });
        NotPositiveInteger notPositiveInteger1 = assertThrows(NotPositiveInteger.class, () -> {
            shop2.addToCart("Banana", 0);
        });
        NotPositiveInteger notPositiveInteger2 = assertThrows(NotPositiveInteger.class, () -> {
            shop2.addToCart("Banana", -1);
        });
        String strA = "The inputted integer ";
        String strB = " is either zero or smaller. The int needs to be a positive integer";
        String str1 = strA + "0" + strB;
        String str2 = strA + "-1" + strB;
        assertEquals(str1,notPositiveInteger1.getMessage());
        assertEquals(str2,notPositiveInteger2.getMessage());
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
            shop2.addToCart("Apple", 5);
            shop2.modifyCart("Banana",5);
            shop2.removeFromCart("Apple");
        } catch (Exception e) {
            assertFalse(true);
        }

        assertFalse(shop2.getCart().containsKey("apple"));
        assertEquals(10,shop2.getCart().get("banana").getQuantity());
    }

    @Test
    public void nonInventoryShopCartModifyTestException() {
        allShopAddItemToCatalogue();

        Exception itemNotFound1 = assertThrows(ItemNotFound.class, () -> {
            shop2.modifyCart("Dragon Fruit", 50);
        });
        Exception itemNotFound2 = assertThrows(ItemNotFound.class, () -> {
            shop2.removeFromCart("Dragon Fruit");
        });
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

            assertEquals(5*300+5*500,receipt.total);
            assertEquals(5,receipt.items.get("banana").getQuantity());
            assertEquals("Banana",receipt.items.get("banana").getName());
            assertEquals(5*300,receipt.items.get("banana").getValue());
            assertEquals(5,receipt.items.get("apple").getQuantity());
            assertEquals("Apple",receipt.items.get("apple").getName());
            assertEquals(5*500,receipt.items.get("apple").getValue());

        } catch (Exception e) {
            Map<String, InventoryStock> map = new HashMap<>();
            assertFalse(true);
        }

    }

    @Test
    public void sellItemFromNonInventoryShopSuccessfullyWithCartModification() {
        inventoryShopInventoryIn();

        try {
            shop2.addToCart("Banana", 5);
            shop2.addToCart("Apple", 5);
            shop2.addToCart("Banana", 5);
            shop2.removeFromCart("Apple");
            shop2.addToCart("Apple", 15);
        } catch (Exception e) {
            System.out.println(e.toString());
            assertFalse(true);
        }
        assertEquals(10*300+15*500,shop2.getCartTotal());
        Receipt receipt;
        try {
            receipt = shop2.makePurchase();

            assertEquals(10*300+15*500,receipt.total);
            assertEquals(10,receipt.items.get("banana").getQuantity());
            assertEquals("Banana",receipt.items.get("banana").getName());
            assertEquals(10*300,receipt.items.get("banana").getValue());
            assertEquals(15,receipt.items.get("apple").getQuantity());
            assertEquals("Apple",receipt.items.get("apple").getName());
            assertEquals(15*500,receipt.items.get("apple").getValue());

        } catch (Exception e) {
            Map<String, InventoryStock> map = new HashMap<>();
            assertFalse(true);
        }

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

            assertEquals(5*300+5*500,receipt.total);
            assertEquals(5,receipt.items.get("banana").getQuantity());
            assertEquals("Banana",receipt.items.get("banana").getName());
            assertEquals(5*300,receipt.items.get("banana").getValue());
            assertEquals(5,receipt.items.get("apple").getQuantity());
            assertEquals("Apple",receipt.items.get("apple").getName());
            assertEquals(5*500,receipt.items.get("apple").getValue());

        } catch (Exception e) {
            Map<String, InventoryStock> map = new HashMap<>();
            assertFalse(true);
        }

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

            assertEquals(10*300+5*500,receipt.total);
            assertEquals(10,receipt.items.get("banana").getQuantity());
            assertEquals("Banana",receipt.items.get("banana").getName());
            assertEquals(10*300,receipt.items.get("banana").getValue());
            assertEquals(5,receipt.items.get("apple").getQuantity());
            assertEquals("Apple",receipt.items.get("apple").getName());
            assertEquals(5*500,receipt.items.get("apple").getValue());

        } catch (Exception e) {
            Map<String, InventoryStock> map = new HashMap<>();
            assertFalse(true);
        }

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

        assertEquals(10*300+15*500,receipt.total);
        assertEquals(10,receipt.items.get("banana").getQuantity());
        assertEquals("Banana",receipt.items.get("banana").getName());
        assertEquals(10*300,receipt.items.get("banana").getValue());
        assertEquals(15,receipt.items.get("apple").getQuantity());
        assertEquals("Apple",receipt.items.get("apple").getName());
        assertEquals(15*500,receipt.items.get("apple").getValue());
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

    @Test
    public void receiptExceptionTest() {
        Map<String,InventoryStock> items = new HashMap<>();
        NotPositiveInteger exception1 = assertThrows(NotPositiveInteger.class, () -> {
            Receipt receipt = new Receipt(-1,items);
        });
        NotPositiveInteger exception2 = assertThrows(NotPositiveInteger.class, () -> {
            Receipt receipt = new Receipt(0,items);
        });
    }
}
