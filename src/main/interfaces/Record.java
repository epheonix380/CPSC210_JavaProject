package interfaces;

import model.Stock;

import java.sql.Time;
import java.util.Date;

public interface Record {

    public Date getDate();

    public Stock getStock();

    public String getStockName();

    public int getValue();

}
