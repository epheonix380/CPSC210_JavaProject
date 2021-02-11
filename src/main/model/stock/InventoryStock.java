package model.stock;

import model.Record;

import java.util.Date;

public class InventoryStock extends Stock {

    private int quantity;

    public InventoryStock(String name, int quantity, int price, int unitCost) {
        super(name, price, unitCost);
        this.quantity = quantity;

    }

    public int getValue() {
        return this.quantity * this.price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void modifyInventory(int quantity) {
        this.quantity += quantity;
    }

    public Record sell(int quantity) {
        Record itemSale;
        if (quantity <= this.quantity) {
            Date date = new Date();
            InventoryStock stock = new InventoryStock(this.name, quantity, this.price, this.unitCost);
            itemSale = new Record(true, date, stock);
            this.quantity -= quantity;

        } else {
            itemSale = new Record(false, null,null);

        }
        return itemSale;

    }
}
