package ui;

import ui.dialogue.ShowErrorMessage;
import ui.elements.Button;
import ui.panels.SelectShopPanel;
import ui.panels.StartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Represents the frame that the main application is run in
public class Frame extends JFrame implements ActionListener {

    private ShowErrorMessage errorMessage;
    private List<Component> componentList = new ArrayList<>();

    // MODIFIES: This
    // EFFECTS: Creates Frame
    public Frame() {
        generateMenu();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("POS App");
        this.pack();
        this.setVisible(true);
        this.setMinimumSize(new Dimension(1000,600));
        this.add(new StartPanel(this));
        this.errorMessage = new ShowErrorMessage();
        this.update();
    }

    // EFFECTS: Shows a dialogue with message as the message
    public void sayErrorMessage(String message) {
        this.errorMessage.sayError(message);
    }

    // MODIFIES: This
    // EFFECTS: Updates the Frame to recognize new components
    //          additionally ensures that there are only 10 panels within the back memory
    public void update() {
        SwingUtilities.updateComponentTreeUI(this);
        for (int i = 10; i < componentList.size(); i++) {
            componentList.remove(0);
        }
    }

    // MODIFIES: This
    // EFFECTS: Generates the menu
    private void generateMenu() {
        JMenuBar menuBar = new JMenuBar();
        ui.elements.Button button1a = new ui.elements.Button("Back", this, "exit");
        ui.elements.Button button1b = new ui.elements.Button("Home", this, "home");
        ui.elements.Button button2a = new ui.elements.Button("Save", this, "save");
        ui.elements.Button button1c = new Button("Exit", this, "quit");
        JMenu menu1 = new JMenu("Navigate");
        JMenu menu2 = new JMenu("File");
        menu1.add(button1a);
        menu1.add(button1b);
        menu1.add(button1c);
        menu2.add(button2a);

        menuBar.add(menu1);
        menuBar.add(menu2);
        this.setJMenuBar(menuBar);
    }

    // EFFECTS: Receives an ActionEvent and executes a function depending on what the command is
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("exit")) {
            back();
        } else if (e.getActionCommand().equals("save")) {
            //
        } else if (e.getActionCommand().equals("quit")) {
            this.dispose();
        } else if (e.getActionCommand().equals("home")) {
            home();
        }
    }

    // MODIFIES: This
    // EFFECTS: Opens the start panel
    private void home() {
        StartPanel startPanel = new StartPanel(this);
        if (componentList.size() >= 1) {
            this.remove(componentList.get(componentList.size() - 1));
            this.add(startPanel);
            this.update();
        } else {
            this.add(startPanel);
            this.update();
        }
    }

    // MODIFIES: This
    // EFFECTS: Goes to the previous panel displayed
    private void back() {
        if (componentList.size() >= 2) {
            Component currComponent = componentList.get(componentList.size() - 1);
            componentList.remove(currComponent);
            this.remove(currComponent);
            Component prevComponent = componentList.get(componentList.size() - 1);
            prevComponent.setVisible(true);
            super.add(prevComponent);
            this.update();
        } else {
            this.sayErrorMessage("No More Backs");
        }
    }

    // MODIFIES: This
    // EFFECTS: adds a component to this
    @Override
    public Component add(Component component) {
        super.add(component);
        componentList.add(component);
        return component;
    }


}
