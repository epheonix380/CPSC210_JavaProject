package ui.subpanels;

import exceptions.ItemAlreadyExists;
import exceptions.NotPositiveInteger;
import model.shop.Shop;
import model.stock.NIStock;
import ui.Frame;
import ui.Refreshable;
import ui.cards.CatalogueCard;
import ui.dialogue.NonInventoryStockEntry;
import ui.elements.Button;
import ui.elements.Label;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class CatalogueSubPanel extends JPanel implements ActionListener, Refreshable {

    private Frame frame;
    private Shop shop;
    private JScrollPane cards;
    private Refreshable refreshable;

    public CatalogueSubPanel(Frame frame, Shop shop, Refreshable refreshable) {
        this.refreshable = refreshable;
        this.frame = frame;
        this.shop = shop;
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
        Label label = new Label("Catalogue of Store");
        Button button = new Button("+", this, "add");

        panel.add(label);
        panel.add(button);

        this.add(panel);
    }

    private JScrollPane generateCards() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Map<String, NIStock> catalogue = shop.getCatalogue();
        for (Map.Entry<String,NIStock> entry : catalogue.entrySet()) {
            NIStock stock = entry.getValue();
            CatalogueCard card = new CatalogueCard(frame, refreshable, stock, shop);
            panel.add(card);
        }

        return new JScrollPane(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            createNIStock();
        }
    }

    private void createNIStock() {
        NonInventoryStockEntry entry = new NonInventoryStockEntry();
        Map<String, String> itemMap = entry.getString();
        if (!itemMap.isEmpty()) {
            String name = itemMap.get("name");
            int price = Integer.parseInt(itemMap.get("price"));
            int cost = Integer.parseInt(itemMap.get("cost"));
            try {
                shop.addToCatalogue(name, price, cost);
                refresh();
            } catch (NotPositiveInteger e) {
                frame.sayErrorMessage(e.getMessage());
            } catch (ItemAlreadyExists e) {
                frame.sayErrorMessage(e.getMessage());
            }
        }
    }

    @Override
    public void refresh() {
        this.remove(this.cards);
        this.cards = generateCards();
        this.add(cards);
        frame.update();
    }
}
