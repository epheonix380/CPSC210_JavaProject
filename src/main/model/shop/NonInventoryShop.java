package model.shop;

import model.stock.InventoryStock;
import model.Receipt;
import model.stock.NIStock;

import java.util.HashMap;
import java.util.Map;

//Represents NonInventoryShop, which does not track the inventory of the shop
public class NonInventoryShop extends Shop {

    // MODIFIES: This
    // EFFECT: Takes all the items from cart and attempts to purchase them
    @Override
    public Receipt makePurchase() {
        int total = 0;
        for (Map.Entry<String,InventoryStock> entry : this.cart.entrySet()) {
            InventoryStock stock = entry.getValue();
            total += stock.getValue();
            this.catalogue.get(stock.getName().toLowerCase()).sell(stock.getQuantity());
        }
        Receipt receipt = new Receipt(total,this.cart);
        this.cart = new HashMap<>();
        this.transactions.add(receipt);
        return receipt;
    }

}
