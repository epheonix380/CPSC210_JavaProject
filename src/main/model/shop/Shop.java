package model.shop;

import exceptions.ItemAlreadyExists;
import exceptions.ItemNotFound;
import exceptions.NotEnoughInventory;
import exceptions.NotPositiveInteger;
import model.stock.InventoryStock;
import model.Receipt;
import model.stock.NIStock;

import java.io.IOException;
import java.util.*;
import org.json.*;
import persistence.JsonConvertable;
import persistence.ShopJson;

// Represents a shop, with a Catalogue of items sold, a cart of the current transaction, and records of previous sales
public abstract class Shop implements JsonConvertable {

    protected String shopType;
    final String shopName;
    private ShopJson shopJson;
    protected Map<String, NIStock> catalogue;
    protected List<Receipt> transactions;
    protected Map<String, InventoryStock> cart;

    // MODIFIES: This
    // EFFECTS: catalogue is a map of all the available items in the store;
    //          transactions is a list of all the transactions that have occurred;
    //          cart is a not yet committed transaction that is taking place
    //          shopName is the name of the shop, the shop will be saved to a json file using this name
    public Shop(String shopName) {
        this.catalogue = new TreeMap<>();
        this.transactions = new ArrayList<>();
        this.cart = new HashMap<>();
        this.shopName = shopName;
        this.shopJson = new ShopJson(shopName);
    }

    // MODIFIES: This
    // EFFECTS: Takes a JSONObject and unpacks it into Shop
    //          shopName is the name of the shop, the shop will be saved to a json file using this name
    public Shop(String shopName,JSONObject json) {
        this.catalogue = catalogueFromJson(json.getJSONObject("catalogue"));
        this.transactions = transactionsFromJson(json.getJSONArray("transactions"));
        this.cart = new HashMap<>();
        this.shopName = shopName;
        this.shopJson = new ShopJson(shopName);
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopType() {
        return shopType;
    }

    // EFFECTS: Saves the current state of shop class
    //          If there is an IOException during this process, will print Autosave failed
    public void save() {
        try {
            shopJson.saveShop(this);
        } catch (IOException e) {
            System.out.println("Autosave failed");
        }
    }

    // EFFECTS: Unpacks a JSONArray and returns it as a List<Receipt>
    protected List<Receipt> transactionsFromJson(JSONArray json) {
        List<Receipt> receipts = new ArrayList<>();
        Iterator<Object> jsonReceipts = json.iterator();
        int i = 0;

        for (Iterator<Object> it = jsonReceipts; it.hasNext(); ) {
            JSONObject receiptJson = json.getJSONObject(i);
            Receipt receipt = new Receipt(receiptJson);
            receipts.add(receipt);
            i++;
            it.next();
        }

        return receipts;
    }

    // EFFECTS: Unpacks a JSONObject and returns it as a Map<String, NIStock>
    protected Map<String, NIStock> catalogueFromJson(JSONObject json) {
        Map<String, NIStock> catalogue = new TreeMap<>();
        Iterator<String> jsonKeys = json.keys();

        for (Iterator<String> it = jsonKeys; it.hasNext(); ) {
            String key = it.next();
            JSONObject jsonObject = json.getJSONObject(key);
            NIStock stock = new NIStock(jsonObject);
            catalogue.put(key, stock);
        }

        return catalogue;
    }

    // EFFECTS: Converts this to JSONObject, saves catalogue and transactions but not cart
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("catalogue", catalogueToJson());
        json.put("transactions", transactionsToJson());

        return json;
    }

    // EFFECTS: Converts transactions to a JSONArray
    protected JSONArray transactionsToJson() {
        JSONArray json = new JSONArray();

        for (Receipt receipt : transactions) {
            json.put(receipt.toJson());
        }

        return json;
    }

    // EFFECTS: Converts catalogue to JSONObject
    protected JSONObject catalogueToJson() {
        JSONObject json = new JSONObject();

        for (Map.Entry<String,NIStock> entry : catalogue.entrySet()) {
            NIStock stock = entry.getValue();
            json.put(entry.getKey(), stock.toJson());
        }

        return json;
    }

    // MODIFIES: This
    // EFFECTS: IF catalogue contains name:
    //              Throw ItemAlreadyExists exception
    //          else IF price <= 0:
    //              Throw NotPositiveInteger exception
    //          else IF unitCost <= 0:
    //              Throw NotPositiveInteger
    //          else:
    //              Adds new item to the catalogue
    public void addToCatalogue(String name, int price, int unitCost) throws NotPositiveInteger, ItemAlreadyExists {
        if (catalogue.containsKey(name.toLowerCase())) {
            throw new ItemAlreadyExists();
        } else if (price <= 0) {
            throw new NotPositiveInteger(price);
        } else if (unitCost <= 0) {
            throw new NotPositiveInteger(unitCost);
        }
        NIStock stock = new NIStock(name, price, unitCost);
        catalogue.put(name.toLowerCase(), stock);
        save();
    }

