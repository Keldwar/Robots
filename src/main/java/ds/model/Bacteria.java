package ds.model;


import ds.model.commands.Command;
import ds.model.commands.MoveBacteriaCommand;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

public class Bacteria implements Entity {
    private double positionX;
    private double positionY;
    private volatile double bacteriaDirection;
    private Target target;
    private Mood mood;
    private Command currentCommand;
    private Genome genome;
    private int satiety;
    public static final double maxVelocity = 0.05;
    public static final double maxAngularVelocity = 0.005;
    public static final int duration = 10;
    private boolean isAlive;
    private boolean isTargetAchieved;
    private Dimension dimension;
    private static final int INITIAL_SATIETY = 50;
    private static final int MAX_SATIETY = 100;

    public Bacteria(double x, double y) {
        this.positionX = x;
        this.positionY = y;
        this.target = new Target();
        this.bacteriaDirection = Math.random() * 10;
        this.dimension = new Dimension(400, 400);
        this.mood = Mood.randomMood();
        this.satiety = (int) (INITIAL_SATIETY + Math.random() * (MAX_SATIETY - INITIAL_SATIETY));
        this.isAlive = true;
        this.currentCommand = new MoveBacteriaCommand();
        this.genome = new Genome();
    }

    private void setRandomMood() {
        this.mood = Mood.randomMood();
    }

    public Bacteria() {
        this.positionX = 100;
        this.positionY = 100;
        this.target = new Target();
        this.bacteriaDirection = 0;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public int getSatiety() {
        return satiety;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
        if (!target.isPositionCorrect(dimension)) {
            target = new Target((int) (Math.random() * dimension.width), (int) (Math.random() * dimension.height));
        }
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getBacteriaDirection() {
        return bacteriaDirection;
    }

    public void setBacteriaDirection(double bacteriaDirection) {
        this.bacteriaDirection = bacteriaDirection;
    }

    public Target getTarget() {
        return target;
    }

    public void changeSatiety(int satiety) {
        this.satiety += satiety;
    }

    public void setTarget(Point point) {
        if (point.x > dimension.width || point.y > dimension.height) {
            this.target.setTargetPosition(new Point(point.x / dimension.width, point.y / dimension.height));
        }
        this.target.setTargetPosition(point);
    }

    @Override
    public void update() {
        if (!this.isAlive) {
            this.setMood(Mood.DEAD);

            return;
        }
        if (this.satiety < 50) {
            if (this.satiety < 0) {
                isAlive = false;
            }
            this.setMood(Mood.HUNGRY);

        }
        if (currentCommand.isCompleted()) {
            currentCommand = nextCommand();
        }

        currentCommand.execution(this);
    }

    public Command nextCommand() {
        return genome.getNextCommand();
    }

    @Override
    public void onStart(PropertyChangeSupport publisher) {
        publisher.addPropertyChangeListener(this);
    }

    @Override
    public void onFinish(PropertyChangeSupport publisher) {
        publisher.removePropertyChangeListener(this);
    }

    public void onTargetAchieved() {
        this.setTarget(new Point((int) (Math.random() * dimension.width), (int) (Math.random() * dimension.height)));
        this.satiety += 20;
        if (this.satiety > 50) {
            this.setRandomMood();
        }
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("new point"))
            setTarget((Point) evt.getNewValue());
        if (evt.getPropertyName().equals("change satiety"))
            changeSatiety((int) evt.getNewValue());
        if (evt.getPropertyName().equals("set dimension"))
            setDimension((Dimension) evt.getNewValue());
    }

    public Mood getMood() {
        return mood;
    }
}
