package interfaces;

import model.stock.Stock;

import java.util.Date;

public interface Record {

    public Date getDate();

    public Stock getItem();

    public String getItemName();

    public int getValue();

}
