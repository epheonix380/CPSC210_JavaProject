package ui.panels;

import model.shop.InventoryShop;
import model.shop.NonInventoryShop;
import persistence.Index;
import ui.Frame;
import ui.dialogue.ShowErrorMessage;
import ui.dialogue.StringEntry;
import ui.elements.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class StartPanel extends JPanel implements ActionListener {

    private static Map<String, String> buttonMap = new LinkedHashMap<String, String>() {{
            put("Inventory Shop", "INVSHOP");
            put("Non Inventory Shop", "NONINVSHOP");
            put("Load Shop", "LOAD");
            put("Delete Shop", "DEL");
            put("Save and Quit", "QUIT");
        }};

    private Frame frame;
    private GridBagLayout manager;
    private StringEntry stringEntry;
    private ShowErrorMessage showErrorMessage;
    private Index index = new Index();

    public StartPanel(Frame frame) {
        this.frame = frame;
        this.stringEntry = new StringEntry();
        this.manager = new GridBagLayout();
        this.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        this.setLayout(manager);

        int count = 0;

        for (String key : buttonMap.keySet()) {
            String buttonText = key;
            System.out.println(buttonText);
            String buttonCommand = buttonMap.get(key);
            Button button = new Button(buttonText, this, buttonCommand);
            this.add(button);

            GridBagConstraints constraints = new GridBagConstraints(
                    0, -1, 1, 1, 0, 0, 10, 0,
                    new Insets(5, 0, 5, 0), 10, 10
            );
            manager.setConstraints(button, constraints);
            count += 1;
        }
        frame.update();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("INVSHOP")) {
            createInventoryShop(command);
        } else if (command.equals("NONINVSHOP")) {
            createNonInventoryShop(command);
        } else if (command.equals("LOAD")) {
            load(command);
        } else if (command.equals("DEL")) {
            delete(command);
        } else if (command.equals("QUIT")) {
            System.out.println("Quitting");
        }
    }

    private void load(String command) {
        SelectShopPanel selectShopPanel = new SelectShopPanel(frame, 0);
        frame.remove(this);
        frame.add(selectShopPanel);
        frame.update();
    }

    private void delete(String command) {
        SelectShopPanel selectShopPanel = new SelectShopPanel(frame, 1);
        frame.remove(this);
        frame.add(selectShopPanel);
        frame.update();
    }

    private void createNonInventoryShop(String command) {
        String name = stringEntry.getString("Please enter the shop name");
        try {
            if (name.isEmpty()) {
                frame.sayErrorMessage("Cancelled: Name was not entered");
            } else if (index.contains(name)) {
                frame.sayErrorMessage("Cancelled: A file name " + name + " already exists");
            } else {
                NonInventoryShop shop = new NonInventoryShop(command);
                MainAppPanel mainApp = new MainAppPanel(frame,shop,true);
                frame.add(mainApp);
                frame.remove(this);
                frame.pack();
            }
        } catch (IOException e) {
            frame.sayErrorMessage("An error has occurred while trying to read the index");
        } catch (NullPointerException e) {
            frame.pack();
        }
    }

    private void createInventoryShop(String command) {
        String name = stringEntry.getString("Please enter the shop name");
        try {
            if (name.isEmpty()) {
                frame.sayErrorMessage("Cancelled: Name was not entered");
            } else if (index.contains(name)) {
                frame.sayErrorMessage("Cancelled: A file name " + name + " already exists");
            } else {
                InventoryShop shop = new InventoryShop(command);
                MainAppPanel mainApp = new MainAppPanel(frame,shop,true);
                frame.add(mainApp);
                frame.remove(this);
                frame.pack();
            }
        } catch (IOException e) {
            frame.sayErrorMessage("An error has occurred while trying to read the index");
        } catch (NullPointerException e) {
            frame.pack();
        }
    }
}
