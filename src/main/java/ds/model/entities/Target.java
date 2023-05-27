package ds.model.entities;

import ds.model.GameState;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

public class Target implements Entity {
    private final int x;
    private final int y;
    private TargetType type;

    public Target() {
        this.x = 150;
        this.y = 100;
        this.type = TargetType.randomTargetType();
    }

    public Target(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = TargetType.randomTargetType();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public static boolean isCorrect(Target target, Dimension dimension) {
        if (target == null)
            return false;
        return target.x <= dimension.width && target.y <= dimension.height;
    }

    public TargetType getType() {
        return type;
    }

    @Override
    public void update(GameState gameState) {

    }

    @Override
    public void onStart(PropertyChangeSupport publisher) {
        publisher.addPropertyChangeListener(this);
    }

    @Override
    public void onFinish(PropertyChangeSupport publisher) {
        publisher.removePropertyChangeListener(this);
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
