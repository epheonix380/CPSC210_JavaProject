package ui.subpanels;

import exceptions.NotEnoughInventory;
import exceptions.NotPositiveInteger;
import model.Receipt;
import model.shop.Shop;
import model.stock.InventoryStock;
import ui.Frame;
import ui.Refreshable;
import ui.cards.CartCard;
import ui.cards.ReceiptCard;
import ui.dialogue.ShowErrorMessage;
import ui.elements.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

// Represents the SubPanel that displays all transactions inside of the main Panel
public class TransactionsSubPanel extends JPanel implements Refreshable {

    private Frame frame;
    private Shop shop;
    private JScrollPane cards;
    private JPanel topBar;
    private Refreshable refreshable;

    // MODIFIES: This
    // EFFECTS: frame is the Frame in which this is contained
    //          shop is the Shop that the cart is contained in
    //          refreshable is the parent that is refreshable
    public TransactionsSubPanel(Frame frame, Shop shop, Refreshable refreshable) {
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
        ui.elements.Label total = new ui.elements.Label("Transactions");
        panel.add(total);

        this.topBar = panel;
        this.add(panel);
    }

    // EFFECTS: Generates the list of cards that display cart
    private JScrollPane generateCards() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        List<Receipt> receipts = shop.getRecords();
        for (int i = receipts.size() - 1; i >= 0; i--) {
            Receipt receipt = receipts.get(i);
            ReceiptCard card = new ReceiptCard(frame, refreshable, receipt, shop);
            panel.add(card);
        }

        return new JScrollPane(panel);
    }

    // EFFECTS: Refreshes this
    @Override
    public void refresh() {
        this.remove(this.cards);
        this.cards = generateCards();
        this.add(cards);
        frame.update();
    }
}



