package model;

import java.sql.Time;
import java.util.Date;

public class Record implements interfaces.Record {

    final Date date;

    final Stock stock;
    final Boolean isSuccess;

    public Record(Boolean isSuccess, Date date, Stock stock) {
        this.isSuccess = isSuccess;
        this.date = isSuccess ? date : null;
        this.stock = isSuccess ? stock : null;

    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public Stock getStock() {
        return this.stock;
    }

    @Override
    public String getStockName() {
        return this.stock.getName();
    }

    @Override
    public int getValue() {
        return this.stock.getValue();
    }
}
