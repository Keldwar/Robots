package ds.model.commands;

import ds.model.Bacteria;
import ds.model.GameState;

public class NeutralizePoisonCommand implements Command {
    private boolean isCompleted;

    public NeutralizePoisonCommand() {
        this.isCompleted = false;
    }

    @Override
    public void execution(Bacteria bacteria, GameState gameState) {
        System.out.println("NeutralizePoisonCommand");
        bacteria.changeSatiety(20);
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
