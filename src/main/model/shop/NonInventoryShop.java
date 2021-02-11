package model.shop;

import model.stock.InventoryStock;
import model.Receipt;
import model.Record;
import model.stock.NIStock;

import java.util.Map;

//Represents NonInventoryShop, which does not track the inventory of the shop
public class NonInventoryShop extends Shop {

    @Override
    public Receipt makePurchase() {
        int total = 0;
        for (Map.Entry<String,InventoryStock> entry : this.cart.entrySet()) {
            InventoryStock stock = entry.getValue();
            total += stock.getValue();
            Record sale = this.catalogue.get(stock.getName()).sell(stock.getQuantity());
            this.records.add(sale);
        }
        Receipt receipt = new Receipt(total,this.cart);
        return receipt;
    }

    @Override
    public void addInventory(NIStock stock, int quantity) {

    }
}
