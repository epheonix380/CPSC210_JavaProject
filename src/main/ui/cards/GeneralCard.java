package ui.cards;

import ui.Frame;
import ui.elements.Button;
import ui.elements.Label;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class GeneralCard extends JPanel {

    private LayoutManager layoutManager;
    protected Frame frame;
    protected JPanel panel;

    public GeneralCard(Frame frame) {
        LayoutManager layoutManager = new FlowLayout();
        this.layoutManager = layoutManager;
        this.frame = frame;
        this.panel = panel;
        this.setLayout(layoutManager);
    }

    public void init(List<String> labelNames, List<Component> componentList) {
        this.removeAll();
        this.add(createLabelStack(labelNames));
        this.add(createButtonStack(componentList));
        frame.update();
    }

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

