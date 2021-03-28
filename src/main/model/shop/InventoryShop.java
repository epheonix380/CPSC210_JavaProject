package model.shop;

import exceptions.ItemAlreadyExists;
import exceptions.ItemNotFound;
import exceptions.NotEnoughInventory;
import exceptions.NotPositiveInteger;
import model.Receipt;
import model.stock.NIStock;
import model.stock.InventoryStock;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//Represents InventoryShop, tracks the inventory going in and out of the shop
public class InventoryShop extends Shop {

    private Map<String, InventoryStock> inventoryMap = new HashMap<>();

    // MODIFIES: This
    // EFFECTS:  shopName is the name of the shop, the shop will be saved to a json file using this name
    public InventoryShop(String shopName) {
        super(shopName);
        super.shopType = "inventory";
        save();
    }

    // MODIFIES: This
    // EFFECTS:  shopName is the name of the shop, the shop will be saved to a json file using this name
    //           json is the JSONObject from which the shop will be constructed
    public InventoryShop(String shopName, JSONObject json) {
        super(shopName,json);
        this.inventoryMap = inventoryMapFromJson(json.getJSONObject("inventoryMap"));
        super.shopType = "inventory";
    }

    // EFFECTS:  Converts this to json, leaves out cart from json conversion
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("catalogue", super.catalogueToJson());
        json.put("transactions", super.transactionsToJson());
        json.put("inventoryMap", inventoryMapToJson());

