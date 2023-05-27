package ds.viewModel.game;

import ds.log.Logger;
import ds.model.GameModel;
import ds.view.game.GameWindow;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameWindowViewModel {
    private final GameWindow gameWindow;
    private final GameViewModel gameViewModel;

    public GameWindowViewModel(GameModel gameModel) {
        this.gameViewModel = new GameViewModel(gameModel);
        this.gameWindow = new GameWindow(this.gameViewModel.getGameView());
        initGameWindowListeners(gameModel);
    }

    private void initGameWindowListeners(GameModel gameModel) {
        this.gameWindow.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                super.componentResized(e);
                Logger.debug("Windows resized to " + gameWindow.getSize());
            }
        });
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }
}
