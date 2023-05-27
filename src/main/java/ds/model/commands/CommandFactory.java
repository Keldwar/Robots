package ds.model.commands;

public class CommandFactory {
    public static Command getCommand(CommandEnum commandEnum) {
        return switch (commandEnum) {
            case LOOK_AROUND -> new LookAroundCommand();
            case MOVE_BACTERIA -> new MoveBacteriaCommand();
            case NEUTRALIZE_POISON -> new NeutralizePoisonCommand();
            case INCREASE_VELOCITY -> new IncreaseVelocityCommand();
        };
    }
}
