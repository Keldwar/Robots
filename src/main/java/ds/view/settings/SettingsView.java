package ds.view.settings;

import ds.model.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingsView extends JPanel {
    private final JSlider amountOfBacteriaSlider;
    private final JSlider amountOfTargetsSlider;
    private final JSlider minBacteria;

    public SettingsView(ActionListener actionListener) {
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        amountOfBacteriaSlider = new JSlider(JSlider.HORIZONTAL,
                1, 100, 20);
        amountOfBacteriaSlider.setMajorTickSpacing(10);
        amountOfBacteriaSlider.setPaintTicks(true);
        this.add(amountOfBacteriaSlider, gbc);


        gbc.gridx = 1;
        gbc.gridy = 0;

        amountOfTargetsSlider = new JSlider(JSlider.HORIZONTAL,
                0, 100, 20);
        amountOfTargetsSlider.setMajorTickSpacing(10);
        amountOfTargetsSlider.setPaintTicks(true);
        this.add(amountOfTargetsSlider, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        JButton startButton = new JButton("Старт");
        startButton.setActionCommand("Start");
        startButton.addActionListener(actionListener);
        this.add(startButton, gbc);

        minBacteria = new JSlider(JSlider.HORIZONTAL, 0, 100, 18);
        minBacteria.setMajorTickSpacing(10);
        minBacteria.setPaintTicks(true);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        this.add(minBacteria, gbc);
    }

    public Settings getSettings() {
        return new Settings(amountOfBacteriaSlider.getValue(), amountOfTargetsSlider.getValue(),
                minBacteria.getValue());
    }
}
