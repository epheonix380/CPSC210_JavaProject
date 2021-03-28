package ui.subpanels;

import exceptions.ItemAlreadyExists;
import exceptions.NotEnoughInventory;
import exceptions.NotPositiveInteger;
import model.Receipt;
import model.shop.Shop;
import model.stock.InventoryStock;
import model.stock.NIStock;
import ui.Frame;
import ui.Refreshable;
import ui.cards.CartCard;
import ui.cards.CatalogueCard;
import ui.dialogue.NonInventoryStockEntry;
import ui.dialogue.ShowErrorMessage;
import ui.elements.Button;
import ui.elements.Label;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

// Represents the SubPanel that displays the shopping Cart inside of the main Panel
public class CartSubPanel extends JPanel implements ActionListener, Refreshable {

    private Frame frame;
    private Shop shop;
    private JScrollPane cards;
    private JPanel topBar;
    private Refreshable refreshable;

    // MODIFIES: This
    // EFFECTS: frame is the Frame in which this is contained
    //          shop is the Shop that the cart is contained in
    //          refreshable is the parent that is refreshable
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

    // MODIFIES: This
    // EFFECTS: Creates a panel that will become the topbar
    private void generateTopBar() {
        JPanel panel = new JPanel();
        LayoutManager layoutManager = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(layoutManager);
        System.out.println(shop.getCartTotal());
        Label total = new Label("Cart Total: " + parseInt(shop.getCartTotal()));
        ui.elements.Button destroyButton = new Button("x", this, "destroy");
        Button purchaseButton = new Button("Purchase Cart", this, "purchase");
        panel.add(total);
        panel.add(destroyButton);
        panel.add(purchaseButton);

        this.topBar = panel;
        this.add(panel);
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

    // EFFECTS: Generates the list of cards that display cart
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

    // EFFECTS: Receives an ActionEvent and executes a function depending on what the command is
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("destroy")) {
            shop.destroyCart();
            this.refresh();
        } else if (e.getActionCommand().equals("purchase")) {
            makePurchase();
        }
    }

    // EFFECTS: Makes a purchase
    private void makePurchase() {
        if (shop.getCart().isEmpty()) {
            new ShowErrorMessage().sayError("Cart Was Empty");
        } else {
            try {
                Receipt receipt = shop.makePurchase();
                System.out.println(receipt);
                refreshable.refresh();
            } catch (NotEnoughInventory e) {
                new ShowErrorMessage().sayError(e.errorMessage());
            } catch (NotPositiveInteger e) {
                shop.destroyCart();
                new ShowErrorMessage().sayError("An Unrecoverable error has occurred");
            }
        }
    }

    // EFFECTS: Refreshes this
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

