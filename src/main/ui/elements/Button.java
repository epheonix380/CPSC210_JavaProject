package ui.elements;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionListener;

public class Button extends JButton {

    public Button(String text, ActionListener listener, String command) {
        super(text);
        this.setBackground(new Color(0xFFFFFF));
        this.setBorderPainted(false);
        this.setActionCommand(command);
        this.addActionListener(listener);
    }
}
