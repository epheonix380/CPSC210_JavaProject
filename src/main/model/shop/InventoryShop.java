package model.shop;

import model.Receipt;
import model.Record;
import model.stock.NIStock;
import model.stock.InventoryStock;

import java.util.HashMap;
import java.util.Map;

public class InventoryShop extends Shop {

    private Map<String, InventoryStock> inventoryMap = new HashMap<>();

    public InventoryShop() {
    }

    public Map<String, InventoryStock> getInventoryMap() {
        return this.inventoryMap;
    }

    public Boolean inventoryContains(String name) {
        return this.inventoryMap.containsKey(name);
    }

    @Override
    public void addInventory(NIStock stock, int quantity) {
        this.inventoryMap.get(stock.getName()).modifyInventory(quantity);
    }

    @Override
    public void addToCatalogue(String name, int price, int unitCost) {
        NIStock stock = new NIStock(name, price, unitCost);
        this.catalogue.put(name,stock);
        InventoryStock inventoryStock = new InventoryStock(name, 0, price, unitCost);
        this.inventoryMap.put(name, inventoryStock);

    }

    @Override
    public void removeItemFromCatalogue(NIStock stock) {
        this.catalogue.remove(stock.getName());
        this.inventoryMap.remove(stock.getName());
    }



    @Override
    public Receipt makePurchase() {
        int total = 0;
        for (Map.Entry<String,InventoryStock> entry : this.cart.entrySet()) {
            InventoryStock stock = entry.getValue();
            total += stock.getValue();
            String name = stock.getName();
            int quantity = -1 * stock.getQuantity();
            Record sale = inventoryMap.get(name).sell(quantity);
            this.records.add(sale);
        }
        Receipt receipt = new Receipt(total,this.cart);
        return receipt;
    }
}
