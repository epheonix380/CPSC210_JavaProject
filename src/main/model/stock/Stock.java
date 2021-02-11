package model.stock;

import model.Record;

public abstract class Stock {

    protected String name;
    protected int unitCost;
    protected int price;

    public Stock(String name, int price, int unitCost) {
        this.price = price;
        this.name = name;
        this.unitCost = unitCost;

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


    public abstract Record sell(int quantity);

    public void setUnitCost(int unitCost) {
        this.unitCost = unitCost;

    }


    public void setPrice(int price) {
        this.price = price;

    }


}
