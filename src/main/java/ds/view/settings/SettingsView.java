package ds.view.settings;

import ds.model.GameModel;

import javax.swing.*;
import java.util.Hashtable;

public class SettingsView extends JPanel {
    public SettingsView(GameModel gameModel) {
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
        this.add(startButton);
    }
}
