package ui.dialogue;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Receipt;
import sun.audio.AudioStream;
import ui.elements.Label;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

// Represents a successful transaction that has taken place
public class SuccessfulTransaction extends JOptionPane {

    private JFrame frame;

    // MODIFIES: This
    // EFFECTS: Opens a window displaying an image to show that a successful transaction has taken place
    //          Additionally plays a sound to show that a successful transaction has taken place
    public SuccessfulTransaction() {
        this.frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        try {
            BufferedImage img = ImageIO.read(new File("data/assets/money.png"));
            ImageIcon icon = new ImageIcon(img);
            JLabel label = new JLabel(icon);
            panel.add(label);
        } catch (Exception e) {
            Label label = new Label("There was a problem loading the image :(");
            panel.add(label);
        }
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("data/assets/sound.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            //
        }
        int result = this.showConfirmDialog(null, panel,
                "Receipt", JOptionPane.CANCEL_OPTION);

    }
}
