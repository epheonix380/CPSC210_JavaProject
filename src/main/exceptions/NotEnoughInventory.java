package exceptions;

import model.stock.InventoryStock;

// Represents Error that occurs when The amount of items in inventory is smaller than the amount of items requested
public class NotEnoughInventory extends Exception {

    private InventoryStock request;
    private InventoryStock currentInventory;

    // MODIFIES: This
    // EFFECTS:  currentInventory is the current inventory that is available in the shop
    //           requestInventory is the inventory that has been requested by the customer
    public NotEnoughInventory(InventoryStock request, InventoryStock currentInventory) {
        this.currentInventory = currentInventory;
        this.request = request;
    }

    // EFFECTS: Creates and returns a custom error message based on the request and currentInventory
    public String errorMessage() {
        String messagePart1 = request.getQuantity() + " items of the name " + request.getName();
        String messagePart2 = " were requested but only " + currentInventory.getQuantity() + " are available";

        return messagePart1 + messagePart2;
    }

}
