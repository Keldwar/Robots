package ds.model.entities;

import ds.model.GameState;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

public class Biome implements Entity {
    private int diam;
    private final BiomeType type;
    private final double x;
    private final double y;

    public Biome(int diam, double x, double y) {
        this.diam = diam;
        this.type = BiomeType.randomBiomeType();
        this.x = x;
        this.y = y;
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
        if (evt.getPropertyName().equals("set diam"))
            this.diam = (int) evt.getNewValue();
    }

    public BiomeType getType() {
        return type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getDiam() {
        return diam;
    }
}
