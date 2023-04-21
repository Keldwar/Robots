package ds.viewModel;

import ds.log.Logger;
import ds.view.GameView;
import ds.view.GameWindow;
import ds.model.GameModel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;


public class GameViewModel {
    private final GameModel gameModel;
    private final GameWindow gameWindow;
    private final java.util.Timer timer = initTimer();

    private static java.util.Timer initTimer() {
        return new Timer("events generator", true);
    }

    public GameViewModel(GameModel gameModel, GameWindow gameWindow) {
        this.gameModel = gameModel;
        this.gameWindow = gameWindow;
        initListeners();
    }

    private void initListeners() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameModel.setDimension(gameWindow.getGameView().getSize());
                getGameView().updateView();
            }
        }, 0, 50);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameModel.updateModel();
            }
        }, 0, 10);
        gameWindow.getGameView().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Logger.debug("Clicked at " + e.getPoint());
                gameModel.setTarget(e.getPoint());
                getGameView().repaint();
            }
        });
        gameWindow.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                super.componentResized(e);
                Logger.debug("Window resized to " + gameWindow.getSize());
                gameModel.setDimension((gameWindow.getSize()));
            }
        });
    }

    public GameView getGameView() {
        return gameWindow.getGameView();
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }
}
