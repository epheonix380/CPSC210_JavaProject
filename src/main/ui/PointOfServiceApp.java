package ui;

import exceptions.ItemAlreadyExists;
import exceptions.ItemNotFound;
import exceptions.NotEnoughInventory;
import exceptions.NotPositiveInteger;
import model.Receipt;
import model.shop.InventoryShop;
import model.shop.Shop;
import model.stock.InventoryStock;
import model.stock.NIStock;

import java.util.*;

// POS Application
public class PointOfServiceApp {

    private Shop shop;
    private Boolean isInventory;
    private Scanner input;
    private Boolean active;

    // EFFECTS: Runs the application
    public PointOfServiceApp(Shop shop, Boolean isInventory) {
        this.shop = shop;
        this.isInventory = isInventory;
        input = new Scanner(System.in);
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: Processes the user input
    private void runApp() {
        Boolean run = true;
        String command;

        while (run) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                break;
            } else {
                processCommand(command);
            }

        }
    }


    // EFFECTS: Prints the normal menu
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tgc -> Get the Catalogue");
        System.out.println("\tac -> Add to the Catalogue");
        System.out.println("\tec -> Edit the Catalogue");
        System.out.println("\trc -> Remove Item From the Catalogue");
        System.out.println("\tp -> Purchase item(s)");
        if (isInventory) {
            System.out.println("\tgi -> Get Inventory");
            System.out.println("\tai -> Add Inventory");
        }
        System.out.println("\tr -> Show Records");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: Executes the function related to the command
    private void processCommand(String command) {
        if (command.equals("gc")) {
            getCatalogue();
        } else if (command.equals("ac")) {
            addToCatalogue();
        } else if (command.equals("ec")) {
            editCatalogue();
        } else if (command.equals("rc")) {
            removeItemFromCatalogue();
        } else if (command.equals("p")) {
            purchase();
        } else if (command.equals("gi") && isInventory) {
            getInventory();
        } else if (command.equals("ai") && isInventory) {
            modifyInventory();
        } else if (command.equals("r")) {
            getRecords();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void editCatalogue() {
        String name = validateString("Name of Catalogue Item you wish to edit");
        int price = validateInt("New Price of item, if you wish to keep the original price, please put 0");
        int unitCost = validateInt("New Cost of item, if you wish to keep the original cost, please put 0");
        try {
            shop.editCatalogue(name,price,unitCost);
        } catch (ItemNotFound e) {
            itemNotFound(name);
        } catch (NotPositiveInteger e) {
            System.out.println(e.getMessage());
        }
    }

    // EFFECTS: Gets and prints records
    private void getRecords() {
        List<Receipt> records = shop.getRecords();
        System.out.println("\nRECORDS:");
        for (Receipt receipt : records) {
            System.out.println("\tDate and Time: " + receipt.getDateTime());
            System.out.println("\t\tTotal: " + receipt.getTotal());
            for (Map.Entry<String, InventoryStock> entry : receipt.getItems().entrySet()) {
                InventoryStock stock = entry.getValue();
                System.out.println("\t\tItem: " + stock.getName());
                System.out.println("\t\t\t Price: " + stock.getPrice());
                System.out.println("\t\t\t Quantity: " + stock.getQuantity());
                System.out.println("\t\t\t Subtotal: " + stock.getValue());
            }
        }
    }

    // REQUIRES: isInventory is true
    // EFFECTS: Gets and prints the inventory
    private void getInventory() {
        Map<String, InventoryStock> inventory = ((InventoryShop)shop).getInventoryMap();
        for (Map.Entry<String,InventoryStock> entry : inventory.entrySet()) {
            InventoryStock stock = entry.getValue();
            System.out.println("Item: " + stock.getName());
            System.out.println("\t Price: " + stock.getPrice());
            System.out.println("\t Unit Cost: " + stock.getUnitCost());
            System.out.println("\t Quantity: " + stock.getQuantity());
            System.out.println("\t Customer Value: " + stock.getValue());
        }
    }

    // EFFECTS: Gets and Prints the Catalogue
    private void getCatalogue() {
        Map<String, NIStock> catalogue =  shop.getCatalogue();
        for (Map.Entry<String,NIStock> entry : catalogue.entrySet()) {
            NIStock stock = entry.getValue();
            System.out.println("Item: " + stock.getName());
            System.out.println("\t Price: " + stock.getPrice());
        }
    }

    // MODIFIES: This
    // EFFECTS: Adds given item to Catalogue
    private void addToCatalogue() {
        String name;
        int price;
        int unitCost;
        name = validateString("Name of Item");
        price = validateInt("Price of Item");
        unitCost = validateInt("Cost of Item");
        try {
            shop.addToCatalogue(name,price,unitCost);
            System.out.println("Successfully Added " + name + " to Catalogue!");
        } catch (NotPositiveInteger e) {
            System.out.println(e.getMessage());
        } catch (ItemAlreadyExists e) {
            System.out.println(e.getMessage());
        }
    }

    // MODIFIES: This
    // EFFECTS: Removes given item from Catalogue
    private void removeItemFromCatalogue() {
        String name;
        name = validateString("Name of Item to remove");
        try {
            shop.removeItemFromCatalogue(name);
            System.out.println("Successfully Removed " + name + " from Catalogue!");
        } catch (ItemNotFound e) {
            itemNotFound(name);
        }

    }

    // MODIFIES: This
    // EFFECTS: Opens the purchase submenu
    private void purchase() {
        active = true;
        String command;
        while (active) {
            getCart();
            showOptions();
            command = input.next();
            processPurchaseCommand(command);

        }
    }

    // EFFECTS: Prints the cart with all the items in it
    private void getCart() {
        Map<String, InventoryStock> cart = shop.getCart();
        System.out.println("\nCART:");
        System.out.println("Total:" + shop.getCartTotal());
        if (shop.getCart().isEmpty()) {
            System.out.println("The Cart is Empty");
        } else {
            for (Map.Entry<String, InventoryStock> entry : cart.entrySet()) {
                InventoryStock stock = entry.getValue();
                System.out.println("\t Item: " + stock.getName());
                System.out.println("\t\t Price: " + stock.getPrice());
                System.out.println("\t\t Quantity: " + stock.getQuantity());
                System.out.println("\t\t Subtotal: " + stock.getValue());
            }
        }
    }

    // EFFECTS: Shows options in the Purchase submenu
    private void showOptions() {
        System.out.println("\nSelect from:");
        System.out.println("\tp -> Make Purchase");
        System.out.println("\ta -> Add to the Cart");
        System.out.println("\tr -> Remove Item From the Cart");
        System.out.println("\td -> Exit and destroy cart");
    }

    // EFFECTS: Executes the functions related to the commands
    private void processPurchaseCommand(String command) {
        if (command.equals("p")) {
            makePurchase();
        } else if (command.equals("a")) {
            addToCart();
        } else if (command.equals("r")) {
            removeFromCart();
        } else if (command.equals("d")) {
            destroyCart();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: Destroys the cart and exits the purchase submenu
    private void destroyCart() {
        shop.destroyCart();
        System.out.println("\nCart Destroyed");
        active = false;
    }

    // MODIFIES: This
    // EFFECTS: Removes the specified item from the cart
    private void removeFromCart() {
        String name;
        name = validateString("Name of Item to remove from cart");
        try {
            shop.removeFromCart(name);
        } catch (ItemNotFound e) {
            itemNotFound(name);
        }
        System.out.println("Removed " + name + " from cart");
    }

    // MODIFIES: This
    // EFFECTS: Adds the specified item at the specified quantity to cart
    private void addToCart() {
        String name;
        int quantity;
        name = validateString("Name of Item to add to cart");
        try {
            quantity = validateInt("Amount of item to add to cart");
            shop.addToCart(name,quantity);
            System.out.println("Added " + quantity + " of " + name + " to cart");
        } catch (ItemNotFound e) {
            itemNotFound(name);
        } catch (NotEnoughInventory e) {
            System.out.println(e.errorMessage());
        } catch (NotPositiveInteger e) {
            System.out.println(e.getMessage());
        }
    }

    // MODIFIES: This
    // EFFECTS: Purchases all the items in the cart
    private void makePurchase() {
        try {
            Receipt receipt = shop.makePurchase();
            System.out.println("\n Success!");
            System.out.println("Total: " + receipt.getTotal());
            Map<String,InventoryStock> items = receipt.getItems();
            for (Map.Entry<String,InventoryStock> entry : items.entrySet()) {
                InventoryStock stock = entry.getValue();
                System.out.println("\tItem: " + stock.getName());
                System.out.println("\t\t Price: " + stock.getPrice());
                System.out.println("\t\t Quantity: " + stock.getQuantity());
                System.out.println("\t\t Subtotal: " + stock.getValue());
            }
            destroyCart();
            active = false;
        } catch (NotEnoughInventory error) {
            System.out.println(error.errorMessage());
        } catch (NotPositiveInteger e) {
            shop.destroyCart();
            System.out.println("A catastrophic error has occurred, the cart has been destroyed.");
        }

    }

    // REQUIRES: isInventory is true
    // MODIFIES: This
    // EFFECTS: Change the amount of inventory of the specified item
    private void modifyInventory() {
        String name;
        int quantity;
        name = validateString("Name of Item to modify");
        try {
            quantity = validateInt("Modify Amount");
            ((InventoryShop)shop).addInventory(name,quantity);
        } catch (ItemNotFound e) {
            itemNotFound(name);
        }

    }

    // EFFECTS: Asks question and fetches only int answer
    private int validateInt(String question) {
        int userInput = 0;
        boolean wentToCatch = false;
        do {
            System.out.println("\n" + question);
            if (input.hasNextInt()) {
                userInput = input.nextInt();
                wentToCatch = true;
            } else {
                input.nextLine();
                System.out.println("\n Please Enter a valid integer");
                input.nextLine();
            }
        } while (!wentToCatch);
        return userInput;
    }

    // EFFECTS: Asks question and fetches only string answer
    private String validateString(String question) {
        String userInput = null;
        boolean wentToCatch = false;
        do {
            System.out.println("\n" + question);
            if (input.hasNext()) {
                userInput = input.next();
                wentToCatch = true;
            } else {
                input.nextLine();
                System.out.println("\n Please Enter a valid String");
                input.nextLine();
            }
        } while (!wentToCatch);
        return userInput;
    }

    // EFFECTS: Prints out the fact that the user specified item could not be found
    private void itemNotFound(String item) {
        String msg = "The requested item '" + item + "' was not found, please check spelling and try again";
        System.out.println(msg);
    }
}
