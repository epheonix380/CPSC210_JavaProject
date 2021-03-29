package ui.panels;

import exceptions.BadlyFormattedShopFile;
import model.shop.Shop;
import persistence.Index;
import persistence.ShopJson;
import ui.Frame;
import ui.elements.Button;
import ui.elements.Label;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Represents a Panel in which a shop is to be selected
public class SelectShopPanel extends JPanel implements ActionListener {

    private List<String> modes = new ArrayList<String>();
    private Index index;
    private Frame frame;
    private JTextField textField;
    private BoxLayout manager;
    private int mode;

    // MODIFIES: This
    // EFFECTS: frame is the Frame in which this is contained
    //          mode is whether this is in delete or load mode, load is 0, delete is 1
    public SelectShopPanel(Frame frame, int mode) {
        this.modes.add("Load");
        this.modes.add("Delete");
        this.index = new Index();
        this.mode = mode;
        this.frame = frame;
        this.manager = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(manager);
        addAllShops();
        textField = new JTextField();
        this.add(textField);
        Button button2 = new Button(modes.get(mode), this, modes.get(mode));
        this.add(button2);
        frame.pack();
        frame.update();
    }

    // MODIFIES: This
    // EFFECTS: Adds all shops to a list to be displayed
    private void addAllShops() {
        try {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            List<String> shopNames = index.getIndex();
            for (String shop : shopNames) {
                Label label = new Label(shop);
                panel.add(label);
            }
            JScrollPane scrollPane = new JScrollPane(panel);
            this.add(scrollPane);
        } catch (IOException e) {
            errorDuringLoad("An error has occurred while trying to read the index");
        }
    }

    // EFFECTS: Displays the error message and exits the panel
    private void errorDuringLoad(String message) {
        exit();
        frame.sayErrorMessage(message);
    }

    // EFFECTS: Exits the panel and goes back to teh start panel
    private void exit() {
        StartPanel startPanel = new StartPanel(frame);
        frame.add(startPanel);
        frame.remove(this);
    }

    // EFFECTS: Loads a specified shop with name command
    private void load(String command) {
        try {
            String name = textField.getText();
            if (name.isEmpty()) {
                frame.sayErrorMessage("No Text in input field");
            } else {
                ShopJson shopJson = new ShopJson(name);
                Shop shop = shopJson.getShop();
                String type = shop.getShopType();
                showShop(shop, type);
            }
        } catch (IOException e) {
            errorDuringLoad("An error has occurred while trying to read the file");
        } catch (NullPointerException e) {
            frame.pack();
        } catch (BadlyFormattedShopFile e) {
            errorDuringLoad("The shop file was badly formatted");
        }
    }

    // EFFECTS: Deletes a specified shop named command
    private void delete(String command) {
        try {
            String name = textField.getText();
            if (name.isEmpty()) {
                frame.sayErrorMessage("No Text in input field");
            } else {
                index.delete(name);
                SelectShopPanel selectShopPanel = new SelectShopPanel(frame, mode);
                frame.add(selectShopPanel);
                frame.remove(this);
                frame.update();
            }
        } catch (IOException e) {
            errorDuringLoad("An error has occurred while trying to read the file");
        } catch (NullPointerException e) {
            frame.sayErrorMessage("The specified shop was not found");
        }
    }

    // EFFECTS: Replaces this with MainAppPanel to display selected shop
    private void showShop(Shop shop, String type) {
        boolean isInventory = type.equals("inventory");
        MainAppPanel main = new MainAppPanel(frame, shop, isInventory);
        this.setVisible(false);
        frame.add(main);
        frame.update();
    }

    // EFFECTS: Receives an ActionEvent and executes a function depending on what the command is
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Load")) {
            load(command);
        } else if (command.equals("Delete")) {
            delete(command);
        } else if (command.equals("BACK")) {
            exit();
        } else {
            frame.sayErrorMessage("An Unknown error has occcured");
        }
    }
}
