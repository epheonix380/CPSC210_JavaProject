package model.stock;


import org.json.JSONObject;

import java.util.Date;
import java.util.Map;

// Represents a NonInventoryStock, which does not track the amount of said Stock the store has
public class NIStock extends Stock {

    // MODIFIES: This
    // EFFECTS: name is the Name of the item
    //          price is how much the item will be sold for to the customer
    //          unitCost is how much the item was bought for
    public NIStock(String name, int price, int unitCost) {
        super(name, price, unitCost);
    }

    // MODIFIES: This
    // EFFECTS: Converts JSONObject into NIStock
    public NIStock(JSONObject json) {
        super(json);
    }

    // EFFECTS: Returns true if this stock can be sold, this method has no effect in NIStock
    @Override
    public boolean sell(int quantity) {
        return true;
    }

}
