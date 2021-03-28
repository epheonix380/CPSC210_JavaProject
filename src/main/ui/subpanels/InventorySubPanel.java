package ui.subpanels;

import model.shop.InventoryShop;
import model.shop.Shop;
import model.stock.InventoryStock;
import model.stock.NIStock;
import ui.Frame;
import ui.Refreshable;
import ui.cards.CatalogueCard;
import ui.cards.InventoryCard;
import ui.elements.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

// Represents the SubPanel that displays the inventory inside of the main Panel
public class InventorySubPanel extends JPanel implements Refreshable {

    private Frame frame;
    private InventoryShop shop;
    private JScrollPane cards;
    private Refreshable refreshable;

    // MODIFIES: This
    // EFFECTS: frame is the Frame in which this is contained
    //          shop is the Shop that the cart is contained in
    //          refreshable is the parent that is refreshable
    public InventorySubPanel(Frame frame, Shop shop, Refreshable refreshable) {
        this.refreshable = refreshable;
        this.frame = frame;
        this.shop = (InventoryShop) shop;
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
        ui.elements.Label label = new ui.elements.Label("Inventory of Store");

        panel.add(label);

        this.add(panel);
    }

    // EFFECTS: Generates the list of cards that display cart
    private JScrollPane generateCards() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Map<String, InventoryStock> catalogue = shop.getInventoryMap();
        for (Map.Entry<String,InventoryStock> entry : catalogue.entrySet()) {
            InventoryStock stock = entry.getValue();
            InventoryCard card = new InventoryCard(frame, refreshable, shop, stock);
            panel.add(card);
        }

        return new JScrollPane(panel);
    }

    // EFFECTS: Refreshes this
    @Override
    public void refresh() {
        this.remove(cards);
        JScrollPane scrollPane = generateCards();
        this.add(scrollPane);
        cards = scrollPane;
    }
}
