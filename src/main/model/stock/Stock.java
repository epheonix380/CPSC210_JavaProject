package model.stock;


import org.json.JSONObject;
import persistence.JsonConvertable;

// Represents an Item in the shop with a name, the cost to purchase the item (in cents), and the price at which it is
// sold to the customer (in cents)
public abstract class Stock implements JsonConvertable {

    protected String name;
    protected int unitCost;
    protected int price;

    /*
     * REQUIRES: price > 0 and unitCost > 0
     * MODIFIES: this
     * EFFECTS: name is the name of the item to be created as a stock;
     *          price is the price at which the item will be sold to the customer;
     * 			unitCost is the price at which the retailer bought the item;
     */
    public Stock(String name, int price, int unitCost) {
        this.price = price;
        this.name = name;
        this.unitCost = unitCost;

    }

    // MODIFIES: This
    // EFFECTS: Converts JSONObject to stock
    public Stock(JSONObject json) {
        String name = json.getString("name");
        int price = json.getInt("price");
        int unitCost = json.getInt("unitCost");
        this.price = price;
        this.name = name;
        this.unitCost = unitCost;
    }

    // EFFECTS: Converts this to JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("name",this.name);
        json.put("unitCost",this.unitCost);
        json.put("price",this.price);

        return json;
    }

    public int getUnitCost() {
        return this.unitCost;

    }

    public int getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }

    // EFFECTS: Returns true if this stock can be sold, false otherwise
    public abstract boolean sell(int quantity);

    // MODIFIES: This
    // EFFECTS: Sets the unitCost of the item
    public void setUnitCost(int unitCost) {
        this.unitCost = unitCost;

    }

    // MODIFIES: This
    // EFFECTS: Sets the price of the item
    public void setPrice(int price) {
        this.price = price;

    }



}
