package ds.model;


import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

public class Bacteria implements Entity {
    private double positionX;
    private double positionY;
    private Target target;
    private Dimension dimension;
    private Mood mood;
    private int satiety;
    public static final double maxVelocity = 0.05;
    public static final double maxAngularVelocity = 0.005;
    private boolean isAlive;
    private volatile double robotDirection;
    private boolean isTargetAchieved;

    public Bacteria(double x, double y) {
        this.positionX = x;
        this.positionY = y;
        this.target = new Target();
        this.robotDirection = Math.random() * 10;
        this.dimension = new Dimension(400, 400);
        this.mood = Mood.randomMood();
        this.satiety = (int) (50 + Math.random() * 100);
        this.isAlive = true;
    }

    private void setRandomMood() {
        this.mood = Mood.randomMood();
    }

    public Bacteria() {
        this.positionX = 100;
        this.positionY = 100;
        this.target = new Target();
        this.robotDirection = 0;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public int getSatiety() {
        return satiety;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
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

    public double getRobotDirection() {
        return robotDirection;
    }

    public void setRobotDirection(double robotDirection) {
        this.robotDirection = robotDirection;
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    private double normalizedPositionX(double x) {
        if (x < 0)
            return 0;
        if (x > dimension.width)
            return dimension.width;
        return x;
    }

    private double normalizedPositionY(double y) {
        if (y < 0)
            return 0;
        if (y > dimension.height)
            return dimension.height;
        return y;
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
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

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, Bacteria.maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -Bacteria.maxAngularVelocity, Bacteria.maxAngularVelocity);
        double newX = getPositionX() + velocity / angularVelocity *
                (Math.sin(getRobotDirection() + angularVelocity * duration) -
                        Math.sin(getRobotDirection()));
        if (!Double.isFinite(newX)) {
            newX = getPositionX() + velocity * duration * Math.cos(getRobotDirection());
        }
        double newY = getPositionY() - velocity / angularVelocity *
                (Math.cos(getRobotDirection() + angularVelocity * duration) -
                        Math.cos(getRobotDirection()));
        if (!Double.isFinite(newY)) {
            newY = getPositionY() + velocity * duration * Math.sin(getRobotDirection());
        }
        setPositionX(normalizedPositionX(newX));
        setPositionY(normalizedPositionY(newY));
        double newDirection = asNormalizedRadians(getRobotDirection() + angularVelocity * duration);
        setRobotDirection(newDirection);

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
        double distance = distance(target.getX(), target.getY(),
                getPositionX(), getPositionY());
        this.isTargetAchieved = false;
        if (distance < 0.5) {
            this.isTargetAchieved = true;
            this.onTargetAchieved();

            return;
        }
        double velocity = Bacteria.maxVelocity;
        double angleToTarget = angleTo(getPositionX(), getPositionY(),
                target.getX(), target.getY());
        double angularVelocity = 0;
        if (angleToTarget > getRobotDirection()) {
            angularVelocity = Bacteria.maxAngularVelocity;
        }
        if (angleToTarget < getRobotDirection()) {
            angularVelocity = -Bacteria.maxAngularVelocity;
        }

        moveRobot(velocity, angularVelocity, 10);

    }

    @Override
    public void onStart(PropertyChangeSupport publisher) {
        publisher.addPropertyChangeListener(this);
    }

    @Override
    public void onFinish(PropertyChangeSupport publisher) {
        publisher.removePropertyChangeListener(this);
    }

    private void onTargetAchieved() {
        this.setTarget(new Point((int) (Math.random() * dimension.width), (int) (Math.random() * dimension.height)));
        this.satiety += 20;
        if (this.satiety > 50) {
            this.setRandomMood();
        }
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
