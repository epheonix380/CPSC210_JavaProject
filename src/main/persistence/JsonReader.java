package persistence;

import exceptions.BadlyFormattedShopFile;
import model.shop.InventoryShop;
import model.shop.NonInventoryShop;
import model.shop.Shop;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Shop read() throws IOException, BadlyFormattedShopFile {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return createShop(jsonObject);
    }

    public boolean isInventory() throws IOException, BadlyFormattedShopFile {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return createBool(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private Shop createShop(JSONObject jsonObject) throws BadlyFormattedShopFile {
        String shopTypeString = jsonObject.getString("shopTypeString");
        Shop shop;
        if (shopTypeString.equals("inventory")) {
            shop = new InventoryShop(jsonObject.getJSONObject("shop"));
        } else if (shopTypeString.equals("nonInventory")) {
            shop = new NonInventoryShop();
        } else {
            throw new BadlyFormattedShopFile(shopTypeString);
        }

        return shop;
    }

    private boolean createBool(JSONObject jsonObject) throws BadlyFormattedShopFile {
        String shopTypeString = jsonObject.getString("shopTypeString");
        Boolean isInventory;
        if (shopTypeString.equals("inventory")) {
            isInventory = true;
        } else if (shopTypeString.equals("nonInventory")) {
            isInventory = false;
        } else {
            throw new BadlyFormattedShopFile("createBool");
        }

        return isInventory;
    }
}
