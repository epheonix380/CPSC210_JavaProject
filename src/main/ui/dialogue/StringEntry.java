package ui.dialogue;

import javax.swing.*;
import java.awt.*;

// Represents a window through which a string should be entered
public class StringEntry extends JOptionPane {

    JFrame frame;

    // MODIFIES: This
    // EFFECTS: frame is the JFrame in which this is contained
    public StringEntry() {
        frame = new JFrame();
        frame.setMaximumSize(new Dimension(600, 800));
        this.setBackground(new Color(0x39ff14));
    }

    // EFFECTS: Opens the data entry window and returns the inputted value if "Ok" is pressed by the user
    public String getString(String question) {
        String answer = this.showInputDialog(frame,question);
        return answer;
    }

}