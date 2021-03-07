package model;

import exceptions.NotPositiveInteger;
import model.stock.InventoryStock;
import model.stock.NIStock;
import org.json.JSONObject;
import persistence.JsonConvertable;

import java.util.*;

// Represents all the items purchased in a purchase, having a Map of items that were sold and the total
public class Receipt implements JsonConvertable {

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
    public Receipt(int total, Map<String,InventoryStock> items) throws NotPositiveInteger {
        if (total <= 0) {
            throw new NotPositiveInteger(total);
        }
        this.total = total;
        this.items = items;
        dateTime = new Date();
    }

    public Receipt(JSONObject json) {
        this.total = json.getInt("total");
        this.items = itemsFromJson(json.getJSONObject("items"));
        this.dateTime = new Date(json.getLong("dateTime"));
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("total", total);
        json.put("items", itemsToJson());
        json.put("dateTime", dateTime.getTime());

        return json;
    }

    private Map<String, InventoryStock> itemsFromJson(JSONObject json) {
        Map<String, InventoryStock> items = new TreeMap<>();
        Iterator<String> jsonKeys = json.keys();

        for (Iterator<String> it = jsonKeys; it.hasNext(); ) {
            String key = it.next();
            JSONObject jsonObject = json.getJSONObject(key);
            InventoryStock stock = new InventoryStock(jsonObject);
            items.put(key, stock);
        }

        return items;
    }

    private JSONObject itemsToJson() {
        JSONObject json = new JSONObject();

        for (Map.Entry<String,InventoryStock> entry : items.entrySet()) {
            InventoryStock stock = entry.getValue();
            json.put(entry.getKey(),stock.toJson());
        }

        return json;
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
