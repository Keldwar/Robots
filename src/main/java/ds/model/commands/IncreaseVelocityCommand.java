package ds.model.commands;

import ds.model.entities.Bacteria;
import ds.model.GameState;

public class IncreaseVelocityCommand implements Command {
    private boolean isCompleted;

    public IncreaseVelocityCommand() {
        this.isCompleted = false;
    }

    @Override
    public void execution(Bacteria bacteria, GameState gameState) {
        //bacteria.increaseVelocity(0.01);
        isCompleted = true;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public int getNextStep(Bacteria bacteria) {
        return 1;
    }
}
