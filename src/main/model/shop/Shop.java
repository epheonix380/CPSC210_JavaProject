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
    protected Map<String,InventoryStock> cart;

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
        catalogue.put(name,stock);

    }

    public List<Receipt> getRecords() {
        return this.transactions;
    }

    public Map<String, NIStock> getCatalogue() {
        return this.catalogue;
    }

    // MODIFIES: This
    // EFFECTS: Removes given stock from catalogue and inventory
    public void removeItemFromCatalogue(NIStock stock) {
        this.catalogue.remove(stock.getName());
    }

    // REQUIRES: NonInventoryStock st is a catalogue item, q > 0
    // MODIFIES: This
    // EFFECTS: Adds the item to cart if there is an existing same type of item it adds the two together
    public void addToCart(NIStock st, int q) {
        InventoryStock inventoryStock = new InventoryStock(st.getName(), q, st.getPrice(), st.getUnitCost());
        if (this.cart.containsKey(st.getName())) {
            this.cart.get(st.getName()).modifyInventory(q);
        } else {
            this.cart.put(st.getName(), inventoryStock);
        }
    }

    public Map<String,InventoryStock> getCart() {
        return this.cart;
    }

    public abstract Map<String, InventoryStock> getInventoryMap();

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
        this.cart.remove(name);
    }

    // MODIFIES: This
    // EFFECTS: Modifies the amount of a specified item in the cart
    public void modifyCart(InventoryStock stock, int modifier) {
        InventoryStock cartStock = this.cart.get(stock.getName());
        cartStock.modifyInventory(modifier);
    }

    // MODIFIES: This
    // EFFECTS: Creates a brand new cart
    public void destroyCart() {
        this.cart = new HashMap<>();
    }

    // EFFECTS: Verifies if the catalogue contains an item
    public boolean catalogueContains(String name) {
        return this.catalogue.containsKey(name);
    }

    // MODIFIES: This
    // EFFECT: Takes all the items from cart and attempts to purchase them
    public abstract Receipt makePurchase();

    // REQUIRES: NonInventoryStock stock is part of catalogue
    // MODIFIES: This
    // EFFECTS: Adds to the inventory of given stock
    public abstract void addInventory(NIStock stock, int quantity);

}
