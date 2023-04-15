package ds.model.commands;

import ds.model.Bacteria;
import ds.model.Target;

import static ds.model.Bacteria.duration;

public class MoveBacteriaCommand implements Command {
    private boolean isCompleted;

    public MoveBacteriaCommand() {
        this.isCompleted = false;
    }

    @Override
    public void execution(Bacteria bacteria) {
        move(bacteria);
    }

    @Override
    public boolean isCompleted() {
        return this.isCompleted;
    }

    public void move(Bacteria bacteria) {
        Target target = bacteria.getTarget();
        double distance = distance(target.getX(), target.getY(), bacteria.getPositionX(), bacteria.getPositionY());

        if (distance < 0.5) {
            this.isCompleted = true;
            bacteria.onTargetAchieved();
            return;
        }
        double angleToTarget = angleTo(bacteria.getPositionX(), bacteria.getPositionY(), target.getX(), target.getY());
        double angularVelocity = 0;
        if (angleToTarget > bacteria.getBacteriaDirection()) {
            angularVelocity = Bacteria.maxAngularVelocity;
        }
        if (angleToTarget < bacteria.getBacteriaDirection()) {
            angularVelocity = -Bacteria.maxAngularVelocity;
        }

        moveBacteria(Bacteria.maxVelocity, angularVelocity, duration, bacteria);
    }

    private void moveBacteria(double velocity, double angularVelocity, double duration, Bacteria bacteria) {
        velocity = applyLimits(velocity, 0, Bacteria.maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -Bacteria.maxAngularVelocity, Bacteria.maxAngularVelocity);
        double newX = bacteria.getPositionX() + velocity / angularVelocity * (Math.sin(bacteria.getBacteriaDirection() + angularVelocity * duration) - Math.sin(bacteria.getBacteriaDirection()));
        if (!Double.isFinite(newX)) {
            newX = bacteria.getPositionX() + velocity * duration * Math.cos(bacteria.getBacteriaDirection());
        }
        double newY = bacteria.getPositionY() - velocity / angularVelocity * (Math.cos(bacteria.getBacteriaDirection() + angularVelocity * duration) - Math.cos(bacteria.getBacteriaDirection()));
        if (!Double.isFinite(newY)) {
            newY = bacteria.getPositionY() + velocity * duration * Math.sin(bacteria.getBacteriaDirection());
        }
        bacteria.setPositionX(normalizedPositionX(newX, bacteria.getDimension().width));
        bacteria.setPositionY(normalizedPositionY(newY, bacteria.getDimension().height));
        double newDirection = asNormalizedRadians(bacteria.getBacteriaDirection() + angularVelocity * duration);
        bacteria.setBacteriaDirection(newDirection);
    }

    public static double distance(double x1, double y1, double x2, double y2) {
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

    private double normalizedPositionX(double x, double width) {
        if (x < 0) return 0;
        return Math.min(x, width);
    }

    private double normalizedPositionY(double y, double height) {
        if (y < 0) return 0;
        return Math.min(y, height);
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min) return min;
        return Math.min(value, max);
    }
}
