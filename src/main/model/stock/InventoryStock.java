package model.stock;


import org.json.JSONObject;

import java.util.Date;

// Represents an InventoryStock, which track the inventory said stock in the store
public class InventoryStock extends Stock {

    private int quantity;

    // REQUIRES: quantity >= 0, price > 0, unitCost > 0
    // MODIFIES: This
    // EFFECTS: name is the Name of the item
    //          quantity is how much of the item is in inventory
    //          price is how much the item will be sold for to the customer
    //          unitCost is how much the item was bought for
    public InventoryStock(String name, int quantity, int price, int unitCost) {
        super(name, price, unitCost);
        this.quantity = quantity;

    }

    public InventoryStock(JSONObject json) {
        super(json);
        int quantity = json.getInt("quantity");
        this.quantity = quantity;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("name",this.name);
        json.put("unitCost",this.unitCost);
        json.put("price",this.price);
        json.put("quantity",this.quantity);

        return json;
    }

    public long getValue() {
        long quantity = this.getQuantity();
        long price = this.getPrice();
        return quantity * price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    // MODIFIES: This
    // EFFECTS: Modifies the quantity of the item
    public void modifyInventory(int quantity) {
        this.quantity += quantity;
    }

    // EFFECTS: returns true if item can be sold
    public boolean isSellable(int quantity) {
        return quantity <= this.quantity;
    }

    // MODIFIES: This
    // EFFECTS: If item can be sold, subtracts amount sold from quantity and returns true
    //          if it can't be sold returns false
    public boolean sell(int quantity) {
        boolean isSuccess;
        if (quantity <= this.quantity) {
            Date date = new Date();
            InventoryStock stock = new InventoryStock(this.name, quantity, this.price, this.unitCost);
            this.quantity -= quantity;
            isSuccess = true;

        } else {
            isSuccess = false;
        }
        return isSuccess;

    }
}
