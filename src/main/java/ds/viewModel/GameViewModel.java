package ds.viewModel;

import ds.View.GameView;
import ds.View.GameWindow;
import ds.model.GameModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;


public class GameViewModel {
    private GameModel gameModel;
    private GameWindow gameWindow;
    private final java.util.Timer m_timer = initTimer();

    private static java.util.Timer initTimer() {
        return new Timer("events generator", true);
    }

    public GameViewModel(GameModel gameModel, GameWindow gameWindow) {
        this.gameModel = gameModel;
        this.gameWindow = gameWindow;

        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameModel.setDimension(gameWindow.getSize());
                getGameView().updateView();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameModel.updateModel();
            }
        }, 0, 10);
        gameWindow.getGameView().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameModel.setTargetPosition(e.getPoint());
                getGameView().repaint();
            }
        });
    }

    public GameView getGameView() {
        return gameWindow.getGameView();
    }
}
