package ds.model.commands;

import ds.model.Bacteria;

public class LookAroundCommand implements Command {
    private boolean isCompleted = false;
    @Override
    public void execution(Bacteria bacteria) {
        System.out.println("Look Around command execution");
        this.isCompleted = true;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }
}
