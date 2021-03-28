package ui.dialogue;

import model.Receipt;
import model.stock.InventoryStock;
import ui.Frame;
import ui.cards.CartCard;
import ui.cards.ReceiptCard;
import ui.cards.ReceiptItemCard;
import ui.elements.Label;

import javax.swing.*;
import java.util.Map;

// Represents a window in which a detailed receipt can be viewed
public class ShowReceipt extends JOptionPane {

    private JFrame frame;
    private Receipt receipt;

    // MODIFIES: This
    // EFFECTS: receipt is the Receipt that will be displayed in this window
    public ShowReceipt(Receipt receipt) {
        this.receipt = receipt;
        this.frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = generateCards();
        String time = "Time: " + receipt.getDateTime().toString();
        String total = "Total: " + parseInt(receipt.getTotal());
        Label timeLabel = new Label(time);
        Label totalLabel = new Label(total);
        panel.add(timeLabel);
        panel.add(totalLabel);
        panel.add(scrollPane);
        int result = this.showConfirmDialog(null, panel,
                "Receipt", JOptionPane.CANCEL_OPTION);

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
