package ui.cards;

import ui.Frame;
import ui.elements.Button;
import ui.elements.Label;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

// Represents a Card that displays information contained in a Frame
public abstract class FrameCard extends JPanel {

    private LayoutManager layoutManager;
    protected Frame frame;
    protected JPanel panel;

    // MODIFIES: This
    // EFFECTS: Frame is the window that contains this
    public FrameCard(Frame frame) {
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
        frame.update();
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

