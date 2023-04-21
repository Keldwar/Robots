package ds.model.commands;

import ds.model.entities.Bacteria;
import ds.model.GameState;

public interface Command {
    void execution(Bacteria bacteria, GameState gameState);

    boolean isCompleted();

    int getNextStep(Bacteria bacteria);
}
