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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Represents the SubPanel that displays the catalogue of items inside of the main Panel
public class CatalogueSubPanel extends JPanel implements ActionListener, Refreshable {

    private Frame frame;
    private Shop shop;
    private Refreshable refreshable;
    private List<CatalogueCard> componentList;
    private JScrollPane scrollPane;
    private TextField textField;

    // MODIFIES: This
    // EFFECTS: frame is the Frame in which this is contained
    //          shop is the Shop whose catalogue this is displaying
    //          refreshable is a refreshable parent
    public CatalogueSubPanel(Frame frame, Shop shop, Refreshable refreshable) {
        this.refreshable = refreshable;
        this.frame = frame;
        this.shop = shop;
        this.textField = new TextField();
        textField.addActionListener(this);
        this.componentList = new ArrayList<>();
        LayoutManager layoutManager = new BoxLayout(this,BoxLayout.Y_AXIS);
        this.setLayout(layoutManager);
        generateTopBar();
        generateCards();
        Label search = new Label("Search Store:");
        this.add(search);
        this.add(textField);
        this.add(scrollPane);
    }

    // MODIFIES: This
    // EFFECTS: Creates a panel that will become the topbar
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

    // EFFECTS: Generates the list of cards that display cart
    private void generateCards() {
        this.componentList = new ArrayList<>();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Map<String, NIStock> catalogue = shop.getCatalogue();
        for (Map.Entry<String,NIStock> entry : catalogue.entrySet()) {
            NIStock stock = entry.getValue();
            CatalogueCard card = new CatalogueCard(frame, refreshable, stock, shop);
            this.componentList.add(card);
            panel.add(card);
        }

        this.scrollPane = new JScrollPane(panel);
    }

    // EFFECTS: Searches all NIStock in catalogue for term and returns all matches
    private void search(String term) {
        this.remove(scrollPane);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        int i = 0;
        for (CatalogueCard card : componentList) {
            if (card.stock.getName().contains(term)) {
                panel.add(card);
                i++;
            }

        }
        this.scrollPane = new JScrollPane(panel);
        this.add(scrollPane);
        frame.update();
    }

    // EFFECTS: Receives an ActionEvent and executes a function depending on what the command is
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            createNIStock();
        } else {
            search(textField.getText());
        }
    }

    // EFFECTS: Creates a new NIStock to add to the catalogue
    private void createNIStock() {
        NonInventoryStockEntry entry = new NonInventoryStockEntry();
        Map<String, String> itemMap = entry.getString();
        if (!itemMap.isEmpty()) {
            try {
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
            } catch (NullPointerException e) {
                //
            }
        }
        refreshable.refresh();
    }

    // EFFECTS: Refreshes this
    @Override
    public void refresh() {
        this.remove(scrollPane);
        generateCards();
        this.add(scrollPane);
        frame.update();
    }
}
