package ds.view;

import ds.bus.GameEventBus;
import ds.model.GameModel;
import ds.view.game.GameWindow;
import ds.log.LogWindow;
import ds.log.Logger;
import ds.view.settings.SettingsWindow;
import ds.viewModel.game.GameWindowViewModel;
import ds.viewModel.settings.SettingsWindowViewModel;

import javax.swing.*;
import java.awt.*;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();

    public MainApplicationFrame(GameModel gameModel, GameEventBus gameEventBus) {

        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

        setContentPane(desktopPane);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindowViewModel(gameModel).getGameWindow();
        addWindow(gameWindow);

        SettingsWindow settingsWindow = new SettingsWindowViewModel(gameModel, gameEventBus).getSettingsWindow();
        addWindow(settingsWindow);

        setJMenuBar(new MenuCreator(this).generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}
