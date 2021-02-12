package model;

import model.stock.InventoryStock;

import java.util.Date;
import java.util.List;
import java.util.Map;

// Represents all the items purchased in a purchase, having a Map of items that were sold and the total
public class Receipt {

    final int total;
    final Map<String,InventoryStock> items;
    final Date dateTime;

    /*
     * REQUIRES: total > 0
     * MODIFIES: this
     * EFFECTS: total is the sum of all the values in the items map;
     * 			the items map is a map of all the items with the String being the item names
     *          dateTime is the date and time when the purchase occurred, this is for record keeping
     */
    public Receipt(int total, Map<String,InventoryStock> items) {
        this.total = total;
        this.items = items;
        dateTime = new Date();
    }

    public int getTotal() {
        return total;
    }

    public Date getDateTime() {
        return this.dateTime;
    }

    public Map<String,InventoryStock> getItems() {
        return items;
    }
}
