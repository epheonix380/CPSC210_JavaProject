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

public class SelectShopPanel extends JPanel implements ActionListener {

    static List<String> modes = new ArrayList<String>() {{
            add("Load");
            add("Delete");
        }};

    private Index index;
    private Frame frame;
    private JTextField textField;
    private BoxLayout manager;
    private int mode;

    public SelectShopPanel(Frame frame, int mode) {
        this.index = new Index();
        this.mode = mode;
        this.frame = frame;
        this.manager = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(manager);
        Button button1 = new Button("Back", this, "BACK");
        this.add(button1);
        addAllShops();
        textField = new JTextField();
        this.add(textField);
        Button button2 = new Button(modes.get(mode), this, modes.get(mode));
        this.add(button2);
        frame.pack();
        System.out.println("Test");
        frame.update();
    }

    private void addAllShops() {
        try {
            List<String> shopNames = index.getIndex();
            for (String shop : shopNames) {
                Label label = new Label(shop);
                this.add(label);
                System.out.println(shop);
            }
        } catch (IOException e) {
            errorDuringLoad("An error has occurred while trying to read the index");
        }
    }

    private void errorDuringLoad(String message) {
        exit();
        frame.sayErrorMessage(message);
    }

    private void exit() {
        StartPanel startPanel = new StartPanel(frame);
        frame.add(startPanel);
        frame.remove(this);
    }

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

    private void showShop(Shop shop, String type) {
        System.out.println("checkpoint 1");
        boolean isInventory = type.equals("inventory");
        MainAppPanel main = new MainAppPanel(frame, shop, isInventory);
        frame.add(main);
        frame.remove(this);
        frame.update();
        System.out.println("checkpoint 2");
    }

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
