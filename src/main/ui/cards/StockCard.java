package ui.cards;

import ui.Frame;
import ui.elements.Button;
import ui.elements.Label;
import ui.panels.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class StockCard extends JPanel {

    private LayoutManager layoutManager;

    public StockCard(Frame frame, String name, String price, Button topButton, Button bottomButton) {
        LayoutManager layoutManager = new FlowLayout();
        this.layoutManager = layoutManager;
        this.setLayout(layoutManager);
        this.add(createLabelStack(name, price));
        this.add(createButtonStack(topButton, bottomButton));
        frame.update();
    }

    public StockCard(Frame frame, String name, String price, String amount, String total, Button topButton, Button bottomButton) {
        LayoutManager layoutManager = new FlowLayout();
        this.layoutManager = layoutManager;
        this.setLayout(layoutManager);
        this.add(createLabelStack(name, price, amount, total));
        this.add(createButtonStack(topButton, bottomButton));
        frame.update();
    }

    private JPanel createButtonStack(Button topButton, Button bottomButton) {
        JPanel panel = new JPanel();
        LayoutManager layoutManager = new BoxLayout(this, BoxLayout.Y_AXIS);
        panel.setLayout(layoutManager);
        panel.add(topButton);
        panel.add(bottomButton);
        return panel;
    }

    private JPanel createLabelStack(String name, String price) {
        JPanel panel = new JPanel();
        LayoutManager layoutManager = new BoxLayout(this, BoxLayout.Y_AXIS);
        panel.setLayout(layoutManager);
        Label nameLabel = new Label(name);
        Label priceLabel = new Label(formatPrice(price));
        panel.add(nameLabel);
        panel.add(priceLabel);
        return panel;
    }

    private JPanel createLabelStack(String name, String price, String amount, String total) {
        JPanel panel = new JPanel();
        LayoutManager layoutManager = new BoxLayout(this, BoxLayout.Y_AXIS);
        panel.setLayout(layoutManager);
        Label nameLabel = new Label(name);
        Label priceLabel = new Label(formatPrice(price));
        Label amountLabel = new Label(formatAmount(amount));
        Label totalLabel = new Label(formatPrice(total));
        panel.add(nameLabel);
        panel.add(priceLabel);
        panel.add(amountLabel);
        panel.add(totalLabel);
        return panel;
    }

    private String formatAmount(String amount) {
        String formattedAmount = "x" + amount;
        return formattedAmount;
    }

    private String formatPrice(String price) {
        int length = price.length();
        int decimalPoint = length - 2;
        String dollarPrice = price.substring(0, decimalPoint) + "." + price.substring(decimalPoint);
        String formattedPrice = "$" + dollarPrice;
        return formattedPrice;
    }

}
