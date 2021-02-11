package model;

import model.stock.Stock;
import model.stock.InventoryStock;

import java.util.Date;

public class Record implements interfaces.Record {

    final Date date;

    final InventoryStock item;
    final Boolean isSuccess;

    public Record(Boolean isSuccess, Date date, InventoryStock item) {
        this.isSuccess = isSuccess;
        this.date = isSuccess ? date : null;
        this.item = isSuccess ? item : null;

    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public Stock getItem() {
        return this.item;
    }

    @Override
    public String getItemName() {
        return this.item.getName();
    }

    @Override
    public int getValue() {
        return this.item.getValue();
    }
}