    public Map<String, NIStock> getCatalogue() {
        return this.catalogue;
    }

    // MODIFIES: This
    // EFFECTS : IF newPrice < 0:
    //              Throw NotPositiveInteger exception
    //           else IF newUnitCost < 0:
    //              Throw NotPositiveInteger exception
    //           else IF catalogue does not contain name:
    //              Throw ItemNotFound exception
    //           else:
    //              Modifies the current NIstock in the catalogue, if newPrice or newUnitCost is 0,
    //              the original price or unitcost is used
    public void editCatalogue(String name, int newPrice, int newUnitCost) throws NotPositiveInteger, ItemNotFound {
        if (newPrice < 0) {
            throw new NotPositiveInteger(newPrice);
        }
        if (newUnitCost < 0) {
            throw new NotPositiveInteger(newUnitCost);
        }
        if (!catalogue.containsKey(name.toLowerCase())) {
            throw new ItemNotFound();
        }
        NIStock stock = catalogue.get(name.toLowerCase());
        int price = newPrice == 0 ? stock.getPrice() : newPrice;
        int unitCost = newUnitCost == 0 ? stock.getUnitCost() : newUnitCost;
        stock.setPrice(price);
        stock.setUnitCost(unitCost);
        catalogue.put(name.toLowerCase(),stock);
        save();
    }

    // MODIFIES: This
    // EFFECTS: IF catalogue contains name:
    //              Removes given stock from catalogue and inventory
    //          else:
    //              Throw ItemNotFound exception
    public void removeItemFromCatalogue(String stockName) throws ItemNotFound {
        if (!catalogue.containsKey(stockName.toLowerCase())) {
            throw new ItemNotFound();
        }
        this.catalogue.remove(stockName.toLowerCase());
        save();
    }

    // EFFECTS: Verifies if the catalogue contains an item
    public boolean catalogueContains(String name) {
        return this.catalogue.containsKey(name.toLowerCase());
    }


    // MODIFIES: This
    // EFFECTS: IF q is a positive integer:
    //              IF catalogue contains name:
    //                  adds q items to cart if item name already exists, adds q to current amount
    //              else:
    //                  throws ItemNotFound exception
    //          else:
    //              throws NotPositiveInteger exception
    public void addToCart(String name, int q) throws NotEnoughInventory, NotPositiveInteger, ItemNotFound {
        if (q <= 0) {
            throw new NotPositiveInteger(q);
        }
        if (!catalogue.containsKey(name.toLowerCase())) {
            throw new ItemNotFound();
        }
        NIStock stock = catalogue.get(name.toLowerCase());
        InventoryStock inventoryStock = new InventoryStock(name, q, stock.getPrice(), stock.getUnitCost());

        if (this.cart.containsKey(name.toLowerCase())) {
            this.cart.get(name.toLowerCase()).modifyInventory(q);
        } else {
            this.cart.put(name.toLowerCase(), inventoryStock);
        }
    }

    public Map<String, InventoryStock> getCart() {
        return this.cart;
    }

    // EFFECTS: Gets the current sum of all of the values of all the items in the cart
    public int getCartTotal() {
        int total = 0;
        for (Map.Entry<String, InventoryStock> entry : this.cart.entrySet()) {
            InventoryStock stock = entry.getValue();
            total += stock.getValue();
        }
        return total;
    }

    // MODIFIES: This
    // EFFECTS: IF cart contains name:
    //              Removes given key and its associated value from cart
    //          else:
    //              Throw ItemNotFound exception
    public void removeFromCart(String name) throws ItemNotFound {
        if (!cart.containsKey(name.toLowerCase())) {
            throw new ItemNotFound();
        }
        this.cart.remove(name.toLowerCase());
    }

    // MODIFIES: This
    // EFFECTS: IF cart contains name:
    //              Modifies the amount of a specified item in the cart
    //          else:
    //              Throw ItemNotFound exception
    public void modifyCart(String name, int modifier) throws ItemNotFound {
        if (!cart.containsKey(name.toLowerCase())) {
            throw new ItemNotFound();
        }
        InventoryStock cartStock = this.cart.get(name.toLowerCase());
        cartStock.modifyInventory(modifier);
    }

    // MODIFIES: This
    // EFFECTS: Creates a brand new cart
    public void destroyCart() {
        this.cart = new HashMap<>();
    }

    public List<Receipt> getRecords() {
        return this.transactions;
    }

    // MODIFIES: This
    // EFFECT: Takes all the items from cart and attempts to purchase them, specified in implementation
    public abstract Receipt makePurchase() throws NotEnoughInventory, NotPositiveInteger;
}