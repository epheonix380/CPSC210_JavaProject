package ui.elements;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionListener;

// Represents a Button that is stylized to match the rest of the application
public class Button extends JButton {

    // MODIFIES: This
    // EFFECTS: text is the text that will be displayed on the button
    //          listener represents the ActionListener that is listening for this button press
    //          command represents the command that will return with ActionEvent to ActionListener
    public Button(String text, ActionListener listener, String command) {
        super(text);
        this.setBackground(new Color(0xFFFFFF));
        this.setBorderPainted(false);
        this.setActionCommand(command);
        this.addActionListener(listener);
    }
}
