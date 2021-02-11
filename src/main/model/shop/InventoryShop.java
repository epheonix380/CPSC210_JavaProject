package model.shop;

import errors.NotEnoughInventoryError;
import model.Receipt;
import model.Record;
import model.stock.NIStock;
import model.stock.InventoryStock;

import java.util.HashMap;
import java.util.Map;

//Represents InventoryShop, tracks the inventory going in and out of the shop
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
        Map<String, InventoryStock> soldItems = new HashMap<>();

        for (Map.Entry<String,InventoryStock> entry : this.cart.entrySet()) {
            InventoryStock stock = entry.getValue();
            String name = stock.getName();
            int quantity = stock.getQuantity();
            InventoryStock requestedStock = inventoryMap.get(name);

            Record sale = requestedStock.sell(quantity);
            if (sale.getSuccess()) {
                this.records.add(sale);
                total += stock.getValue();
                soldItems.put(entry.getKey(),entry.getValue());
            } else {
                this.records.add(sale);
                NotEnoughInventoryError error = new NotEnoughInventoryError(stock, requestedStock);
                throw error;
            }

        }
        Receipt receipt = new Receipt(total,soldItems);
        return receipt;
    }
}
