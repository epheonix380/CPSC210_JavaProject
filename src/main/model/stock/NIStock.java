package model.stock;

import model.Record;

import java.util.Date;

// Represents a NonInventoryStock, which does not track the amount of said Stock the store has
public class NIStock extends Stock {

    public NIStock(String name, int price, int unitCost) {
        super(name, price, unitCost);
    }

    @Override
    public Record sell(int quantity) {
        Record itemSale;
        Date date = new Date();
        InventoryStock stock = new InventoryStock(this.name, quantity, this.price, this.unitCost);
        itemSale = new Record(true, date, stock);
        return itemSale;
    }
}
