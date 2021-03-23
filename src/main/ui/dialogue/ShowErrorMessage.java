package ui.dialogue;

import javax.swing.*;
import java.awt.*;

public class ShowErrorMessage extends JOptionPane {

    JFrame frame;

    public ShowErrorMessage() {
        this.frame = new JFrame();
    }

    public void sayError(String message) {
        Label label = new Label(message);
        this.setMessage(label);
        JDialog dialog = this.createDialog(frame, "Error");
        dialog.setVisible(true);
    }
}
