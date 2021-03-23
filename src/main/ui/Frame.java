package ui;

import ui.dialogue.ShowErrorMessage;
import ui.panels.SelectShopPanel;
import ui.panels.StartPanel;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private ShowErrorMessage errorMessage;

    public Frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("POS App");
        this.pack();
        this.setVisible(true);
        this.setMinimumSize(new Dimension(800,600));
        this.add(new StartPanel(this));
        this.errorMessage = new ShowErrorMessage();
    }

    public void showToUser() {
        this.setVisible(true);
    }

    public void sayErrorMessage(String message) {
        this.errorMessage.sayError(message);
    }

    public void update() {
        SwingUtilities.updateComponentTreeUI(this);
    }


}
