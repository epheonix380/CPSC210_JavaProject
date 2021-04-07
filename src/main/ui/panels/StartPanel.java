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

// Represents the first panel that the user will see and interact with
public class StartPanel extends JPanel implements ActionListener {
/*
    private static Map<String, String> buttonMap = new LinkedHashMap<String, String>() {{
            put("Inventory Shop", "INVSHOP");
            put("Non Inventory Shop", "NONINVSHOP");
            put("Load Shop", "LOAD");
            put("Delete Shop", "DEL");
            put("Save and Quit", "QUIT");
        }};
*/
    private Frame frame;
    private GridBagLayout manager;
    private StringEntry stringEntry;
    private Index index = new Index();

    // MODIFIES: This
    // EFFECTS: frame represents the Frame in which this is contained
    public StartPanel(Frame frame) {
        this.frame = frame;
        this.stringEntry = new StringEntry();
        this.manager = new GridBagLayout();
        this.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        this.setLayout(manager);
        generateButtons();
        frame.update();


    }

    private void generateButtons() {
        Button button1 = new Button("Inventory Shop", this, "INVSHOP");
        Button button2 = new Button("Non Inventory Shop", this, "NONINVSHOP");
        Button button3 = new Button("Load Shop", this,"LOAD");
        Button button4 = new Button("Delete Shop", this, "DEL");
        Button button5 = new Button("Save and Quit", this, "QUIT");
        GridBagConstraints constraints = new GridBagConstraints(
                0, -1, 1, 1, 0, 0, 10, 0,
                new Insets(5, 0, 5, 0), 10, 10
        );

        this.add(button1);
        manager.setConstraints(button1, constraints);
        this.add(button2);
        manager.setConstraints(button2, constraints);
        this.add(button3);
        manager.setConstraints(button3, constraints);
        this.add(button4);
        manager.setConstraints(button4, constraints);
        this.add(button5);
        manager.setConstraints(button5, constraints);

    }

    // EFFECTS: Receives an ActionEvent and executes a function depending on what the command is
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
            frame.dispose();
        }
    }

    // MODIFIES: This
    // EFFECTS: opens the load menu
    private void load(String command) {
        SelectShopPanel selectShopPanel = new SelectShopPanel(frame, 0);
        this.setVisible(false);
        frame.add(selectShopPanel);
        frame.update();
    }

    // MODIFIES: This
    // EFFECTS: opens the delete menu
    private void delete(String command) {
        SelectShopPanel selectShopPanel = new SelectShopPanel(frame, 1);
        this.setVisible(false);
        frame.add(selectShopPanel);
        frame.update();
    }

    // EFFECTS: opens the createNonInventoryShop dialogue and creates a NonInventoryShop with the given name
    private void createNonInventoryShop(String command) {
        String name = stringEntry.getString("Please enter the shop name");
        try {
            if (name.isEmpty()) {
                frame.sayErrorMessage("Cancelled: Name was not entered");
            } else if (index.contains(name)) {
                frame.sayErrorMessage("Cancelled: A file name " + name + " already exists");
            } else {
                NonInventoryShop shop = new NonInventoryShop(name);
                MainAppPanel mainApp = new MainAppPanel(frame,shop,false);
                this.setVisible(false);
                frame.add(mainApp);
                frame.pack();
            }
        } catch (IOException e) {
            frame.sayErrorMessage("An error has occurred while trying to read the index");
        } catch (NullPointerException e) {
            frame.pack();
        }
    }

    // EFFECTS: opens the createInventoryShop dialogue and creates an InventoryShop with the given name
    private void createInventoryShop(String command) {
        String name = stringEntry.getString("Please enter the shop name");
        try {
            if (name.isEmpty()) {
                frame.sayErrorMessage("Cancelled: Name was not entered");
            } else if (index.contains(name)) {
                frame.sayErrorMessage("Cancelled: The file name " + name + " already exists");
            } else {
                InventoryShop shop = new InventoryShop(name);
                MainAppPanel mainApp = new MainAppPanel(frame,shop,true);
                this.setVisible(false);
                frame.add(mainApp);
                frame.pack();
            }
        } catch (IOException e) {
            frame.sayErrorMessage("An error has occurred while trying to read the index");
        } catch (NullPointerException e) {
            frame.pack();
        }
    }
}
