package model;

import model.stock.Stock;
import model.stock.InventoryStock;

import java.util.Date;

// The way in which the Shop remembers all the transactions that happened, having date time, item sold,
// and whether the item was successfully sold
public class Record implements interfaces.Record {

    final Date date;

    final InventoryStock item;
    final Boolean isSuccess;

    /*
     * MODIFIES: this
     * EFFECTS: isSuccess represents whether the transaction this record represents was successful;
     * 			date represents the date time when the transaction occurred;
     *          item is the items that the transaction occur uppon
     */
    public Record(Boolean isSuccess, Date date, InventoryStock item) {
        this.isSuccess = isSuccess;
        this.date = isSuccess ? date : null;
        this.item = isSuccess ? item : null;

    }

    public boolean getSuccess() {
        return this.isSuccess;
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
