package model.shop;

import errors.NotEnoughInventoryError;
import model.Receipt;
import model.stock.NIStock;
import model.stock.InventoryStock;

import java.util.HashMap;
import java.util.Map;

//Represents InventoryShop, tracks the inventory going in and out of the shop
public class InventoryShop extends Shop {

    private Map<String, InventoryStock> inventoryMap = new HashMap<>();

    public Map<String, InventoryStock> getInventoryMap() {
        return this.inventoryMap;
    }

    // EFFECTS: Checks if the inventoryMap contains a key value pair with the given key name
    public Boolean inventoryContains(String name) {
        return this.inventoryMap.containsKey(name);
    }

    // REQUIRES: NonInventoryStock st is a catalogue item, q > 0
    // MODIFIES: This
    // EFFECTS: Adds the item to cart ONLY IF the inventory has enough, also adds to existing item if there
    //          is an existing item
    @Override
    public void addToCart(NIStock st, int q) {
        InventoryStock inventoryStock = inventoryMap.get(st.getName());
        InventoryStock cartStock = new InventoryStock(st.getName(), q, st.getPrice(), st.getUnitCost());

        if (this.cart.containsKey(st.getName())) {
            int existingCartQuantity = this.cart.get(st.getName()).getQuantity();
            cartStock.modifyInventory(existingCartQuantity);

            if (inventoryStock.isSellable(cartStock.getQuantity())) {
                this.cart.put(st.getName(), cartStock);

            } else {
                NotEnoughInventoryError error = new NotEnoughInventoryError(cartStock,inventoryStock);
                throw error;

            }

        } else {
            if (inventoryStock.isSellable(cartStock.getQuantity())) {
                this.cart.put(st.getName(), cartStock);
            } else {
                NotEnoughInventoryError error = new NotEnoughInventoryError(cartStock,inventoryStock);
                throw error;
            }
        }



    }

    // REQUIRES: NonInventoryStock stock is part of catalogue
    // MODIFIES: This
    // EFFECTS: Adds to the inventory of given stock
    @Override
    public void addInventory(NIStock stock, int quantity) {
        this.inventoryMap.get(stock.getName()).modifyInventory(quantity);
    }

    // REQUIRES: name is unique, price > 0, unitCost > 0
    // MODIFIES: This
    // EFFECTS: Adds new item to the catalogue
    @Override
    public void addToCatalogue(String name, int price, int unitCost) {
        NIStock stock = new NIStock(name, price, unitCost);
        this.catalogue.put(name,stock);
        InventoryStock inventoryStock = new InventoryStock(name, 0, price, unitCost);
        this.inventoryMap.put(name, inventoryStock);
    }

    // MODIFIES: This
    // EFFECTS: Removes given stock from catalogue and inventory
    @Override
    public void removeItemFromCatalogue(NIStock stock) {
        this.catalogue.remove(stock.getName());
        this.inventoryMap.remove(stock.getName());
    }

    // MODIFIES: This
    // EFFECT: Takes all the items from cart and attempts to purchase them
    @Override
    public Receipt makePurchase() {
        int total = 0;
        Map<String, InventoryStock> soldItems = new HashMap<>();

        for (Map.Entry<String,InventoryStock> entry : this.cart.entrySet()) {
            InventoryStock stock = entry.getValue();
            String name = stock.getName();
            int quantity = stock.getQuantity();
            InventoryStock requestedStock = inventoryMap.get(name);

            boolean isSuccess = requestedStock.sell(quantity);
            if (isSuccess) {
                total += stock.getValue();
                soldItems.put(entry.getKey(),entry.getValue());
            } else {
                NotEnoughInventoryError error = new NotEnoughInventoryError(stock, requestedStock);
                throw error;
            }

        }
        Receipt receipt = new Receipt(total,soldItems);
        this.cart = new HashMap<>();
        transactions.add(receipt);
        return receipt;
    }
}
