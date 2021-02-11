package ui;

import model.Receipt;
import model.shop.InventoryShop;
import model.shop.NonInventoryShop;
import model.shop.Shop;
import model.stock.InventoryStock;
import model.stock.NIStock;
import model.stock.Stock;

import java.util.*;

// POS Application
public class PointOfServiceApp {

    private Shop shop;
    private Boolean isInventory;
    private Scanner input;
    private Boolean active;

    // EFFECTS: Runs the application
    public PointOfServiceApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: Processes the user input
    private void runApp() {
        Boolean run = true;
        String command = null;

        run = init();

        while (run) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                run = false;
                break;
            } else {
                processCommand(command);
            }

        }
    }

    private Boolean init() {
        input = new Scanner(System.in);
        Boolean run = true;
        displayStartup();
        String command = null;
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("ni")) {
            shop = new NonInventoryShop();
            isInventory = false;
        } else if (command.equals("q")) {
            run = false;
        } else {
            isInventory = true;
            shop = new InventoryShop();
        }

        return run;
    }

    private void displayStartup() {
        System.out.println("\nSelect from:");
        System.out.println("\ti -> Inventory Shop");
        System.out.println("\tni -> Non-Inventory SHop");
        System.out.println("\tq -> quit");
    }

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tgC -> Get the Catalogue");
        System.out.println("\taC -> Add to the Catalogue");
        System.out.println("\trC -> Remove Item From the Catalogue");
        System.out.println("\tp -> Purchase item(s)");
        if (isInventory) {
            System.out.println("\tmI -> Modify Inventory");
        }
        System.out.println("\tq -> quit");
    }

    private void processCommand(String command) {
        if (command.equals("gc")) {
            getCatalogue();
        } else if (command.equals("ac")) {
            addToCatalogue();
        } else if (command.equals("rc")) {
            removeItemFromCatalogue();
        } else if (command.equals("p")) {
            purchase();
        } else if (command.equals("mi") && isInventory) {
            modifyInventory();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void getCatalogue() {
        Map<String, NIStock> catalogue =  shop.getCatalogue();
        for (Map.Entry<String,NIStock> entry : catalogue.entrySet()) {
            NIStock stock = entry.getValue();
            System.out.println("Item: " + stock.getName());
            System.out.println("\t Price: " + stock.getPrice());
        }
    }

    private void addToCatalogue() {
        String name;
        int price;
        int unitCost;
        name = validateString("Name of Item");
        price = validateInt("Price of Item");
        unitCost = validateInt("Cost of Item");
        shop.addToCatalogue(name,price,unitCost);
        System.out.println("Successfully Added " + name + " to Catalogue!");
    }

    private void removeItemFromCatalogue() {
        String name;
        name = validateString("Name of Item to remove");
        NIStock stock = shop.getCatalogue().get(name);
        shop.removeItemFromCatalogue(stock);
        System.out.println("Successfully Removed " + name + " from Catalogue!");
    }

    private void purchase() {
        active = true;
        String command = null;
        while (active) {
            getCart();
            showOptions();
            command = input.next();
            processPurchaseCommand(command);

        }
    }

    private void getCart() {
        Map<String, InventoryStock> cart = shop.getCart();
        for (Map.Entry<String,InventoryStock> entry : cart.entrySet()) {
            InventoryStock stock = entry.getValue();
            System.out.println("Item: " + stock.getName());
            System.out.println("\t Price: " + stock.getPrice());
            System.out.println("\t Quantity: " + stock.getQuantity());
            System.out.println("\t Subtotal: " + stock.getValue());
        }
    }

    private void showOptions() {
        System.out.println("\nSelect from:");
        System.out.println("\tmp -> Make Purchase");
        System.out.println("\tac -> Add to the Cart");
        System.out.println("\trc -> Remove Item From the Cart");
        System.out.println("\tdc -> Exit and destroy cart");
    }

    private void processPurchaseCommand(String command) {
        if (command.equals("mp")) {
            makePurchase();
        } else if (command.equals("ac")) {
            addToCart();
        } else if (command.equals("rc")) {
            removeFromCart();
        } else if (command.equals("dc")) {
            destroyCart();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void destroyCart() {
        shop.destroyCart();
        System.out.println("Cart Destroyed");
        active = false;
    }

    private void removeFromCart() {
        String name;
        name = validateString("Name of Item to remove from cart");
        shop.removeFromCart(name);
        System.out.println("Removed " + name + " from cart");
    }

    private void addToCart() {
        String name;
        int quantity;
        name = validateString("Name of Item to add to cart");
        quantity = validateInt("Amount of item to add to cart");
        NIStock stock = shop.getCatalogue().get(name);
        shop.addToCart(stock,quantity);
        System.out.println("Added " + quantity + " of " + name + " to cart");
    }

    private void makePurchase() {
        Receipt receipt = shop.makePurchase();
        System.out.println("Total: " + receipt.getTotal());
        Map<String,InventoryStock> items = receipt.getItems();
        for (Map.Entry<String,InventoryStock> entry : items.entrySet()) {
            InventoryStock stock = entry.getValue();
            System.out.println("Item: " + stock.getName());
            System.out.println("\t Price: " + stock.getPrice());
            System.out.println("\t Quantity: " + stock.getQuantity());
            System.out.println("\t Subtotal: " + stock.getValue());
        }
        active = false;
    }

    private void modifyInventory() {
        String name;
        int quantity;
        name = validateString("Name of Item to modify");
        quantity = validateInt("Modify Amount");
        NIStock stock = shop.getCatalogue().get(name);
        shop.addInventory(stock,quantity);
    }

    private int validateInt(String question) {
        int userInput = 0;
        Boolean wentToCatch = false;
        do {
            System.out.println(question);
            if (input.hasNextInt()) {
               userInput = input.nextInt();
               wentToCatch = true;
            } else {
                input.nextLine();
                System.out.println("Please Enter a valid integer");
                input.nextLine();
            }
        } while (!wentToCatch);
        return userInput;
    }

    private String validateString(String question) {
        String userInput = null;
        Boolean wentToCatch = false;
        do {
            System.out.println(question);
            if (input.hasNext()) {
                userInput = input.next();
                wentToCatch = true;
            } else {
                input.nextLine();
                System.out.println("Please Enter a valid String");
                input.nextLine();
            }
        } while (!wentToCatch);
        return userInput;
    }
}
