package persistence;

import exceptions.BadlyFormattedShopFile;
import model.shop.InventoryShop;
import model.shop.NonInventoryShop;
import model.shop.Shop;
import org.json.JSONObject;

import java.io.IOException;

public class ShopJson {

    private String source;
    static final String path = "./data/shops/";
    private static final int TAB = 4;

    // EFFECTS: constructs reader to read from source file
    public ShopJson(String source) {
        this.source = path + source + ".json";
    }

    public Shop getShop() throws BadlyFormattedShopFile, IOException {
        Reader reader = new Reader(this.source);
        String jsonString = reader.getJson();
        Shop shop = read(jsonString);
        System.out.println(shop);
        return shop;
    }

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

    private Shop read(String jsonData) throws BadlyFormattedShopFile {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonData);
        } catch (Exception e) {
            throw new BadlyFormattedShopFile(jsonData);
        }
        return createShop(jsonObject);
    }

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
