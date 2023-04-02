package ds.model;


import java.awt.*;
import java.beans.PropertyChangeEvent;

public class Robot implements Entity {
    private double positionX;
    private double positionY;
    private Target target;
    private Dimension dimension;

    public static final double maxVelocity = 0.05;
    public static final double maxAngularVelocity = 0.005;

    private volatile double robotDirection;

    public Robot() {
        this.positionX = 100;
        this.positionY = 100;
        this.target = new Target();
        this.robotDirection = 0;
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

    public void setTarget(Point point) {
        this.target.setTargetPosition(point);
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, Robot.maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -Robot.maxAngularVelocity, Robot.maxAngularVelocity);
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
        double distance = distance(target.getX(), target.getY(),
                getPositionX(), getPositionY());
        if (distance < 0.5) {
            return;
        }
        double velocity = Robot.maxVelocity;
        double angleToTarget = angleTo(getPositionX(), getPositionY(),
                target.getX(), target.getY());
        double angularVelocity = 0;
        if (angleToTarget > getRobotDirection()) {
            angularVelocity = Robot.maxAngularVelocity;
        }
        if (angleToTarget < getRobotDirection()) {
            angularVelocity = -Robot.maxAngularVelocity;
        }

        moveRobot(velocity, angularVelocity, 10);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Point point = (Point) evt.getNewValue();
        setTarget(point);
    }
}
