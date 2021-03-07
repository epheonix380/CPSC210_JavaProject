package model.shop;

import exceptions.NotPositiveInteger;
import model.stock.InventoryStock;
import model.Receipt;
import model.stock.NIStock;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//Represents NonInventoryShop, which does not track the inventory of the shop
public class NonInventoryShop extends Shop {

    // MODIFIES: This
    // EFFECTS: shopName is the name of the shop, and the name that the shop will be saved as in json
    public NonInventoryShop(String shopName) {
        super(shopName);
        super.shopType = "nonInventory";
        save();
    }

    // MODIFIES: This
    // EFFECTS: shopName is the name of the shop, and the name that the shop will be saved as in json
    //          json is the JSONObject from which the shop will be constructed
    public NonInventoryShop(String shopName, JSONObject json) {
        super(shopName, json);
        super.shopType = "nonInventory";
    }

    // MODIFIES: This
    // EFFECT:  IF cart total <= 0:
    //              Throw NotPositiveInteger exception
    //          else:
    //              Takes all the items from cart and purchases them
    @Override
    public Receipt makePurchase() throws NotPositiveInteger {
        int total = 0;
        for (Map.Entry<String,InventoryStock> entry : this.cart.entrySet()) {
            InventoryStock stock = entry.getValue();
            total += stock.getValue();
            this.catalogue.get(stock.getName().toLowerCase()).sell(stock.getQuantity());
        }
        Receipt receipt = new Receipt(total,this.cart);
        this.cart = new HashMap<>();
        this.transactions.add(receipt);
        super.save();
        return receipt;
    }

}
