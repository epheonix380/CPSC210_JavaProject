package ui.cards;

import model.Receipt;
import model.shop.Shop;
import ui.Frame;
import ui.Refreshable;
import ui.dialogue.ReceiptDisplay;
import ui.dialogue.ShowReceipt;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


// Represents a Receipt summary card inside of a frame
public class ReceiptCard extends FrameCard implements ActionListener {

    private Receipt receipt;
    private Shop shop;
    private TextField textField;
    private Refreshable refreshable;

    // MODIFIES: This
    // EFFECTS: frame is the frame that the card is contained in
    //          refreshable is an object that needs to be refreshed if state changes occur
    //          receipt is the Receipt that the Card is currently displaying
    //          shop is the Shop that the receipt is contained in
    public ReceiptCard(Frame frame, Refreshable refreshable, Receipt receipt, Shop shop) {
        super(frame);
        this.receipt = receipt;
        this.refreshable = refreshable;
        this.init(getLabelNames(), getComponentList());
        this.shop = shop;
    }

    // EFFECTS: Adds label names to a List then returns the List
    private java.util.List<String> getLabelNames() {
        java.util.List<String> labelNames = new ArrayList<>();
        labelNames.add(receipt.getDateTime().toString());
        labelNames.add("Total: " + parseInt(receipt.getTotal()));
        labelNames.add(receipt.getItems().size() + " items");

        return labelNames;
    }

    // EFFECTS: formats money from cents to dollars and adds appropriate decimals and dollar signs
    private String parseInt(int integer) {
        String stringValue = String.valueOf(integer);
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
        return value;
    }

    // EFFECTS: Gets all the buttons and textFields required for this card and adds them to a list
    private java.util.List<Component> getComponentList() {
        ui.elements.Button openButton = new ui.elements.Button("Open", this, "open");

        List<Component> componentList = new ArrayList<>();
        componentList.add(openButton);

        return componentList;
    }

    // EFFECTS: Receives an ActionEvent and executes a function depending on what the command is
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("open")) {
            open();
        }
    }

    // EFFECTS: Opens the receipt in a new window (new JFrame)
    private void open() {
        ReceiptDisplay showReceipt = new ReceiptDisplay(receipt);
    }
}
