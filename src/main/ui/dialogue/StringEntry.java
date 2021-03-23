package ui.dialogue;

import javax.swing.*;
import java.awt.*;

public class StringEntry extends JOptionPane {

    JFrame frame;

    public StringEntry() {
        frame = new JFrame();
        this.setBackground(new Color(0x39ff14));
    }

    public String getString(String question) {
        String answer = this.showInputDialog(frame,question);
        return answer;
    }

}