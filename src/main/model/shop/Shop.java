package model.shop;

import errors.NotEnoughInventoryError;
import model.stock.InventoryStock;
import model.Receipt;
import model.stock.NIStock;

import java.util.*;

// Represents a shop, with a Catalogue of items sold, a cart of the current transaction, and records of previous sales
public abstract class Shop {

    protected Map<String, NIStock> catalogue;
    protected List<Receipt> transactions;
    protected Map<String, InventoryStock> cart;

    // MODIFIES: This
    // EFFECTS: catalogue is a map of all the available items in the store;
    //          transactions is a list of all the transactions that have occurred;
    //          cart is a not yet committed transaction that is taking place
    public Shop() {
        this.catalogue = new TreeMap<>();
        this.transactions = new ArrayList<>();
        this.cart = new HashMap<>();
    }

    // REQUIRES: name is unique, price > 0, unitCost > 0
    // MODIFIES: This
    // EFFECTS: Adds new item to the catalogue
    public void addToCatalogue(String name, int price, int unitCost) {
        NIStock stock = new NIStock(name, price, unitCost);
        catalogue.put(name.toLowerCase(), stock);

    }

    public Map<String, NIStock> getCatalogue() {
        return this.catalogue;
    }

    // REQUIRES: newPrice is not negative and newUnitCost is not negative
    // MODIFIES: This
    // EFFECTS : Modifies the current NIstock in the catalogue, if newPrice or newUnitCost is 0,
    //           the original price or unitcost is used
    public void editCatalogue(String name, int newPrice, int newUnitCost) {
        NIStock stock = catalogue.get(name.toLowerCase());
        int price = newPrice == 0 ? stock.getPrice() : newPrice;
        int unitCost = newUnitCost == 0 ? stock.getUnitCost() : newUnitCost;
        stock.setPrice(price);
        stock.setUnitCost(unitCost);
        catalogue.put(name.toLowerCase(),stock);
    }

    // MODIFIES: This
    // EFFECTS: Removes given stock from catalogue and inventory
    public void removeItemFromCatalogue(String stockName) {
        this.catalogue.remove(stockName.toLowerCase());
    }

    // EFFECTS: Verifies if the catalogue contains an item
    public boolean catalogueContains(String name) {
        return this.catalogue.containsKey(name.toLowerCase());
    }


    // REQUIRES: NonInventoryStock st is a catalogue item, q > 0
    // MODIFIES: This
    // EFFECTS: Adds the item to cart if there is an existing same type of item it adds the two together
    public void addToCart(String name, int q) {
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
    // EFFECTS: Removes given key and its associated value from cart
    public void removeFromCart(String name) {
        this.cart.remove(name.toLowerCase());
    }

    // MODIFIES: This
    // EFFECTS: Modifies the amount of a specified item in the cart
    public void modifyCart(String name, int modifier) {
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
    // EFFECT: Takes all the items from cart and attempts to purchase them
    public abstract Receipt makePurchase();
}