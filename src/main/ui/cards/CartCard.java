package ui.cards;

import exceptions.ItemNotFound;
import exceptions.NotEnoughInventory;
import model.shop.Shop;
import model.stock.InventoryStock;
import ui.Frame;
import ui.Refreshable;
import ui.elements.Button;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Represents a card of information displaying an item in the cart
public class CartCard extends FrameCard implements ActionListener {

    private InventoryStock stock;
    private Shop shop;
    private TextField textField;
    private Refreshable refreshable;

    // MODIFIES: This
    // EFFECTS: frame is the frame that the card is contained in
    //          refreshable is an object that can be refreshed if state changes occur
    //          stock is the InventoryStock that the Card is currently displaying
    //          shop is the Shop that the cart is contained in
    public CartCard(Frame frame, Refreshable refreshable, InventoryStock stock, Shop shop) {
        super(frame);
        this.refreshable = refreshable;
        this.init(getLabelNames(stock), getComponentList(stock));
        this.stock = stock;
        this.shop = shop;
    }

    // EFFECTS: Adds label names to a List then returns the List
    private List<String> getLabelNames(InventoryStock stock) {
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

    // EFFECTS: Gets all the buttons and textFields required for this card and adds them to a list
    private List<Component> getComponentList(InventoryStock stock) {
        Button plusButton = new Button("+", this, "add");
        Button minusButton = new Button("-", this, "subtract");
        Button deleteButton = new Button("Delete", this, "delete");
        String displayText = String.valueOf(stock.getQuantity());
        TextField textField = new TextField(displayText);
        textField.addActionListener(this);
        this.textField = textField;

        List<Component> componentList = new ArrayList<>();
        componentList.add(plusButton);
        componentList.add(textField);
        componentList.add(minusButton);
        componentList.add(deleteButton);

        return componentList;
    }

    // EFFECTS: Receives an ActionEvent and executes a function depending on what the command is
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            addOne();
        } else if (e.getActionCommand().equals("subtract")) {
            subtractOne();
        } else if (e.getActionCommand().equals("delete")) {
            delete();
        } else {
            addText();
        }
    }

    // MODIFIES: This
    // EFFECTS: Deletes this.stock from shop, refreshes so the state change is displayed
    private void delete() {
        try {
            shop.removeFromCart(this.stock.getName());
        } catch (ItemNotFound exception) {
            //This state is impossible with GUI
        }
        refreshable.refresh();
    }

    // MODIFIES: This
    // EFFECTS: Modifies the cart stock according to what the text in the textField says
    private void addText() {
        String text = textField.getText();
        if (!text.isEmpty()) {
            int amount = Integer.parseInt(text);
            if (amount > 0) {
                int delta = amount - stock.getQuantity();
                try {
                    shop.modifyCart(stock.getName(), delta);
                } catch (NotEnoughInventory exception) {
                    super.frame.sayErrorMessage(exception.errorMessage());
                } catch (ItemNotFound exception) {
                    //This state is impossible with GUI
                }
            } else if (amount == 0) {
                delete();
            } else {
                textField.setText(String.valueOf(this.stock.getQuantity()));
                frame.sayErrorMessage("Value was less than 1");
            }
        }
        this.refreshable.refresh();
    }

    // MODIFIES: This
    // EFFECTS: adds one to this.stock
    private void addOne() {
        try {
            shop.modifyCart(stock.getName(), 1);
            this.refreshable.refresh();
        } catch (NotEnoughInventory e) {
            super.frame.sayErrorMessage(e.errorMessage());
        } catch (ItemNotFound e) {
            //This state is impossible with GUI
        }
    }

    // MODIFIES: This
    // EFFECTS: If this.stock.getQuantity > 0: Subtracts one from this.stock
    //          else deletes this.stock from shop
    private void subtractOne() {
        if (this.stock.getQuantity() > 0) {
            try {
                shop.modifyCart(stock.getName(), -1);
                this.refreshable.refresh();
            } catch (NotEnoughInventory e) {
                super.frame.sayErrorMessage(e.errorMessage());
            } catch (ItemNotFound e) {
                //This state is impossible with GUI
            }
        } else {
            delete();
        }
    }
}
