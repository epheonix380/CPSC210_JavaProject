package ui.panels;

import model.shop.Shop;
import ui.Frame;
import ui.Refreshable;
import ui.elements.Label;
import ui.subpanels.CartSubPanel;
import ui.subpanels.CatalogueSubPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainAppPanel extends JPanel implements Refreshable {

    private boolean isInventory;
    private List<Refreshable> panels;

    public MainAppPanel(Frame frame, Shop shop, boolean isInventory) {
        System.out.println("checkpoint 1.5");
        this.panels = new ArrayList<>();
        this.isInventory = isInventory;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        CatalogueSubPanel catalogueSubPanel = new CatalogueSubPanel(frame, shop, this);
        this.panels.add(catalogueSubPanel);
        this.add(catalogueSubPanel);
        System.out.println("checkpoint 10");
        CartSubPanel cartSubPanel = new CartSubPanel(frame, shop, this);
        System.out.println("Checkpoint 20");
        this.panels.add(cartSubPanel);
        this.add(cartSubPanel);
    }

    @Override
    public void refresh() {
        for (Refreshable panel : panels) {
            panel.refresh();
        }
    }

    public void displayShop() {

    }
}
