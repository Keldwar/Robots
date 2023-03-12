package gui;

import java.awt.*;

public class Robot {
    private double positionX = 100;
    private double positionY = 100;

    public static final double maxVelocity = 0.1;
    public static final double maxAngularVelocity = 0.001;

    private volatile double robotDirection = 0;
    private volatile int targetPositionX = 150;
    private volatile int targetPositionY = 100;

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

    public int getTargetPositionX() {
        return targetPositionX;
    }

    public void setTargetPositionX(int targetPositionX) {
        this.targetPositionX = targetPositionX;
    }

    public int getTargetPositionY() {
        return targetPositionY;
    }

    public void setTargetPositionY(int targetPositionY) {
        this.targetPositionY = targetPositionY;
    }

    public Point getTargetPosition() {
        return new Point(getTargetPositionX(), getTargetPositionY());
    }
}
