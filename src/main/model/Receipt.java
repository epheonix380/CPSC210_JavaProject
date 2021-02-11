package model;

import model.stock.InventoryStock;

import java.util.List;
import java.util.Map;

public class Receipt {

    final int total;
    final Map<String,InventoryStock> items;

    public Receipt(int total, Map<String,InventoryStock> items) {
        this.total = total;
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public Map<String,InventoryStock> getItems() {
        return items;
    }
}
