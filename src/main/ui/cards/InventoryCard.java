package ui.cards;

import exceptions.ItemNotFound;
import model.shop.InventoryShop;
import model.stock.InventoryStock;
import ui.Frame;
import ui.Refreshable;
import ui.dialogue.StringEntry;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InventoryCard extends FrameCard implements ActionListener {

    private InventoryShop shop;
    private InventoryStock stock;
    private Refreshable refreshable;

    // MODIFIES: This
    // EFFECTS: frame is the frame that the card is contained in
    //          refreshable is an object that can be refreshed if state changes occur
    //          stock is the InventoryStock that the Card is currently displaying
    //          shop is the Shop that the inventory is contained in
    public InventoryCard(Frame frame, Refreshable refreshable, InventoryShop shop, InventoryStock stock) {
        super(frame);

        this.shop = shop;
        this.refreshable = refreshable;
        this.stock = stock;

        this.init(getLabelNames(), getComponentList());
    }

    // EFFECTS: Gets all the buttons and textFields required for this card and adds them to a list
    private java.util.List<Component> getComponentList() {
        ui.elements.Button editButton = new ui.elements.Button("Edit", this, "edit");

        List<Component> componentList = new ArrayList<>();
        componentList.add(editButton);

        return componentList;
    }

    // EFFECTS: Adds label names to a List then returns the List
    private List<String> getLabelNames() {
        List<String> labelNames = new ArrayList<>();

        labelNames.add(stock.getName());

        String stringPrice = String.valueOf(stock.getPrice());
        String price = formatMoney(stringPrice);
        labelNames.add(price);

        String stringAmount = String.valueOf(stock.getQuantity());
        String amount = "x" + stringAmount;
        labelNames.add(amount);

        String stringValue = String.valueOf(stock.getValue());
        String value = formatMoney(stringValue);
        labelNames.add(value);

        return labelNames;
    }

    // EFFECTS: formats money from cents to dollars and adds appropriate decimals and dollar signs
    private String formatMoney(String stringPrice) {
        String price;
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

        return price;

    }

    // EFFECTS: Receives an ActionEvent and executes a function depending on what the command is
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("edit")) {
            edit();
        }
    }

    // EFFECTS: Opens the quantity editing menu then edits the quantity of this.stock according to the
    //          result
    private void edit() {
        try {
            int modify = Integer.parseInt(new StringEntry().getString("Modification Amount"));
            shop.addInventory(stock.getName(), modify);
            refreshable.refresh();
            frame.update();
        } catch (ItemNotFound  e) {
            //IMPOSSIBLE STATE
        }
    }
}
