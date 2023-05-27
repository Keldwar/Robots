package ds.model.commands;

import ds.model.entities.Bacteria;
import ds.model.GameState;
import ds.model.entities.TargetType;

public class NeutralizePoisonCommand implements Command {
    private boolean isCompleted;

    public NeutralizePoisonCommand() {
        this.isCompleted = false;
    }

    @Override
    public void execution(Bacteria bacteria, GameState gameState) {
        if (bacteria.getTarget() == null) {
            isCompleted = true;
            return;
        }
        if (bacteria.getTarget().getType() == TargetType.POISON) {
            bacteria.changeSatiety(20);
        }
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
