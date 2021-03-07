package ui;

import model.shop.InventoryShop;
import model.shop.NonInventoryShop;
import model.shop.Shop;
import persistence.Index;
import persistence.ShopJson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;

public class StartApp {

    private Shop shop;
    private boolean isInventory;
    private Scanner input;
    private Index index = new Index();

    public StartApp() {
        input = new Scanner(System.in);
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: Processes the user input

    private void runApp() {
        Boolean run = true;
        while (run) {
            int state = init();
            if (state == 1) {
                new PointOfServiceApp(shop,isInventory);
            } else if (state == 2) {
                run = true;
            } else {
                run = false;
            }
        }
    }

    // MODIFIES: This
    // EFFECTS: Initializes the application
    private int init() {
        displayStartup();
        String command;
        command = input.next();
        command = command.toLowerCase();

        return evaluateCommand(command);
    }

    private int evaluateCommand(String command) {
        int state = 1;
        if (command.equals("ni")) {
            state = createNI();
        } else if (command.equals("q")) {
            state = 0;
        } else if (command.equals("l")) {
            state = load();
        } else if (command.equals("d")) {
            delete();
            state = 2;
        } else if (command.equals("i")) {
            state = createIN();
        } else {
            System.out.println("Command not recognised");
        }
        return state;
    }

    private int createIN() {
        try {
            String shopName = validateString("Name of Shop");
            if (index.contains(shopName)) {
                System.out.println("This shop already exists, delete the existing shop or choose another name");
                return 2;
            } else {
                shop = new InventoryShop(shopName);
                isInventory = true;
                return 1;
            }
        } catch (IOException e) {
            System.out.println("An error has occurred while trying to read the index");
            return 2;
        }
    }

    private int createNI() {
        try {
            String shopName = validateString("Name of Shop");
            if (index.contains(shopName)) {
                System.out.println("This shop already exists, delete the existing shop or choose another name");
                return 2;
            } else {
                shop = new NonInventoryShop(shopName);
                isInventory = false;
                return 1;
            }
        } catch (IOException e) {
            System.out.println("An error has occurred while trying to read the index");
            return 2;
        }
    }

    private void delete() {
        try {
            System.out.println("Please choose from the indexed shop files:");
            for (String str : index.getIndex()) {
                System.out.println("\t " + str);
            }
        } catch (IOException e) {
            System.out.println("Error Retrieving index");
        }
        String shopName = validateString("Name of Shop to delete:");
        try {
            index.delete(shopName);
        } catch (FileNotFoundException e) {
            System.out.println("The file you specified was not found");
        } catch (IOException e) {
            System.out.println("There was an error while trying to find the file");
        }
    }

    private int load() {
        int state = 2;
        try {
            System.out.println("Please choose from the indexed shop files:");
            for (String str : index.getIndex()) {
                System.out.println("\t " + str);
            }
        } catch (IOException e) {
            System.out.println("Error Retrieving index");
            return 2;
        }
        String shopName = validateString("Name of Shop");
        try {
            ShopJson shopJson = new ShopJson(shopName);
            this.shop = shopJson.getShop();
            isInventory = this.shop.getShopType() == "inventory";
            state = 1;
        } catch (NoSuchFileException e) {
            System.out.println("That file does not exist");
        } catch (Exception e) {
            System.out.println("An Error has occurred please try again");
        }
        return state;
    }

    // EFFECTS: Prints the startup menu
    private void displayStartup() {
        System.out.println("\nSelect from:");
        System.out.println("\ti -> Inventory Shop");
        System.out.println("\tni -> Non-Inventory SHop");
        System.out.println("\tl -> Load Existing Shop");
        System.out.println("\td -> Delete Existing Shop");
        System.out.println("\tq -> quit");
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
}
