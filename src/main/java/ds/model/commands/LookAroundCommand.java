package ds.model.commands;

import ds.model.Bacteria;
import ds.model.GameState;
import ds.model.Target;

import java.util.List;

public class LookAroundCommand implements Command {
    private boolean isCompleted = false;

    @Override
    public void execution(Bacteria bacteria, GameState gameState) {
        System.out.println("Look Around command execution");
        //bacteria.setTarget(chooseTarget(bacteria, gameState));
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
        List<Target> targetList = gameState.getTargetList();
        Target result = null;
        double minDistance = Double.MAX_VALUE;

        for (Target target : targetList) {
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
