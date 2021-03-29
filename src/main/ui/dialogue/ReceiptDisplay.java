package ui.dialogue;

import model.Receipt;
import model.stock.InventoryStock;
import ui.cards.ReceiptItemCard;
import ui.elements.Label;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

// Represents a window in which a detailed receipt can be viewed
public class ReceiptDisplay extends JDialog {

    private Receipt receipt;
    private JFrame frame;

    // MODIFIES: This
    // EFFECTS: receipt is the Receipt that will be displayed in this window
    public ReceiptDisplay(Receipt receipt) {
        this.receipt = receipt;
        this.frame = new JFrame();
        this.setMinimumSize(new Dimension(300,600));
        this.setTitle("Receipt");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new Label("Time: " + receipt.getDateTime().toString()));
        panel.add(new Label("Total: " + parseInt(receipt.getTotal())));
        panel.add(generateCards());
        this.add(panel);
        this.show();

    }

    // EFFECTS: Generates a JScrollPane in which contained a ReceiptItemCard for every key value pair in
    //          the items map in receipt
    private JScrollPane generateCards() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Map<String, InventoryStock> receiptItems = receipt.getItems();
        for (Map.Entry<String,InventoryStock> entry : receiptItems.entrySet()) {
            InventoryStock stock = entry.getValue();
            ReceiptItemCard card = new ReceiptItemCard(frame, stock);
            panel.add(card);
        }

        return new JScrollPane(panel);
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
}
