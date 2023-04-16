package ds.model;

import org.apache.commons.collections4.ListUtils;

import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameModel {
    private final List<Entity> entities;
    private final PropertyChangeSupport support;
    private final GameState gameState;

    public GameModel() {
        this.support = new PropertyChangeSupport(this);
        this.entities = initStateOfBacterias(5);
        this.gameState = new GameState();

        Timer timer = initTimer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer");
                support.firePropertyChange("change satiety", null, -10);
            }
        }, 0, 3000);
    }

    private static java.util.Timer initTimer() {
        return new Timer("satiety generator", true);
    }

    public void setDimension(Dimension dimension) {
        gameState.setDimension(dimension);
        support.firePropertyChange("set dimension", null, dimension);
    }

    public Dimension getDimension() {
        return ((Bacteria) entities.get(0)).getDimension();
    }

    public void updateModel() {
        for (Entity entity : entities) {
            entity.update(gameState);
            if (!entity.isAlive())
                entity.onFinish(support);
        }
        entities.removeIf(entity -> !entity.isAlive());
    }

    public List<Entity> getEntities() {
        return entities; //ListUtils.union(entities, gameState.getTargetList());
    }

    public void setTarget(Point point) {
        support.firePropertyChange("new point", null, point);
    }

    public List<Entity> initStateOfBacterias(int amount) {
        List<Entity> entityList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            Bacteria bacteria = new Bacteria(Math.random() * 400, Math.random() * 400);
            bacteria.setTarget(new Point((int) (Math.random() * 400), (int) (Math.random() * 400)));
            bacteria.onStart(support);
            entityList.add(bacteria);
        }
        return entityList;
    }
}
