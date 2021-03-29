package ui.dialogue;

import javax.swing.*;
import java.awt.*;

// Represents a dialogue window with an error message
public class ShowErrorMessage extends JOptionPane {

    JFrame frame;

    // MODIFIES: This
    // EFFECTS: frame represents the JFrame in which this is contained
    public ShowErrorMessage() {
        this.frame = new JFrame();
        frame.setMaximumSize(new Dimension(600, 800));
    }

    // EFFECTS: Displays message as the error in a popup window
    public void sayError(String message) {
        Label label = new Label(message);
        this.setMessage(label);
        JDialog dialog = this.createDialog(frame, "Alert");
        dialog.setVisible(true);
    }
}
