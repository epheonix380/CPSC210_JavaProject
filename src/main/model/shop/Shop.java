package model.shop;

import model.stock.InventoryStock;
import model.Receipt;
import model.Record;
import model.stock.NIStock;

import java.util.*;

// Represents a shop, with a Catalogue of items sold, a cart of the current transaction, and records of previous sales
public abstract class Shop {

    protected Map<String, NIStock> catalogue;
    protected List<Record> records;
    protected Map<String,InventoryStock> cart;

    public Shop() {
        this.catalogue = new TreeMap<>();
        this.records = new ArrayList<>();
        this.cart = new HashMap<>();
    }

    public void addToCatalogue(String name, int price, int unitCost) {
        NIStock stock = new NIStock(name, price, unitCost);
        catalogue.put(name,stock);

    }

    public List<Record> getRecords() {
        return this.records;
    }

    public Map<String, NIStock> getCatalogue() {
        return this.catalogue;
    }

    public void removeItemFromCatalogue(NIStock stock) {
        this.catalogue.remove(stock.getName());
    }

    public void addToCart(NIStock st, int q) {
        InventoryStock inventoryStock = new InventoryStock(st.getName(), q, st.getPrice(), st.getUnitCost());
        this.cart.put(st.getName(), inventoryStock);

    }

    public Map<String,InventoryStock> getCart() {
        return this.cart;
    }

    public void removeFromCart(String name) {
        this.cart.remove(name);
    }

    public void modifyCart(InventoryStock stock, int modifier) {
        InventoryStock cartStock = this.cart.get(stock.getName());
        cartStock.modifyInventory(modifier);
    }

    public void destroyCart() {
        this.cart = new HashMap<>();
    }

    public Boolean catalogueContains(String name) {
        return this.catalogue.containsKey(name);
    }

    public abstract Receipt makePurchase();

    public abstract void addInventory(NIStock stock, int quantity);

}
