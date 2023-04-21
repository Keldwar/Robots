package ds.model.commands;

import ds.model.entities.Bacteria;
import ds.model.entities.Entity;
import ds.model.GameState;
import ds.model.entities.Target;

import java.util.Set;

public class LookAroundCommand implements Command {
    private boolean isCompleted = false;

    @Override
    public void execution(Bacteria bacteria, GameState gameState) {
        System.out.println("Look Around command execution");
        bacteria.setTarget(chooseTarget(bacteria, gameState));
        this.isCompleted = true;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public int getNextStep(Bacteria bacteria) {
        return 1;
    }

    private Target chooseTarget(Bacteria bacteria, GameState gameState) {
        Set<Entity> targetSet = gameState.getEntitiesByClass().get(Target.class);
        Target result = null;
        double minDistance = Double.MAX_VALUE;

        for (Entity entity : targetSet) {
            Target target = (Target) entity;
            double distance = Math.pow(target.getX() - bacteria.getPositionX(), 2) +
                    Math.pow(target.getY() - bacteria.getPositionY(), 2);
            if (distance < minDistance) {
                minDistance = distance;
                result = target;
            }
        }
        return result;
    }
}
