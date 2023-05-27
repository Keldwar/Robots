package ds;

import com.formdev.flatlaf.FlatLightLaf;
import ds.bus.GameEventBus;
import ds.model.GameModel;
import ds.view.MainApplicationFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameModel gameModel = new GameModel();
        GameEventBus gameEventBus = new GameEventBus();

        SwingUtilities.invokeLater(() -> {
            MainApplicationFrame frame = new MainApplicationFrame(gameModel, gameEventBus);
            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
