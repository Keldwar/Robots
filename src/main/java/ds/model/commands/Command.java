package ds.model.commands;

import ds.model.Bacteria;
import ds.model.Entity;

public interface Command {
    void execution(Bacteria bacteria);
    boolean isCompleted();
}
