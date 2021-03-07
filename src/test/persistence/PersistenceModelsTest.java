package persistence;

import exceptions.BadlyFormattedShopFile;
import model.Receipt;
import model.shop.InventoryShop;
import model.shop.NonInventoryShop;
import model.shop.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.Index;
import persistence.Reader;
import persistence.ShopJson;

import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PersistenceModelsTest {

    private ShopJson invShopJson;
    private ShopJson nonInvShopJson;

    @BeforeEach
    public void init() {
        invShopJson = new ShopJson("PersistenceTest");
        nonInvShopJson = new ShopJson("PersistenceTestNI");
    }

    @Test
    public void testPutInventoryShopInStorage() {
        InventoryShop shop = new InventoryShop("PersistenceTest");
        try {
            shop.addToCatalogue("Bread",200,100);
            shop.addInventory("Bread", 200);
            shop.addToCart("Bread",50);
            shop.makePurchase();
        } catch (Exception e) {
            System.out.println(e.toString());
            assertFalse(true);
        }
        Reader reader = new Reader("./data/shops/PersistenceTest.json");
        String jsonString;
        Index index = new Index();
        try {
            jsonString = reader.getJson();
        } catch (Exception e) {
            jsonString = "";
            assertFalse(true);
        }
        assertTrue(jsonString.length()>0);
        try {
            assertTrue(index.contains("PersistenceTest"));
        } catch (Exception e) {
            assertFalse(true);
        }

    }

    @Test
    public void testRetrieveInventoryShopFromStorage() {
        InventoryShop retrievedShop;
        try {
            retrievedShop = (InventoryShop) invShopJson.getShop();
        } catch (Exception e) {
            assertFalse(true);
            retrievedShop = new InventoryShop("Failed");
        }

        assertEquals("Bread",retrievedShop.getCatalogue().get("bread").getName());
        assertEquals(200,retrievedShop.getCatalogue().get("bread").getPrice());
        assertEquals(100,retrievedShop.getCatalogue().get("bread").getUnitCost());
        assertEquals(150,retrievedShop.getInventoryMap().get("bread").getQuantity());

        List<Receipt> receiptList = retrievedShop.getRecords();
        Receipt receipt = receiptList.get(0);

        assertEquals(200*50,receipt.getTotal());
        assertEquals(50,receipt.getItems().get("bread").getQuantity());
        assertEquals("Bread",receipt.getItems().get("bread").getName());
        assertEquals(200*50,receipt.getItems().get("bread").getValue());
    }

    @Test
    public void testPutNonInventoryShopInStorage() {
        NonInventoryShop shop = new NonInventoryShop("PersistenceTestNI");
        try {
            shop.addToCatalogue("Bread",200,100);
            shop.addToCart("Bread",50);
            shop.makePurchase();
        } catch (Exception e) {
            System.out.println(e.toString());
            assertFalse(true);
        }
        Reader reader = new Reader("./data/shops/PersistenceTestNI.json");
        String jsonString;
        Index index = new Index();
        try {
            jsonString = reader.getJson();
        } catch (Exception e) {
            jsonString = "";
            assertFalse(true);
        }
        assertTrue(jsonString.length()>0);
        try {
            assertTrue(index.contains("PersistenceTestNI"));
        } catch (Exception e) {
            assertFalse(true);
        }

    }

    @Test
    public void testRetrieveNonInventoryShopFromStorage() {
        NonInventoryShop retrievedShop;
        try {
            retrievedShop = (NonInventoryShop) nonInvShopJson.getShop();
        } catch (Exception e) {
            assertFalse(true);
            retrievedShop = new NonInventoryShop("Failed");
        }

        assertEquals("Bread",retrievedShop.getCatalogue().get("bread").getName());
        assertEquals(200,retrievedShop.getCatalogue().get("bread").getPrice());
        assertEquals(100,retrievedShop.getCatalogue().get("bread").getUnitCost());

        List<Receipt> receiptList = retrievedShop.getRecords();
        Receipt receipt = receiptList.get(0);

        assertEquals(200*50,receipt.getTotal());
        assertEquals(50,receipt.getItems().get("bread").getQuantity());
        assertEquals("Bread",receipt.getItems().get("bread").getName());
        assertEquals(200*50,receipt.getItems().get("bread").getValue());
    }

    @Test
    public void testBadlyFormattedFile() {
        Writer writer = new Writer("./data/shops/BadFormatting.json");
        try {
            writer.write("Hello World");
        } catch (Exception e) {
            assertFalse(true);
        }
        ShopJson shopJson = new ShopJson("BadFormatting");
        Exception notWellFormatted = assertThrows(BadlyFormattedShopFile.class, () -> {
            Shop shop = shopJson.getShop();
        });
    }

    @Test
    public void indexAdding() {
        Index index = new Index();
        List<String> files;
        int length;
        try {
            files = index.getIndex();
            assertFalse(files.contains("TestingIndexAdding"));
            length = files.size();
            index.addToIndex("TestingIndexAdding");
            files = index.getIndex();
        } catch (Exception e) {
            assertFalse(true);
            length = 0;
            files = new ArrayList<>();
        }
        assertTrue(files.contains("TestingIndexAdding"));
        assertEquals(length + 1, files.size());
    }

    @Test
    public void indexDelete() {
        Index index = new Index();
        List<String> files;
        int length;
        try {
            files = index.getIndex();
            assertTrue(files.contains("TestingIndexAdding"));
            length = files.size();
            index.delete("TestingIndexAdding");
            files = index.getIndex();
        } catch (Exception e) {
            assertFalse(true);
            length = 0;
            files = new ArrayList<>();
        }
        assertFalse(files.contains("TestingIndexAdding"));
        assertEquals(length - 1, files.size());
    }

    @Test
    public void indexDeleteException() {
        Index index = new Index();

        Exception fileNotFoundException = assertThrows(FileNotFoundException.class, () -> {
            index.delete("TestingIndexAdding");
        });
    }




}
