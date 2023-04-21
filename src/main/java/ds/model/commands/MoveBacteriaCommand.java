package ds.model.commands;

import ds.model.entities.Bacteria;
import ds.model.GameState;
import ds.model.entities.Target;
import ds.model.entities.TargetType;

import java.util.HashMap;
import java.util.Map;

import static ds.model.entities.Bacteria.duration;

public class MoveBacteriaCommand implements Command {
    private boolean isCompleted;
    public static final Map<TargetType, Integer> stepByTargetType = initMoveBacteria();

    private static Map<TargetType, Integer> initMoveBacteria() {
        Map<TargetType, Integer> result = new HashMap<>();
        result.put(TargetType.FOOD, 1);
        result.put(TargetType.POISON, 2);
        result.put(TargetType.NONE, 3);

        return result;
    }

    public MoveBacteriaCommand() {
        System.out.println("Move command execution");
        this.isCompleted = false;
    }

    @Override
    public void execution(Bacteria bacteria, GameState gameState) {
        move(bacteria, gameState);
    }

    @Override
    public boolean isCompleted() {
        return this.isCompleted;
    }

    @Override
    public int getNextStep(Bacteria bacteria) {
        if (bacteria.getTarget() != null)
            return stepByTargetType.get(bacteria.getTarget().getType());
        return 1;
    }

    public void move(Bacteria bacteria, GameState gameState) {
        if (!Target.isCorrect(bacteria.getTarget(), gameState.getDimension()) ||
                !gameState.getEntitiesByClass().get(Target.class).contains(bacteria.getTarget())) {
            this.isCompleted = true;
            return;
        }

        Target target = bacteria.getTarget();
        double distance = distance(target.getX(), target.getY(), bacteria.getPositionX(), bacteria.getPositionY());

        if (distance < 0.5) {
            this.isCompleted = true;
            bacteria.onTargetAchieved();
            System.out.println(bacteria.getSatiety());
            gameState.getEntitiesByClass().get(Target.class).remove(bacteria.getTarget());
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