        return json;
    }

    // EFFECTS: Converts inventoryMap to json
    private JSONObject inventoryMapToJson() {
        JSONObject json = new JSONObject();

        for (Map.Entry<String,InventoryStock> entry : inventoryMap.entrySet()) {
            InventoryStock stock = entry.getValue();
            json.put(entry.getKey(), stock.toJson());
        }

        return json;
    }

    // MODIFIES: This
    // EFFECTS: Converts json to inventoryMap then sets This.inventoryMap as that inventoryMap
    private Map<String, InventoryStock> inventoryMapFromJson(JSONObject json) {
        Map<String, InventoryStock> inventoryMap = new HashMap<>();
        Iterator<String> jsonKeys = json.keys();

        for (Iterator<String> it = jsonKeys; it.hasNext(); ) {
            String key = it.next();
            JSONObject jsonObject = json.getJSONObject(key);
            InventoryStock stock = new InventoryStock(jsonObject);
            inventoryMap.put(key, stock);
        }

        return inventoryMap;
    }

    public Map<String, InventoryStock> getInventoryMap() {
        return this.inventoryMap;
    }

    // EFFECTS: Checks if the inventoryMap contains a key value pair with the given key name
    public Boolean inventoryContains(String name) {
        return this.inventoryMap.containsKey(name.toLowerCase());
    }

    // MODIFIES: This
    // EFFECTS: IF q is a positive integer:
    //              IF catalogue contains name:
    //                  IF given item inventory has >= q items:
    //                      adds q items to cart if item name already exists, adds q to current amount
    //                  else:
    //                      throws NotEnoughInventory exception
    //              else:
    //                  throws ItemNotFound exception
    //          else:
    //              throws NotPositiveInteger exception
    @Override
    public void addToCart(String name, int q) throws NotEnoughInventory, NotPositiveInteger, ItemNotFound {
        if (q <= 0) {
            throw new NotPositiveInteger(q);
        }
        if (!catalogue.containsKey(name.toLowerCase())) {
            throw new ItemNotFound();
        }
        NIStock stock = catalogue.get(name.toLowerCase());
        InventoryStock inventoryStock = inventoryMap.get(name.toLowerCase());
        InventoryStock cartStock = new InventoryStock(name, q, stock.getPrice(), stock.getUnitCost());

        if (this.cart.containsKey(name.toLowerCase())) {
            addToCartExt(name, cartStock, inventoryStock, stock);
        } else {
            if (inventoryStock.isSellable(cartStock.getQuantity())) {
                this.cart.put(name.toLowerCase(), cartStock);
            } else {
                NotEnoughInventory error = new NotEnoughInventory(cartStock,inventoryStock);
                throw error;
            }
        }
    }

    // MODIFIES: This
    // EFFECTS: If catalogue contains item name:
    //              If cartStock + modifier is less than or equal to existing inventory of name:
    //                  Modifies current cart item to have cartStock + modifier quantity
    //              else throws NotEnoughInventoryError
    //          else throws ItemNotFoundError
    @Override
    public void modifyCart(String name, int modifier) throws NotEnoughInventory, ItemNotFound {
        if (!catalogue.containsKey(name.toLowerCase())) {
            throw new ItemNotFound();
        }
        InventoryStock inventoryStock = inventoryMap.get(name.toLowerCase());
        InventoryStock cartStock = this.cart.get(name.toLowerCase());
        int quantity = cartStock.getQuantity() + modifier;
        InventoryStock modifiedCartStock = new InventoryStock(
                cartStock.getName(), quantity, cartStock.getPrice(), cartStock.getUnitCost());

        if (inventoryStock.isSellable(modifiedCartStock.getQuantity())) {
            cartStock.modifyInventory(modifier);

        } else {
            NotEnoughInventory error = new NotEnoughInventory(modifiedCartStock,inventoryStock);
            throw error;

        }
    }

    // MODIFIES: This
    // EFFECTS: IF is isSellable with ct quantity:
    //              put ct in cart
    //          else:
    //              Throw NotEnoughInventory exception
    private void addToCartExt(String n, InventoryStock ct, InventoryStock is, NIStock s) throws NotEnoughInventory {
        int existingCartQuantity = this.cart.get(n.toLowerCase()).getQuantity();
        ct.modifyInventory(existingCartQuantity);

        if (is.isSellable(ct.getQuantity())) {
            this.cart.put(s.getName().toLowerCase(), ct);

        } else {
            NotEnoughInventory error = new NotEnoughInventory(ct,is);
            throw error;

        }
    }

    // MODIFIES: This
    // EFFECTS: IF inventoryMap contains name:
    //              Adds to the inventory of given stock
    //          else:
    //              Throw ItemNotFound exception
    public void addInventory(String name, int quantity) throws ItemNotFound {
        if (!inventoryMap.containsKey(name.toLowerCase())) {
            throw new ItemNotFound();
        }
        this.inventoryMap.get(name.toLowerCase()).modifyInventory(quantity);
        super.save();
    }

    // MODIFIES: This
    // EFFECTS: IF catalogue contains key:
    //              Throw ItemAlreadyExists exception
    //          else IF price <= 0:
    //              Throw NotPositiveInteger exception
    //          else IF unitCost <= 0:
    //              Throw NotPositiveInteger exception
    //          else:
    //              add item to catalogue
    @Override
    public void addToCatalogue(String name, int price, int unitCost) throws NotPositiveInteger, ItemAlreadyExists {
        if (catalogue.containsKey(name.toLowerCase())) {
            throw new ItemAlreadyExists();
        } else if (price <= 0) {
            throw new NotPositiveInteger(price);
        } else if (unitCost <= 0) {
            throw new NotPositiveInteger(unitCost);
        }
        NIStock stock = new NIStock(name, price, unitCost);
        this.catalogue.put(name.toLowerCase(),stock);
        InventoryStock inventoryStock = new InventoryStock(name, 0, price, unitCost);
        this.inventoryMap.put(name.toLowerCase(), inventoryStock);
        super.save();
    }

    // MODIFIES: This
    // EFFECTS: IF newPrice < 0:
    //              Throw NotPositiveInteger Exception
    //          else IF newUnitCost < 0:
    //              Throw NotPostiveInteger Exception
    //          else IF catalogue does not contain name:
    //              Throw ItemNotFound Exception
    //          else:
    //              edit catalogue and set name with prices: newPrice and with unitCost: newUnitCost
    @Override
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
        InventoryStock inStock = inventoryMap.get(name.toLowerCase());
        inStock.setPrice(price);
        inStock.setUnitCost(unitCost);
        inventoryMap.put(name.toLowerCase(),inStock);
        save();
    }

    // MODIFIES: This
    // EFFECTS: IF catalogue contains name:
    //              Removes given stock from catalogue and inventory
    //          else:
    //              Throw ItemNotFound exception
    @Override
    public void removeItemFromCatalogue(String name) throws ItemNotFound {
        if (!catalogue.containsKey(name.toLowerCase())) {
            throw new ItemNotFound();
        }
        this.catalogue.remove(name.toLowerCase());
        this.inventoryMap.remove(name.toLowerCase());
        super.save();
    }

    // MODIFIES: This
    // EFFECT:  IF cart total <= 0:
    //              Throw NotPositiveInteger exception
    //          else IF inventoryMap has enough items for all the respective items in cart:
    //              Takes all the items from cart and purchases them
    //          else:
    //              Throw NotEnoughInventory Exception
    @Override
    public Receipt makePurchase() throws NotEnoughInventory, NotPositiveInteger {
        int total = 0;
        Map<String, InventoryStock> soldItems = new HashMap<>();

        for (Map.Entry<String,InventoryStock> entry : this.cart.entrySet()) {
            InventoryStock stock = entry.getValue();
            String name = stock.getName();
            int quantity = stock.getQuantity();
            InventoryStock requestedStock = inventoryMap.get(name.toLowerCase());

            boolean isSuccess = requestedStock.sell(quantity);
            if (isSuccess) {
                total += stock.getValue();
                soldItems.put(entry.getKey().toLowerCase(),entry.getValue());
            } else {
                NotEnoughInventory error = new NotEnoughInventory(stock, requestedStock);
                throw error;
            }

        }
        Receipt receipt = new Receipt(total,soldItems);
        this.cart = new HashMap<>();
        transactions.add(receipt);
        super.save();
        return receipt;
    }
}
