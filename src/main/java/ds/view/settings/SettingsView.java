package ds.view.settings;

import ds.bus.GameAction;
import ds.model.Settings;
import ds.view.game.Statistics;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingsView extends JPanel {
    private final JSlider amountOfBacteriaSlider;
    private final JSlider amountOfTargetsSlider;
    private final JSlider minBacteria;
    private final JLabel curAmountOfBacteriaLabel;
    private static final int defaultAmountOfBacteria = 20;
    private static final int defaultAmountOfTargets = 20;
    private static final int defaultMinBacteria = 18;
    private final JSlider amountOfBiomesSlider;
    private static final int defaultAmountOfBiomes = 5;

    public SettingsView(ActionListener actionListener) {
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel settingsTab = new JPanel();
        settingsTab.setLayout(new GridLayout(1, 2));

        JLabel amountOfBacteriaLabel = new JLabel("Количество бактерий", JLabel.CENTER);
        amountOfBacteriaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setBorder(new TitledBorder("Общие"));
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.add(amountOfBacteriaLabel);
        amountOfBacteriaSlider = new JSlider(JSlider.HORIZONTAL,
                1, 100, 20);
        amountOfBacteriaSlider.setMajorTickSpacing(10);
        amountOfBacteriaSlider.setPaintTicks(true);
        settingsPanel.add(amountOfBacteriaSlider);


        JLabel amountOfTargetsLabel = new JLabel("Количество целей", JLabel.CENTER);
        amountOfTargetsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsPanel.add(amountOfTargetsLabel);

        amountOfTargetsSlider = new JSlider(JSlider.HORIZONTAL,
                0, 100, 20);
        amountOfTargetsSlider.setMajorTickSpacing(10);
        amountOfTargetsSlider.setPaintTicks(true);
        settingsPanel.add(amountOfTargetsSlider);

        JLabel minBacteriaLabel = new JLabel("Минимально бактерий", JLabel.CENTER);
        minBacteriaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsPanel.add(minBacteriaLabel);
        minBacteria = new JSlider(JSlider.HORIZONTAL, 0, 100, defaultMinBacteria);
        minBacteria.setMajorTickSpacing(10);
        minBacteria.setPaintTicks(true);
        settingsPanel.add(minBacteria);

        settingsTab.add(settingsPanel);
        JPanel internalButtonsPanel = new JPanel();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        JButton startButton = new JButton("Старт");
        startButton.setActionCommand(GameAction.START.name());
        startButton.addActionListener(actionListener);
        internalButtonsPanel.add(startButton);

        JButton defaultButton = new JButton("Сбросить");
        defaultButton.setActionCommand(GameAction.DEFAULT.name());
        defaultButton.addActionListener(actionListener);
        internalButtonsPanel.add(defaultButton);
        buttonsPanel.setBorder(new TitledBorder("Другое"));
        buttonsPanel.add(internalButtonsPanel, BorderLayout.SOUTH);

        JLabel amountOfBiomesLabel = new JLabel("Количество биомов", JLabel.CENTER);
        amountOfBiomesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        amountOfBiomesSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
        amountOfBiomesSlider.setMajorTickSpacing(1);
        amountOfBiomesSlider.setPaintTicks(true);
        JPanel biomesPanel = new JPanel();
        biomesPanel.setLayout(new BoxLayout(biomesPanel, BoxLayout.Y_AXIS));
        biomesPanel.add(amountOfBiomesLabel);
        biomesPanel.add(amountOfBiomesSlider, BorderLayout.NORTH);
        buttonsPanel.add(biomesPanel, BorderLayout.NORTH);
        settingsTab.add(buttonsPanel);
        tabbedPane.addTab("Настройки", settingsTab);


        JPanel statisticsTab = new JPanel();
        JLabel cur = new JLabel("Бактерий живы", JLabel.LEFT);
        statisticsTab.add(cur);
        curAmountOfBacteriaLabel = new JLabel();
        statisticsTab.add(curAmountOfBacteriaLabel);

        tabbedPane.addTab("Статистика", statisticsTab);
        this.add(tabbedPane);

    }

    public Settings getSettings() {
        return new Settings(amountOfBacteriaSlider.getValue(), amountOfTargetsSlider.getValue(),
                minBacteria.getValue(), amountOfBiomesSlider.getValue());
    }

    public void setDefaultSettings() {
        amountOfBacteriaSlider.setValue(defaultAmountOfBacteria);
        amountOfTargetsSlider.setValue(defaultAmountOfTargets);
        minBacteria.setValue(defaultMinBacteria);
        amountOfBiomesSlider.setValue(defaultAmountOfBiomes);
    }

    public void setStatistics(Statistics statistics) {
        curAmountOfBacteriaLabel.setText(String.valueOf(statistics.curAmountOfBacteria()));
    }
}
