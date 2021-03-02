package model.stock;


import org.json.JSONObject;

import java.util.Date;

// Represents a NonInventoryStock, which does not track the amount of said Stock the store has
public class NIStock extends Stock {

    public NIStock(String name, int price, int unitCost) {
        super(name, price, unitCost);
    }

    public NIStock(JSONObject json) {
        super(json);
    }

    // EFFECTS: Returns true if this stock can be sold, this method has no effect in NIStock
    @Override
    public boolean sell(int quantity) {
        return true;
    }
}
