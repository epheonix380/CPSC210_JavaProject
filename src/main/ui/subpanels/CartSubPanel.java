package ui.subpanels;

import exceptions.ItemAlreadyExists;
import exceptions.NotPositiveInteger;
import model.shop.Shop;
import model.stock.InventoryStock;
import model.stock.NIStock;
import ui.Frame;
import ui.Refreshable;
import ui.cards.CartCard;
import ui.cards.CatalogueCard;
import ui.dialogue.NonInventoryStockEntry;
import ui.elements.Button;
import ui.elements.Label;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class CartSubPanel extends JPanel implements ActionListener, Refreshable {

    private Frame frame;
    private Shop shop;
    private JScrollPane cards;
    private JPanel topBar;
    private Refreshable refreshable;

    public CartSubPanel(Frame frame, Shop shop, Refreshable refreshable) {
        this.frame = frame;
        this.shop = shop;
        this.refreshable = refreshable;
        LayoutManager layoutManager = new BoxLayout(this,BoxLayout.Y_AXIS);
        this.setLayout(layoutManager);
        generateTopBar();
        this.cards = generateCards();
        this.add(cards);
    }

    private void generateTopBar() {
        JPanel panel = new JPanel();
        LayoutManager layoutManager = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(layoutManager);
        System.out.println(shop.getCartTotal());
        Label total = new Label("Cart Total: " + parseInt(shop.getCartTotal()));
        ui.elements.Button destroyButton = new Button("x", this, "destroy");

        panel.add(total);
        panel.add(destroyButton);

        this.topBar = panel;
        this.add(panel);
    }

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

    private JScrollPane generateCards() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Map<String, InventoryStock> cart = shop.getCart();
        for (Map.Entry<String,InventoryStock> entry : cart.entrySet()) {
            InventoryStock stock = entry.getValue();
            CartCard card = new CartCard(frame, refreshable, stock, shop);
            panel.add(card);
        }

        return new JScrollPane(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("destroy")) {
            shop.destroyCart();
            this.refresh();
        }
    }


    @Override
    public void refresh() {
        this.remove(this.cards);
        this.remove(this.topBar);
        generateTopBar();
        this.cards = generateCards();
        this.add(cards);
        frame.update();
    }
}

