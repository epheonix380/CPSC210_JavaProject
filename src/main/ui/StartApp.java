package ui;

import model.shop.InventoryShop;
import model.shop.NonInventoryShop;
import model.shop.Shop;
import persistence.JsonReader;

import java.util.Scanner;

public class StartApp {

    private Shop shop;
    private boolean isInventory;
    private Scanner input;

    public StartApp() {
        input = new Scanner(System.in);
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: Processes the user input

    private void runApp() {
        Boolean run = true;
        while (run) {
            run = init();
            if (run) {
                new PointOfServiceApp(shop,isInventory);
            }
        }
    }

    // MODIFIES: This
    // EFFECTS: Initializes the application
    private Boolean init() {
        displayStartup();
        String command;
        command = input.next();
        command = command.toLowerCase();

        return evaluateCommand(command);
    }

    private boolean evaluateCommand(String command) {
        boolean run = true;
        JsonReader reader = new JsonReader("./data/shops/shop.json");
        if (command.equals("ni")) {
            shop = new NonInventoryShop();
            isInventory = false;
        } else if (command.equals("q")) {
            run = false;
        } else if (command.equals("l")) {
            try {
                shop = reader.read();
                isInventory = reader.isInventory();
            } catch (Exception e) {
                System.out.println("An Error has occured please try again");
                run = true;
            }
        } else {
            isInventory = true;
            shop = new InventoryShop();
        }
        return run;
    }

    // EFFECTS: Prints the startup menu
    private void displayStartup() {
        System.out.println("\nSelect from:");
        System.out.println("\ti -> Inventory Shop");
        System.out.println("\tni -> Non-Inventory SHop");
        System.out.println("\tl -> Load Existing Shop");
        System.out.println("\tq -> quit");
    }

}
