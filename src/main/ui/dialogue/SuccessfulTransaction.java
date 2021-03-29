package ui.dialogue;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Receipt;
import model.stock.InventoryStock;
import sun.audio.AudioStream;
import ui.cards.ReceiptItemCard;
import ui.elements.Label;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

// Represents a successful transaction that has taken place
public class SuccessfulTransaction extends JOptionPane {

    private JFrame frame;
    private Receipt receipt;

    // MODIFIES: This
    // EFFECTS: Opens a window displaying an image to show that a successful transaction has taken place
    //          Additionally plays a sound to show that a successful transaction has taken place
    public SuccessfulTransaction(Receipt receipt) {
        this.receipt = receipt;
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
        playAudio();
        String time = "Time: " + receipt.getDateTime().toString();
        String total = "Total: " + parseInt(receipt.getTotal());
        Label timeLabel = new Label(time);
        Label totalLabel = new Label(total);
        panel.add(timeLabel);
        panel.add(totalLabel);
        panel.add(generateCards());
        int result = this.showConfirmDialog(null, panel,
                "Receipt", JOptionPane.OK_CANCEL_OPTION);

    }

    private void playAudio() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("data/assets/sound.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            //
        }
    }

    private JScrollPane generateCards() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Map<String, InventoryStock> receiptItems = receipt.getItems();
        for (Map.Entry<String,InventoryStock> entry : receiptItems.entrySet()) {
            InventoryStock stock = entry.getValue();
            ReceiptItemCard card = new ReceiptItemCard(frame, stock);
            panel.add(card);
        }

        return new JScrollPane(panel);
    }

    private String parseInt(int integer) {
        String stringValue = String.valueOf(integer);
        String value;
        if (stringValue.length() >= 2) {
            int decimalPoint2 = stringValue.length() - 2;
            value = "$" + stringValue.substring(0, decimalPoint2) + "." + stringValue.substring(decimalPoint2);
        } else {
            value = stringValue;
            for (int i = 0; i < 2 - stringValue.length(); i++) {
                value = "0" + value;
            }
            value = "$0." + value;
        }
        return value;
    }
}
