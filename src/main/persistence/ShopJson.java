package persistence;

import exceptions.BadlyFormattedShopFile;
import model.shop.InventoryShop;
import model.shop.NonInventoryShop;
import model.shop.Shop;
import org.json.JSONObject;

import java.io.IOException;

// Represents reading Shop from json files
public class ShopJson {

    private String source;
    static final String path = "./data/shops/";
    private static final int TAB = 4;

    // MODIFIES: This
    // EFFECTS: source is the name of the Shop file to be read and written
    public ShopJson(String source) {
        this.source = path + source + ".json";
    }

    // EFFECTS: gets and returns Shop, if file is badly formatted throws BadlyFormattedShopFile
    //          if an error occurs during reading throws IOException
    public Shop getShop() throws BadlyFormattedShopFile, IOException {
        Reader reader = new Reader(this.source);
        String jsonString = reader.getJson();
        Shop shop = read(jsonString);
        System.out.println(shop);
        return shop;
    }

    // EFFECTS: Converts shop to jsonString and saves it using writer
    //          if an error occurs during writing process, throws IOException
    public void saveShop(Shop shop) throws IOException {
        Writer writer = new Writer(this.source);
        Index index = new Index();
        index.addToIndex(shop.getShopName());
        JSONObject shopJson = shop.toJson();
        JSONObject json = new JSONObject();
        json.put("shopTypeString", shop.getShopType());
        json.put("shopName", shop.getShopName());
        json.put("shop", shopJson);
        writer.write(json.toString(TAB));
    }

    // EFFECTS: Attempts to convert string jsonData to JSONObject, if fails throws BadlyFormattedShopFile
    private Shop read(String jsonData) throws BadlyFormattedShopFile {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonData);
        } catch (Exception e) {
            throw new BadlyFormattedShopFile(jsonData);
        }
        return createShop(jsonObject);
    }

    // EFFECTS: Converts JSONObject jsonObject to Shop
    //          if fails throws BadlyFormattedShopFile
    private Shop createShop(JSONObject jsonObject) throws BadlyFormattedShopFile {
        String shopTypeString = jsonObject.getString("shopTypeString");
        Shop shop;
        String shopName = jsonObject.getString("shopName");
        if (shopTypeString.equals("inventory")) {
            shop = new InventoryShop(shopName, jsonObject.getJSONObject("shop"));
        } else if (shopTypeString.equals("nonInventory")) {
            shop = new NonInventoryShop(shopName, jsonObject.getJSONObject("shop"));
        } else {
            throw new BadlyFormattedShopFile(shopTypeString);
        }

        return shop;
    }
}
