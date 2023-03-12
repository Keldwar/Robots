package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel implements Subject{
    private Robot robot;
    private List<Observer> observersList;
    public GameModel() {
        this.robot = new Robot();
        observersList = new ArrayList<>();
    }

    protected void setTargetPosition(Point p) {
        robot.setTargetPositionX(p.x);
        robot.setTargetPositionY(p.y);
        notify();
    }
    protected Point getTargetPosition() {
        return robot.getTargetPosition();
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

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, Robot.maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -Robot.maxAngularVelocity, Robot.maxAngularVelocity);
        double newX = robot.getPositionX() + velocity / angularVelocity *
                (Math.sin(robot.getRobotDirection() + angularVelocity * duration) -
                        Math.sin(robot.getRobotDirection()));
        if (!Double.isFinite(newX)) {
            newX = robot.getPositionX() + velocity * duration * Math.cos(robot.getRobotDirection());
        }
        double newY = robot.getPositionY() - velocity / angularVelocity *
                (Math.cos(robot.getRobotDirection() + angularVelocity * duration) -
                        Math.cos(robot.getRobotDirection()));
        if (!Double.isFinite(newY)) {
            newY = robot.getPositionY() + velocity * duration * Math.sin(robot.getRobotDirection());
        }
        robot.setPositionX(newX);
        robot.setPositionY(newY);
        double newDirection = asNormalizedRadians(robot.getRobotDirection() + angularVelocity * duration);
        robot.setRobotDirection(newDirection);
    }

    protected void onModelUpdateEvent() {
        double distance = distance(robot.getTargetPositionX(), robot.getTargetPositionY(),
                robot.getPositionX(), robot.getPositionY());
        if (distance < 0.5) {
            return;
        }
        double velocity = Robot.maxVelocity;
        double angleToTarget = angleTo(robot.getPositionX(), robot.getPositionY(),
                robot.getTargetPositionX(), robot.getTargetPositionY());
        double angularVelocity = 0;
        if (angleToTarget > robot.getRobotDirection()) {
            angularVelocity = robot.maxAngularVelocity;
        }
        if (angleToTarget < robot.getRobotDirection()) {
            angularVelocity = -robot.maxAngularVelocity;
        }

        moveRobot(velocity, angularVelocity, 10);
        notifyObservers();
    }

    public Robot getRobot() {
        return robot;
    }

    @Override
    public void attach(Observer observer) {
        observersList.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observersList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observersList) {
            observer.Update();
        }
    }
}
