package ui.cards;

import model.stock.InventoryStock;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// Represents a card that shows an individual item in a receipt which is contained in a JFrame
public class ReceiptItemCard extends JFrameCard {

    private InventoryStock stock;

    // MODIFIES: This
    // EFFECTS: frame is the JFrame that this is contained in
    //          stock is the representation of item that this card displays
    public ReceiptItemCard(JFrame frame, InventoryStock stock) {
        super(frame);
        this.stock = stock;
        this.init(getLabelNames(), new ArrayList<>());

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

    // NOTE TO SELF: Improvement: Put formatMoney or derivative in FrameCard and JFrameCard so that
    //                            all cards can access it without having to repeat code

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


}
