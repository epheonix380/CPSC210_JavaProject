package ui.panels;

import model.shop.Shop;
import ui.Frame;
import ui.Refreshable;
import ui.elements.Button;
import ui.elements.Label;
import ui.subpanels.CartSubPanel;
import ui.subpanels.CatalogueSubPanel;
import ui.subpanels.InventorySubPanel;
import ui.subpanels.TransactionsSubPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Represents a refreshable panel that contains refreshable children
public class MainAppPanel extends JPanel implements Refreshable {

    private boolean isInventory;
    private List<Refreshable> panels;
    private Frame frame;
    private Shop shop;

    // MODIFIES: This
    // EFFECTS: frame is the Frame in which this is contained
    //          shop is the Shop that this is displaying
    //          isInventory is whether the Shop that this is displaying is an inventory shop
    public MainAppPanel(Frame frame, Shop shop, boolean isInventory) {
        this.frame = frame;
        this.shop = shop;
        this.panels = new ArrayList<>();
        this.isInventory = isInventory;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        CatalogueSubPanel catalogueSubPanel = new CatalogueSubPanel(frame, shop, this);
        this.panels.add(catalogueSubPanel);
        this.add(catalogueSubPanel);
        CartSubPanel cartSubPanel = new CartSubPanel(frame, shop, this);
        this.panels.add(cartSubPanel);
        this.add(cartSubPanel);
        if (isInventory) {
            InventorySubPanel inventorySubPanel  = new InventorySubPanel(frame, shop, this);
            this.panels.add(inventorySubPanel);
            this.add(inventorySubPanel);
        }
        TransactionsSubPanel transactionsSubPanel = new TransactionsSubPanel(frame, shop, this);
        this.panels.add(transactionsSubPanel);
        this.add(transactionsSubPanel);
        frame.update();
    }

    // EFFECTS: Refreshes all the children of this
    @Override
    public void refresh() {
        for (Refreshable panel : panels) {
            panel.refresh();
        }
    }


}
