package ds.model.commands;

import ds.model.Bacteria;
import ds.model.Entity;
import ds.model.GameState;

public interface Command {
    void execution(Bacteria bacteria, GameState gameState);
    boolean isCompleted();
}
