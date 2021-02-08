package model;

import java.sql.Time;
import java.util.Date;
import java.util.Timer;

public class Stock implements interfaces.Stock {

    private String name;
    private int quantity;
    private int unitCost;
    private int sellingPrice;
    private int value;
    private int revenue;

    public Stock(String name, int quantity,int sellingPrice, int unitCost) {
        this.sellingPrice = sellingPrice;
        this.name = name;
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.value = quantity * unitCost;
        this.revenue = quantity * sellingPrice;

    }

    @Override
    public int getQuantity() {
        return this.quantity;

    }

    @Override
    public int getValue() {
        this.value = this.quantity * this.unitCost;
        return this.value;

    }

    @Override
    public int getCustomerValue() {
        this.value = this.quantity * this.sellingPrice;
        return this.value;
    }

    @Override
    public int getUnitCost() {
        return this.unitCost;

    }

    @Override
    public int getSellingPrice() {
        return this.getSellingPrice();
    }

    @Override
    public String getName() {
        return this.name;

    }

    @Override
    public Record sell(int quantity) {
        Record itemSale;
        if (quantity <= this.quantity) {
            Date date = new Date();
            Stock stock = new Stock(this.name, quantity, this.sellingPrice, this.unitCost);
            itemSale = new Record(true, date, stock);

        } else {
            itemSale = new Record(false, null,null);

        }
        return itemSale;

    }

    @Override
    public Record buy(int quantity) {
        return null;
    }

    @Override
    public void setUnitCost(int unitCost) {

    }

    @Override
    public void setSellingPrice(int sellingPrice) {

    }
}
