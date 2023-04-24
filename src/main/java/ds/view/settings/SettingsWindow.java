package ds.view.settings;

import javax.swing.*;
import java.awt.*;

public class SettingsWindow extends JInternalFrame {
    public SettingsWindow(SettingsView settingsView) {
        super("Настройки", true, true, true, true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(settingsView, BorderLayout.CENTER);
        getContentPane().add(panel);
        setLocation(830, 10);
        pack();
    }
}
