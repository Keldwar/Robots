package ds.model.commands;

public enum CommandEnum {
    MOVE_BACTERIA(1),
    LOOK_AROUND(1);
    public final int step;

    CommandEnum(int step) {
        this.step = step;
    }
}
