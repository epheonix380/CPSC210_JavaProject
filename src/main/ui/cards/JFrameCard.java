package ui.cards;

import ui.Frame;
import ui.elements.Label;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.util.List;

// Represents a card that is displayed in a JFrame
public abstract class JFrameCard extends JPanel {

    private LayoutManager layoutManager;
    protected JFrame frame;
    protected JPanel panel;

    // MODIFIES: This
    // EFFECTS: Frame is the window that contains this
    public JFrameCard(JFrame frame) {
        LayoutManager layoutManager = new FlowLayout();
        this.setBorder(new LineBorder(new Color(0x000000)));
        this.layoutManager = layoutManager;
        this.frame = frame;
        this.panel = panel;
        this.setLayout(layoutManager);
    }

    // MODIFIES: This
    // EFFECTS: Adds given labelNames as labels to this, then adds given componentList as components
    //          to this, then updates the frame
    public void init(List<String> labelNames, List<Component> componentList) {
        this.removeAll();
        this.add(createLabelStack(labelNames));
        this.add(createButtonStack(componentList));
        SwingUtilities.updateComponentTreeUI(frame);
    }

    // EFFECTS: Goes through labelNames, creates a label for each name and add it to a panel, then returns panel
    private JPanel createLabelStack(List<String> labelNames) {
        JPanel panel = new JPanel();
        LayoutManager layoutManager = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layoutManager);
        for (String labelName : labelNames) {
            Label label = new Label(labelName);
            panel.add(label);
        }
        return panel;
    }

    // MODIFIES: This
    // EFFECTS: Adds all components in componentList to a panel then returns it
    private JPanel createButtonStack(List<Component> componentList) {
        JPanel panel = new JPanel();
        LayoutManager layoutManager = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layoutManager);
        for (Component component : componentList) {
            panel.add(component);
        }
        return panel;
    }

}

