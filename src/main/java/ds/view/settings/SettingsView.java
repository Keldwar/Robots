package ds.view.settings;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SettingsView extends JPanel {
    public SettingsView(ActionListener actionListener) {
        JSlider amountOfBacteria = new JSlider(JSlider.HORIZONTAL,
                0, 100, 20);
        amountOfBacteria.setMajorTickSpacing(10);
        amountOfBacteria.setPaintTicks(true);
        this.add(amountOfBacteria);

        JSlider amountOfTargets = new JSlider(JSlider.HORIZONTAL,
                0, 100, 20);
        amountOfTargets.setMajorTickSpacing(10);
        amountOfTargets.setPaintTicks(true);
        this.add(amountOfTargets);

        JButton startButton = new JButton("Старт");
        startButton.setActionCommand("Start");
        startButton.addActionListener(actionListener);
        this.add(startButton);

    }
}
