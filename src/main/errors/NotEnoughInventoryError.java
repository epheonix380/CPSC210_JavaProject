package errors;

import model.stock.InventoryStock;

public class NotEnoughInventoryError extends Error {

    private InventoryStock request;
    private InventoryStock currentInventory;

    public NotEnoughInventoryError(InventoryStock request, InventoryStock currentInventory) {
        this.currentInventory = currentInventory;
        this.request = request;
    }

    public String errorMessage() {
        String messagePart1 = request.getQuantity() + " items of the name " + request.getName();
        String messagePart2 = " were requested but only " + currentInventory.getQuantity() + " are available";

        return messagePart1 + messagePart2;
    }

}
