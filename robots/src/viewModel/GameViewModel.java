package viewModel;

import View.GameView;
import model.GameModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;


public class GameViewModel {
    private GameModel gameModel;
    private GameView gameView;
    private final java.util.Timer m_timer = initTimer();

    private static java.util.Timer initTimer() {
        return new Timer("events generator", true);
    }

    public GameViewModel(GameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameView.updateView();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameModel.updateModel();
            }
        }, 0, 10);
        gameView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameModel.setTargetPosition(e.getPoint());
                gameView.repaint();
            }
        });
    }

    public GameView getGameView() {
        return gameView;
    }
}
