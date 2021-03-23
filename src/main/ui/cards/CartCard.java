package ui.cards;

import exceptions.ItemNotFound;
import exceptions.NotEnoughInventory;
import exceptions.NotPositiveInteger;
import model.shop.InventoryShop;
import model.shop.Shop;
import model.stock.InventoryStock;
import ui.Frame;
import ui.Refreshable;
import ui.elements.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartCard extends GeneralCard implements ActionListener {

    private InventoryStock stock;
    private Shop shop;
    private TextField textField;
    private Refreshable refreshable;

    public CartCard(Frame frame, Refreshable refreshable, InventoryStock stock, Shop shop) {
        super(frame);
        this.refreshable = refreshable;
        this.init(getLabelNames(stock), getComponentList(stock));
        this.stock = stock;
        this.shop = shop;
    }

    private List<String> getLabelNames(InventoryStock stock) {
        List<String> labelNames = new ArrayList<>();

        labelNames.add(stock.getName());

        String stringPrice = String.valueOf(stock.getPrice());
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
        labelNames.add(price);

        String stringAmount = String.valueOf(stock.getQuantity());
        String amount = "x" + stringAmount;
        labelNames.add(amount);

        String stringValue = String.valueOf(stock.getValue());
        String value;
        if (stringValue.length() >= 2) {
            int decimalPoint2 = stringValue.length() - 2;
            value = "$" + stringValue.substring(0, decimalPoint2) + "." + stringValue.substring(decimalPoint2);
        } else {
            value = stringValue;
            for (int i = 0; i < 2 - stringValue.length(); i++) {
                value = "0" + value;
            }
            value = "$0." + value;
        }
        labelNames.add(value);

        return labelNames;
    }

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

    private void delete() {
        try {
            shop.removeFromCart(this.stock.getName());
        } catch (ItemNotFound exception) {
            //This state is impossible with GUI
        }
        refreshable.refresh();
    }

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
