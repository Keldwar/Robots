package ds.viewModel.game;

import ds.log.Logger;
import ds.view.game.GameView;
import ds.model.GameModel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;


public class GameViewModel {
    private final GameModel gameModel;
    private final GameView gameView;
    private final java.util.Timer timer = initTimer();

    private static java.util.Timer initTimer() {
        return new Timer("events generator", true);
    }

    public GameViewModel(GameModel gameModel) {
        this.gameModel = gameModel;
        this.gameView = new GameView(gameModel);
        initListeners();
    }

    private void initListeners() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameModel.setDimension(gameView.getSize());
                getGameView().updateView();
            }
        }, 0, 50);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameModel.updateModel();
            }
        }, 0, 10);
        gameView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Logger.debug("Clicked at " + e.getPoint());
                gameModel.setTarget(e.getPoint());
                getGameView().repaint();
            }
        });
        gameView.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                super.componentResized(e);
                Logger.debug("Panel resized to " + gameView.getSize());
                gameModel.setDimension((gameView.getSize()));
            }
        });
    }

    public GameView getGameView() {
        return gameView;
    }

}
