package ui.cards;

import exceptions.ItemNotFound;
import exceptions.NotEnoughInventory;
import exceptions.NotPositiveInteger;
import model.shop.Shop;
import model.stock.NIStock;
import ui.Frame;
import ui.Refreshable;
import ui.dialogue.NonInventoryStockEntry;
import ui.dialogue.StringEntry;
import ui.elements.Button;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Represents a card displaying information about a catalogue item in a shop
public class CatalogueCard extends FrameCard implements ActionListener {

    public final NIStock stock;
    private Shop shop;
    private TextField textField;
    private Refreshable refreshable;

    // MODIFIES: This
    // EFFECTS: frame is the frame that the card is contained in
    //          refreshable is an object that can be refreshed if state changes occur
    //          stock is the NIStock that the Card is currently displaying
    //          shop is the Shop that the catalogue is contained in
    public CatalogueCard(Frame frame, Refreshable refreshable, NIStock stock, Shop shop) {
        super(frame);
        this.refreshable = refreshable;
        this.init(getLabelNames(stock), getComponentList(stock));
        this.stock = stock;
        this.shop = shop;
    }

    // EFFECTS: Adds label names to a List then returns the List
    private java.util.List<String> getLabelNames(NIStock stock) {
        java.util.List<String> labelNames = new ArrayList<>();

        labelNames.add(stock.getName());
        String price;
        String stringPrice = String.valueOf(stock.getPrice());
        if (stringPrice.length() >= 2) {
            int decimalPoint1 = stringPrice.length() - 2;
            price = "$" + stringPrice.substring(0, decimalPoint1) + "." + stringPrice.substring(decimalPoint1);
        } else {
            price = stringPrice;
            for (int i = 0; i < 2 - stringPrice.length(); i++) {
                price = "0" + price;
            }
            price = "$0." + price;
        }
        labelNames.add(price);

        return labelNames;
    }

    // EFFECTS: Gets all the buttons and textFields required for this card and adds them to a list
    private java.util.List<Component> getComponentList(NIStock stock) {
        ui.elements.Button editButton = new ui.elements.Button("Edit", this, "edit");
        ui.elements.Button deleteButton = new ui.elements.Button("Delete", this, "delete");
        Button addToCartButton = new Button("Add To Cart", this, "cart");

        List<Component> componentList = new ArrayList<>();
        componentList.add(addToCartButton);
        componentList.add(editButton);
        componentList.add(deleteButton);

        return componentList;
    }

    // EFFECTS: Receives an ActionEvent and executes a function depending on what the command is
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("edit")) {
            editNIStock();
        } else if (e.getActionCommand().equals("delete")) {
            try {
                shop.removeItemFromCatalogue(this.stock.getName());
            } catch (ItemNotFound exception) {
                //Impossible state in GUI
            }
            refreshable.refresh();
        } else if (e.getActionCommand().equals("cart")) {
            addToCart();
            refreshable.refresh();
        } else {
//
        }
    }

    // EFFECTS: Adds this.stock to card, asking for the quantity while doing so, then refreshes
    private void addToCart() {
        StringEntry entry = new StringEntry();
        try {
            int amount = Integer.parseInt(entry.getString("How many?"));
            shop.addToCart(this.stock.getName(), amount);
        } catch (NotEnoughInventory e) {
            frame.sayErrorMessage(e.errorMessage());

        } catch (NotPositiveInteger e) {
            frame.sayErrorMessage(e.getMessage());
        } catch (ItemNotFound e) {
            // IMPOSSIBLE WITH GUI
        } catch (Exception e) {
            //
        }
    }

    // EFFECTS: Collects the edits that will be done on this.stock then commits said edits
    private void editNIStock() {
        NonInventoryStockEntry entry = new NonInventoryStockEntry();
        Map<String, String> infoMap = entry.getString(this.stock);
        if (!infoMap.isEmpty()) {
            String name = infoMap.get("name");
            int price = Integer.parseInt(infoMap.get("price"));
            int cost = Integer.parseInt(infoMap.get("cost"));
            try {
                shop.editCatalogue(name,price,cost);
                this.init(getLabelNames(stock), getComponentList(stock));
                frame.update();
            } catch (NotPositiveInteger e) {
                frame.sayErrorMessage(e.getMessage());
            } catch (ItemNotFound e) {
                frame.sayErrorMessage(e.getMessage());
            }
        }
    }

}

