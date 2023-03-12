package gui;

import java.awt.*;

public class GameController {
    private final GameModel gameModel;


    public GameController() {
        this.gameModel = new GameModel();
    }
    public void UpdateModel() {
        gameModel.onModelUpdateEvent();
    }

    public void setTargetPosition(Point point) {
        gameModel.setTargetPosition(point);
    }
}
