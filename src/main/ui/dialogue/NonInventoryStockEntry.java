package ui.dialogue;

import model.stock.NIStock;
import ui.Frame;
import ui.elements.Label;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

// Represents a data entry window for a NonInventoryStock
public class NonInventoryStockEntry extends JOptionPane {

    private JFrame frame;

    // MODIFIES: This
    // EFFECTS: frame represents the JFrame in which this is contained
    public NonInventoryStockEntry() {
        this.frame = new JFrame();
        frame.setMaximumSize(new Dimension(600, 800));

    }

    // EFFECTS: Creates a data entry window with values from stock as default values
    //          returns the entered values if user selects "Ok"
    //          else returns an empty HashMap
    public Map<String, String> getString(NIStock stock) {
        Map<String, String> resultMap = new HashMap<>();

        JTextField nameText = new JTextField(stock.getName());
        JTextField priceText = new JTextField(String.valueOf(stock.getPrice()));
        JTextField costText = new JTextField(String.valueOf(stock.getUnitCost()));

        Label nameLabel = new Label("Name of Item:");
        Label priceLabel = new Label("Price of Item:");
        Label costLabel = new Label("Cost of Item:");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(nameLabel);
        panel.add(nameText);

        panel.add(priceLabel);
        panel.add(priceText);

        panel.add(costLabel);
        panel.add(costText);

        int result = this.showConfirmDialog(null, panel,
                "Add/Edit Menu", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            resultMap.put("name", nameText.getText());
            resultMap.put("price", priceText.getText());
            resultMap.put("cost", costText.getText());
        }

        return resultMap;
    }

    // EFFECTS: Creates a data entry window with empty values
    //          returns the entered values if user selects "Ok"
    //          else returns an empty HashMap
    public Map<String, String> getString() {
        Map<String, String> resultMap = new HashMap<>();

        JTextField nameText = new JTextField();
        JTextField priceText = new JTextField();
        JTextField costText = new JTextField();

        Label nameLabel = new Label("Name of Item:");
        Label priceLabel = new Label("Price of Item:");
        Label costLabel = new Label("Cost of Item:");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(nameLabel);
        panel.add(nameText);

        panel.add(priceLabel);
        panel.add(priceText);

        panel.add(costLabel);
        panel.add(costText);

        int result = this.showConfirmDialog(null, panel,
                "Add/Edit Menu", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            resultMap.put("name", nameText.getText());
            resultMap.put("price", priceText.getText());
            resultMap.put("cost", costText.getText());
        }

        return resultMap;
    }


}
