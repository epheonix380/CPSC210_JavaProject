package interfaces;

import model.Record;

import javax.security.auth.AuthPermission;

public interface Stock {

    public int getQuantity();

    public int getValue();

    public int getCustomerValue();

    public int getUnitCost();

    public int getSellingPrice();

    public String getName();

    public Record sell(int quantity);

    public Record buy(int quantity);

    public void setUnitCost(int unitCost);

    public void setSellingPrice(int sellingPrice);

}
