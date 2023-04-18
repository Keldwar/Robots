package ds.model;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

public class Target implements Entity {
    private volatile int x;
    private volatile int y;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setTargetPosition(Point p) {
        setX(p.x);
        setY(p.y);
    }

    protected Point getTargetPosition() {
        return new Point(getX(), getY());
    }

    public boolean isPositionCorrect(Dimension dimension) {
        return this.x <= dimension.width && this.y <= dimension.height;
    }

    public TargetType getType() {
        return type;
    }

    public void setType(TargetType type) {
        this.type = type;
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
